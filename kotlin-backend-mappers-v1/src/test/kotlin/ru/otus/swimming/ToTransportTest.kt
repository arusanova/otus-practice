package ru.otus.swimming

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.Test
import ru.otus.swimming.api.v1.models.OrderCreateResponse
import ru.otus.swimming.api.v1.models.OrderReadResponse
import ru.otus.swimming.api.v1.models.OrderSearchByCompanyIdResponse
import ru.otus.swimming.api.v1.models.OrderSearchByUserIdResponse
import ru.otus.swimming.models.ClSrvCommand
import ru.otus.swimming.models.ClSrvCompanyId
import ru.otus.swimming.models.ClSrvOrder
import ru.otus.swimming.models.ClSrvOrderId
import ru.otus.swimming.models.ClSrvRequestId
import ru.otus.swimming.models.ClSrvState
import ru.otus.swimming.models.ClSrvUserId
import ru.otus.swimming.models.ClSrvWorkMode
import ru.otus.swimming.stubs.ClSrvStubs
import java.util.*
import kotlin.test.assertEquals

class ToTransportTest {
    @Test
    fun toTransportCreate() {
        // given
        val context = ClSrvContext(
            command = ClSrvCommand.CREATE,
            state = ClSrvState.RUNNING,
            errors = mutableListOf(),
            workMode = ClSrvWorkMode.PROD,
            stubCase = ClSrvStubs.SUCCESS,
            requestId = ClSrvRequestId(id = UUID.randomUUID().toString()),
            timeStart = Clock.System.now(),
            orderResponse = ClSrvOrder(
                userId = ClSrvUserId(id = "123"),
                companyId = ClSrvCompanyId(id = "123"),
                id = ClSrvOrderId(id = "123"),
                dateTime = Instant.parse("2023-09-01T21:00:00Z"),
                address = "Москва, ул Зорге 3к1",
                service = "Аквакомплекс Лужники"
            )
        )

        // when
        val createTransport = context.toTransport() as OrderCreateResponse

        // then
        assertEquals(expected = context.requestId.asString(), actual = createTransport.requestId)
        assertEquals(expected = "create", actual = createTransport.responseType)
        assertEquals(expected = context.orderResponse.userId.asString(), actual = createTransport.order!!.userId)
        assertEquals(expected = context.orderResponse.companyId.asString(), actual = createTransport.order!!.companyId)
        assertEquals(expected = context.orderResponse.address, actual = createTransport.order!!.address)
        assertEquals(expected = context.orderResponse.service, actual = createTransport.order!!.service)
        assertEquals(expected = context.orderResponse.dateTime.toString(), actual = createTransport.order!!.dateTime)
    }

    @Test
    fun toTransportRead() {
        // given
        val context = ClSrvContext(
            command = ClSrvCommand.READ,
            state = ClSrvState.RUNNING,
            errors = mutableListOf(),
            workMode = ClSrvWorkMode.PROD,
            stubCase = ClSrvStubs.SUCCESS,
            requestId = ClSrvRequestId(id = UUID.randomUUID().toString()),
            timeStart = Clock.System.now(),
            orderResponse = ClSrvOrder(
                userId = ClSrvUserId(id = "123"),
                companyId = ClSrvCompanyId(id = "123"),
                id = ClSrvOrderId(id = "123"),
                dateTime = Instant.parse("2023-09-01T21:00:00Z"),
                address = "Москва, ул Зорге 3к1",
                service = "Аквакомплекс Лужники"
            )
        )

        // when
        val createTransport = context.toTransport() as OrderReadResponse

        // then
        assertEquals(expected = context.requestId.asString(), actual = createTransport.requestId)
        assertEquals(expected = "read", actual = createTransport.responseType)
        assertEquals(expected = context.orderResponse.userId.asString(), actual = createTransport.order!!.userId)
        assertEquals(expected = context.orderResponse.companyId.asString(), actual = createTransport.order!!.companyId)
        assertEquals(expected = context.orderResponse.address, actual = createTransport.order!!.address)
        assertEquals(expected = context.orderResponse.service, actual = createTransport.order!!.service)
        assertEquals(expected = context.orderResponse.dateTime.toString(), actual = createTransport.order!!.dateTime)
    }

    @Test
    fun toTransportSearchByCompanyId() {
        // given
        val context = ClSrvContext(
            command = ClSrvCommand.SEARCH_BY_COMPANY_ID,
            state = ClSrvState.RUNNING,
            errors = mutableListOf(),
            workMode = ClSrvWorkMode.PROD,
            stubCase = ClSrvStubs.SUCCESS,
            requestId = ClSrvRequestId(id = UUID.randomUUID().toString()),
            timeStart = Clock.System.now(),
            ordersResponse = mutableListOf(
                ClSrvOrder(
                    userId = ClSrvUserId(id = "123"),
                    companyId = ClSrvCompanyId(id = "123"),
                    id = ClSrvOrderId(id = "123"),
                    dateTime = Instant.parse("2023-09-01T21:00:00Z"),
                    address = "Москва, ул Зорге 3к1",
                    service = "Аквакомплекс Лужники"
                )
            )
        )

        // when
        val createTransport = context.toTransport() as OrderSearchByCompanyIdResponse

        // then
        assertEquals(expected = context.requestId.asString(), actual = createTransport.requestId)
        assertEquals(expected = "search_by_company_id", actual = createTransport.responseType)
        assertEquals(expected = context.ordersResponse.first().userId.asString(), actual = createTransport.orders!!.first().userId)
        assertEquals(expected = context.ordersResponse.first().companyId.asString(), actual = createTransport.orders!!.first().companyId)
        assertEquals(expected = context.ordersResponse.first().address, actual = createTransport.orders!!.first().address)
        assertEquals(expected = context.ordersResponse.first().service, actual = createTransport.orders!!.first().service)
        assertEquals(expected = context.ordersResponse.first().dateTime.toString(), actual = createTransport.orders!!.first().dateTime)
    }

    @Test
    fun toTransportSearchByUserId() {
        // given
        val context = ClSrvContext(
            command = ClSrvCommand.SEARCH_BY_USER_ID,
            state = ClSrvState.RUNNING,
            errors = mutableListOf(),
            workMode = ClSrvWorkMode.PROD,
            stubCase = ClSrvStubs.SUCCESS,
            requestId = ClSrvRequestId(id = UUID.randomUUID().toString()),
            timeStart = Clock.System.now(),
            ordersResponse = mutableListOf(
                ClSrvOrder(
                    userId = ClSrvUserId(id = "123"),
                    companyId = ClSrvCompanyId(id = "123"),
                    id = ClSrvOrderId(id = "123"),
                    dateTime = Instant.parse("2023-09-01T21:00:00Z"),
                    address = "Москва, ул Зорге 3к1",
                    service = "Аквакомплекс Лужники"
                )
            )
        )

        // when
        val createTransport = context.toTransport() as OrderSearchByUserIdResponse

        // then
        assertEquals(expected = context.requestId.asString(), actual = createTransport.requestId)
        assertEquals(expected = "search_by_user_id", actual = createTransport.responseType)
        assertEquals(expected = context.ordersResponse.first().userId.asString(), actual = createTransport.orders!!.first().userId)
        assertEquals(expected = context.ordersResponse.first().companyId.asString(), actual = createTransport.orders!!.first().companyId)
        assertEquals(expected = context.ordersResponse.first().address, actual = createTransport.orders!!.first().address)
        assertEquals(expected = context.ordersResponse.first().service, actual = createTransport.orders!!.first().service)
        assertEquals(expected = context.ordersResponse.first().dateTime.toString(), actual = createTransport.orders!!.first().dateTime)
    }
}