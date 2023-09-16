package ru.otus.swimming.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.Test
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.ClSrvProcessor
import ru.otus.swimming.models.ClSrvCommand
import ru.otus.swimming.models.ClSrvCompanyId
import ru.otus.swimming.models.ClSrvOrder
import ru.otus.swimming.models.ClSrvOrderId
import ru.otus.swimming.models.ClSrvUserId
import ru.otus.swimming.models.ClSrvWorkMode
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class OrderSearchByCompanyIdValidationTest {
    private val ctx = ClSrvContext().apply {
        command = ClSrvCommand.SEARCH_BY_COMPANY_ID
        workMode = ClSrvWorkMode.TEST
    }
    private val processor = ClSrvProcessor()

    @Test
    fun incorrectCompanyId() = runTest {
        // given
        val orderWithBadCompanyId = ClSrvOrder(
            userId = ClSrvUserId(id = "123"),
            companyId = ClSrvCompanyId(id = ""),
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
        assertEquals(actual = error.field, expected = "companyId")
        assertEquals(actual = error.code, expected = "badFormat")
    }
}