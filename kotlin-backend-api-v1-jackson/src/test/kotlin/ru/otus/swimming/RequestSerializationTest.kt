package ru.otus.swimming

import org.junit.Test
import ru.otus.swimming.api.v1.models.IResponse
import ru.otus.swimming.api.v1.models.OrderCreateResponse
import ru.otus.swimming.api.v1.models.OrderResponseObject
import ru.otus.swimming.api.v1.models.ResponseResult
import java.util.*
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val orderCreateResponse = OrderCreateResponse(
        requestId = UUID.randomUUID().toString(),
        order = OrderResponseObject(
            userId = "123",
            companyId = "123",
            address = "Москва, ул Зорге 3к1",
            dateTime = "2023-09-01T21:00:00+01:00",
            service = "Аквакомплекс Лужники"
        ),
        responseType = "create",
        result = ResponseResult.SUCCESS,
        errors = listOf()
    )

    @Test
    fun serialize() {
        // when
        val json = apiV1Mapper.writeValueAsString(orderCreateResponse)

        // then
        assertContains(json, Regex("\"userId\":\\s*\"${orderCreateResponse.order?.userId}\""))
        assertContains(json, Regex("\"companyId\":\\s*\"${orderCreateResponse.order?.companyId}\""))
        assertContains(json, Regex("\"address\":\\s*\"${orderCreateResponse.order?.address}\""))
        assertContains(json, Regex("\"service\":\\s*\"${orderCreateResponse.order?.service}\""))
        assertContains(json, Regex("\"responseType\":\\s*\"${orderCreateResponse.responseType}\""))
        assertContains(json, Regex("\"dateTime\":\\s*\"2023-09-01T21:00:00\\+01:00\""))
        assertContains(json, Regex("\"result\":\\s*\"${ResponseResult.SUCCESS}\""))
    }

    @Test
    fun deserialize() {
        // when
        val json = apiV1Mapper.writeValueAsString(orderCreateResponse)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as OrderCreateResponse

        // then
        assertEquals(orderCreateResponse, obj)
    }
}