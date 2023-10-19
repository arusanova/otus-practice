package ru.otus.swimming.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.Test
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.ClSrvCorSettings
import ru.otus.swimming.ClSrvProcessor
import ru.otus.swimming.NONE
import ru.otus.swimming.models.ClSrvCommand
import ru.otus.swimming.models.ClSrvCompanyId
import ru.otus.swimming.models.ClSrvOrder
import ru.otus.swimming.models.ClSrvOrderId
import ru.otus.swimming.models.ClSrvState
import ru.otus.swimming.models.ClSrvUserId
import ru.otus.swimming.models.ClSrvWorkMode
import ru.otus.swimming.repo.stub.OrderStub
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@OptIn(ExperimentalCoroutinesApi::class)
class OrderCreateValidationTest {
    private val ctx = ClSrvContext().apply {
        command = ClSrvCommand.CREATE
        workMode = ClSrvWorkMode.TEST
    }
    private val processor = ClSrvProcessor(ClSrvCorSettings(repoTest = OrderStub()))

    @Test
    fun incorrectUserId() = runTest {
        // given
        val orderWithBadUserId = ClSrvOrder(
            userId = ClSrvUserId(id = ""),
            companyId = ClSrvCompanyId(id = "123"),
            id = ClSrvOrderId(id = "123"),
            dateTime = Clock.System.now(),
            address = "ул Зорге",
            service = "Аквакомплекс Лужники"
        )
        val ctx = ctx.apply {
            orderRequest = orderWithBadUserId
        }

        // when
        processor.process(ctx)

        // then
        val error = ctx.errors.first()
        assertTrue { ctx.state == ClSrvState.FAILING }
        assertEquals(actual = error.field, expected = "userId")
        assertEquals(actual = error.code, expected = "badFormat")
    }

    @Test
    fun incorrectCompanyId() = runTest {
        // given
        val orderWithBadCompanyId = ClSrvOrder(
            userId = ClSrvUserId(id = "123"),
            companyId = ClSrvCompanyId(id = "-123"),
            id = ClSrvOrderId(id = "123"),
            dateTime = Clock.System.now(),
            address = "ул Зорге",
            service = "Аквакомплекс Лужники"
        )
        val ctx = ctx.apply {
            orderRequest = orderWithBadCompanyId
        }

        // when
        processor.process(ctx)

        // then
        val error = ctx.errors.first()
        assertTrue { ctx.state == ClSrvState.FAILING }
        assertEquals(actual = error.field, expected = "companyId")
        assertEquals(actual = error.code, expected = "badFormat")
    }

    @Test
    fun incorrectAddress() = runTest {
        // given
        val orderWithBadAddress = ClSrvOrder(
            userId = ClSrvUserId(id = "123"),
            companyId = ClSrvCompanyId(id = "123"),
            id = ClSrvOrderId(id = "123"),
            dateTime = Clock.System.now(),
            service = "Аквакомплекс Лужники",
            address = ""
        )
        val ctx = ctx.apply {
            orderRequest = orderWithBadAddress
        }

        // when
        processor.process(ctx)

        // then
        val error = ctx.errors.first()
        assertEquals(actual = error.field, expected = "address")
        assertEquals(actual = error.code, expected = "badFormat")
    }

    @Test
    fun incorrectService() = runTest {
        // given
        val orderWithBadAddress = ClSrvOrder(
            userId = ClSrvUserId(id = "123"),
            companyId = ClSrvCompanyId(id = "123"),
            id = ClSrvOrderId(id = "123"),
            dateTime = Clock.System.now(),
            address = "ул Зорге",
            service = ""
        )
        val ctx = ctx.apply {
            orderRequest = orderWithBadAddress
        }

        // when
        processor.process(ctx)

        // then
        val error = ctx.errors.first()
        assertEquals(actual = error.field, expected = "service")
        assertEquals(actual = error.code, expected = "badFormat")
    }

    @Test
    fun incorrectDatetime() = runTest {
        // given
        val orderWithBadDatetime = ClSrvOrder(
            userId = ClSrvUserId(id = "123"),
            companyId = ClSrvCompanyId(id = "123"),
            id = ClSrvOrderId(id = "123"),
            dateTime = Instant.NONE,
            address = "ул Зорге",
            service = "Аквакомплекс Лужники"
        )
        val ctx = ctx.apply {
            orderRequest = orderWithBadDatetime
        }

        // when
        processor.process(ctx)

        // then
        val error = ctx.errors.first()
        assertTrue { ctx.state == ClSrvState.FAILING }
        assertEquals(actual = error.field, expected = "datetime")
        assertEquals(actual = error.code, expected = "badFormat")
    }
}