package ru.otus.swimming

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.otus.swimming.api.v1.models.OrderDebug
import ru.otus.swimming.api.v1.models.OrderReadObject
import ru.otus.swimming.api.v1.models.OrderReadRequest
import ru.otus.swimming.api.v1.models.OrderReadResponse
import ru.otus.swimming.api.v1.models.OrderRequestDebugMode
import ru.otus.swimming.api.v1.models.OrderRequestDebugStubs
import java.util.*
import kotlin.test.assertEquals

class KafkaTest {
    @Test
    fun runKafka() {
        // given
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()

        val listener = OrderListener(
            config = config,
            processor = ClSrvProcessor(),
            consumer = consumer,
            producer = producer
        )
        val readRequest = OrderReadRequest(
            requestType = "create",
            requestId = UUID.randomUUID().toString(),
            debug = OrderDebug(
                mode = OrderRequestDebugMode.STUB,
                stub = OrderRequestDebugStubs.SUCCESS
            ),
            order = OrderReadObject(id = "123")
        )
        val record = ConsumerRecord(
            config.ordersTopicIn,
            0,
            0L,
            "test-1",
            apiV1RequestSerialize(readRequest)
        )
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(config.ordersTopicIn, 0)))
            consumer.addRecord(record)
            listener.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(config.ordersTopicIn, 0)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        // when
        listener.run()

        // then
        val message = producer.history().first()
        val result = apiV1ResponseDeserialize<OrderReadResponse>(message.value())
        assertEquals(expected = readRequest.order!!.id, result.order!!.id)
        assertEquals(expected = "Москва, ул Зорге 3к1", result.order!!.address)
        assertEquals(expected = config.ordersTopicOut, actual = message.topic())
    }
}