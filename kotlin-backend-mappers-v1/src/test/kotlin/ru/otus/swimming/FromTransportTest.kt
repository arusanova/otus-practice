package ru.otus.swimming

import org.junit.Test
import ru.otus.swimming.api.v1.models.OrderCreateObject
import ru.otus.swimming.api.v1.models.OrderCreateRequest
import ru.otus.swimming.api.v1.models.OrderDebug
import ru.otus.swimming.api.v1.models.OrderReadObject
import ru.otus.swimming.api.v1.models.OrderReadRequest
import ru.otus.swimming.api.v1.models.OrderRequestDebugMode
import ru.otus.swimming.api.v1.models.OrderRequestDebugStubs
import ru.otus.swimming.api.v1.models.OrderSearchByCompanyIdObject
import ru.otus.swimming.api.v1.models.OrderSearchByCompanyIdRequest
import ru.otus.swimming.api.v1.models.OrderSearchByUserIdObject
import ru.otus.swimming.api.v1.models.OrderSearchByUserIdRequest
import ru.otus.swimming.models.ClSrvCommand
import ru.otus.swimming.models.ClSrvWorkMode
import ru.otus.swimming.stubs.ClSrvStubs
import java.util.*
import kotlin.test.assertEquals

class FromTransportTest {
    @Test
    fun fromTransportCreaete() {
        // given
        val orderCreateRequest = OrderCreateRequest(
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
        val context = ClSrvContext()

        // when
        context.fromTransport(orderCreateRequest)

        // then
        assertEquals(actual = context.orderRequest.address, expected = orderCreateRequest.order!!.address)
        assertEquals(actual = context.orderRequest.service, expected = orderCreateRequest.order!!.service)
        assertEquals(actual = context.orderRequest.dateTime.toString(), expected = orderCreateRequest.order!!.dateTime)
        assertEquals(actual = context.orderRequest.companyId.asString(), expected = orderCreateRequest.order!!.companyId)
        assertEquals(actual = context.orderRequest.userId.asString(), expected = orderCreateRequest.order!!.userId)
        assertEquals(actual = context.requestId.asString(), expected = orderCreateRequest.requestId)
        assertEquals(actual = context.command, expected = ClSrvCommand.CREATE)
        assertEquals(actual = context.stubCase, expected = ClSrvStubs.SUCCESS)
        assertEquals(actual = context.workMode, expected = ClSrvWorkMode.PROD)
    }

    @Test
    fun fromTransportRead() {
        // given
        val orderCreateRequest = OrderReadRequest(
            requestType = "read",
            requestId = UUID.randomUUID().toString(),
            debug = OrderDebug(
                mode = OrderRequestDebugMode.PROD,
                stub = OrderRequestDebugStubs.SUCCESS
            ),
            order = OrderReadObject(
                id = "123"
            )
        )
        val context = ClSrvContext()

        // when
        context.fromTransport(orderCreateRequest)

        // then
        assertEquals(actual = context.orderRequest.id.asString(), expected = orderCreateRequest.order!!.id)
        assertEquals(actual = context.requestId.asString(), expected = orderCreateRequest.requestId)
        assertEquals(actual = context.command, expected = ClSrvCommand.READ)
        assertEquals(actual = context.stubCase, expected = ClSrvStubs.SUCCESS)
        assertEquals(actual = context.workMode, expected = ClSrvWorkMode.PROD)
    }

    @Test
    fun fromTransportSearchByUserId() {
        // given
        val orderCreateRequest = OrderSearchByUserIdRequest(
            requestType = "search_by_user_id",
            requestId = UUID.randomUUID().toString(),
            debug = OrderDebug(
                mode = OrderRequestDebugMode.PROD,
                stub = OrderRequestDebugStubs.SUCCESS
            ),
            order = OrderSearchByUserIdObject(
                userId = "123"
            )
        )
        val context = ClSrvContext()

        // when
        context.fromTransport(orderCreateRequest)

        // then
        assertEquals(actual = context.orderRequest.userId.asString(), expected = orderCreateRequest.order!!.userId)
        assertEquals(actual = context.requestId.asString(), expected = orderCreateRequest.requestId)
        assertEquals(actual = context.command, expected = ClSrvCommand.SEARCH_BY_USER_ID)
        assertEquals(actual = context.stubCase, expected = ClSrvStubs.SUCCESS)
        assertEquals(actual = context.workMode, expected = ClSrvWorkMode.PROD)
    }

    @Test
    fun fromTransportSearchByCompanyId() {
        // given
        val orderCreateRequest = OrderSearchByCompanyIdRequest(
            requestType = "search_by_company_id",
            requestId = UUID.randomUUID().toString(),
            debug = OrderDebug(
                mode = OrderRequestDebugMode.PROD,
                stub = OrderRequestDebugStubs.SUCCESS
            ),
            order = OrderSearchByCompanyIdObject(
                companyId = "123"
            )
        )
        val context = ClSrvContext()

        // when
        context.fromTransport(orderCreateRequest)

        // then
        assertEquals(actual = context.orderRequest.companyId.asString(), expected = orderCreateRequest.order!!.companyId)
        assertEquals(actual = context.requestId.asString(), expected = orderCreateRequest.requestId)
        assertEquals(actual = context.command, expected = ClSrvCommand.SEARCH_BY_COMPANY_ID)
        assertEquals(actual = context.stubCase, expected = ClSrvStubs.SUCCESS)
        assertEquals(actual = context.workMode, expected = ClSrvWorkMode.PROD)
    }
}