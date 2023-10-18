package ru.otus.swimming.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.ClSrvProcessor
import ru.otus.swimming.models.ClSrvCommand
import ru.otus.swimming.models.ClSrvCompanyId
import ru.otus.swimming.models.ClSrvOrder
import ru.otus.swimming.models.ClSrvRequestId
import ru.otus.swimming.models.ClSrvWorkMode
import ru.otus.swimming.stubs.ClSrvStubs
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class OrderFindByCompanyIdStubTest {
    private val processor = ClSrvProcessor()
    private val companyId = "123"

    @Test
    fun success() = runTest {
        // given
        val context = ClSrvContext(
            command = ClSrvCommand.SEARCH_BY_COMPANY_ID,
            workMode = ClSrvWorkMode.STUB,
            stubCase = ClSrvStubs.SUCCESS,
            requestId = ClSrvRequestId(id = "123"),
            orderRequest = ClSrvOrder(
                companyId = ClSrvCompanyId(companyId)
            )
        )

        // when
        processor.process(context)

        // then
        with(context) {
            assertEquals(expected = companyId, actual = ordersResponse.first().companyId.asString())
        }
    }

    @Test
    fun notFound() = runTest {
        // given
        val context = ClSrvContext(
            command = ClSrvCommand.SEARCH_BY_COMPANY_ID,
            workMode = ClSrvWorkMode.STUB,
            stubCase = ClSrvStubs.NOT_FOUND,
            requestId = ClSrvRequestId(id = "123"),
            orderRequest = ClSrvOrder(
                companyId = ClSrvCompanyId(companyId)
            )
        )

        // when
        processor.process(context)

        // then
        with(context) {
            assertEquals(expected = ClSrvStubs.NOT_FOUND.name, actual = errors.first().code)
        }
    }

    @Test
    fun badCompanyId() = runTest {
        // given
        val context = ClSrvContext(
            command = ClSrvCommand.SEARCH_BY_COMPANY_ID,
            workMode = ClSrvWorkMode.STUB,
            stubCase = ClSrvStubs.BAD_COMPANY_ID,
            requestId = ClSrvRequestId(id = "123"),
            orderRequest = ClSrvOrder(
                companyId = ClSrvCompanyId(companyId)
            )
        )

        // when
        processor.process(context)

        // then
        with(context) {
            assertEquals(expected = ClSrvStubs.BAD_COMPANY_ID.name, actual = errors.first().code)
        }
    }
}