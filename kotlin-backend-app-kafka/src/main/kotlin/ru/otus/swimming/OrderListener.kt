package ru.otus.swimming

import kotlinx.atomicfu.AtomicBoolean
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import ru.otus.swimming.api.v1.models.IRequest
import java.time.Duration
import java.util.*

class OrderListener(
    private val config: AppKafkaConfig,
    private val processor: ClSrvProcessor,
    private val consumer: Consumer<String, String> = config.createKafkaConsumer(),
    private val producer: Producer<String, String> = config.createKafkaProducer(),
    private val process: AtomicBoolean = atomic(true)
) {
    fun run() = runBlocking {
        try {
            consumer.subscribe(listOf(config.ordersTopicIn))
            while (process.value) {
                val records: ConsumerRecords<String, String> = withContext(Dispatchers.IO) {
                    consumer.poll(Duration.ofSeconds(1))
                }
                records.forEach { processRecord(it) }
            }
        } catch (e: Exception) {
            withContext(NonCancellable) {
                throw e
            }
        } finally {
            withContext(NonCancellable) {
                consumer.close()
                producer.close()
            }
        }
    }

    private suspend fun processRecord(record: ConsumerRecord<String, String>) {
        log.info { "process ${record.key()} from ${record.topic()}:\n${record.value()}" }

        val request: IRequest = apiV1RequestDeserialize(record.value())

        val ctx = ClSrvContext(timeStart = Clock.System.now())
        ctx.fromTransport(request)

        processor.process(ctx)
        val responseJson = apiV1ResponseSerialize(ctx.toTransport())

        sendResponseToOutputTopic(responseJson)
    }

    private fun sendResponseToOutputTopic(response: String) {
        ProducerRecord(
            config.ordersTopicOut,
            UUID.randomUUID().toString(),
            response
        ).let { producer.send(it) }
    }

    fun stop() {
        process.value = false
    }

    private companion object {
        private val log = KotlinLogging.logger {}
    }
}