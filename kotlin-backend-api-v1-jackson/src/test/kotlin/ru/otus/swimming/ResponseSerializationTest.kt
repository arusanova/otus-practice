package ru.otus.swimming

import org.junit.Test
import ru.otus.swimming.api.v1.models.IRequest
import ru.otus.swimming.api.v1.models.OrderCreateObject
import ru.otus.swimming.api.v1.models.OrderCreateRequest
import ru.otus.swimming.api.v1.models.OrderDebug
import ru.otus.swimming.api.v1.models.OrderRequestDebugMode
import ru.otus.swimming.api.v1.models.OrderRequestDebugStubs
import java.util.*
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val orderCreateRequest = OrderCreateRequest(
        requestType = "create",
        requestId = UUID.randomUUID().toString(),
        debug = OrderDebug(
            mode = OrderRequestDebugMode.PROD,
            stub = OrderRequestDebugStubs.SUCCESS
        ),
        order = OrderCreateObject(
            userId = "123",
            companyId = "123",
            address = "Москва, ул Зорге 3к1",
            dateTime = "2023-09-01T21:00:00+01:00",
            service = "Аквакомплекс Лужники"
        )
    )

    @Test
    fun serialize() {
        // when
        val json = apiV1Mapper.writeValueAsString(orderCreateRequest)

        // then
        assertContains(json, Regex("\"userId\":\\s*\"${orderCreateRequest.order?.userId}\""))
        assertContains(json, Regex("\"companyId\":\\s*\"${orderCreateRequest.order?.companyId}\""))
        assertContains(json, Regex("\"address\":\\s*\"${orderCreateRequest.order?.address}\""))
        assertContains(json, Regex("\"service\":\\s*\"${orderCreateRequest.order?.service}\""))
        assertContains(json, Regex("\"requestType\":\\s*\"${orderCreateRequest.requestType}\""))
        assertContains(json, Regex("\"dateTime\":\\s*\"2011-12-03T10:15:30\\+01:00\""))
    }

    @Test
    fun deserialize() {
        // when
        val json = apiV1Mapper.writeValueAsString(orderCreateRequest)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as OrderCreateRequest

        // then
        assertEquals(orderCreateRequest, obj)
    }
}