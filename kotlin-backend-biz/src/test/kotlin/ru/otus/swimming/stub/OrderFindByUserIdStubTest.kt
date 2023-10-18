package ru.otus.swimming.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.ClSrvProcessor
import ru.otus.swimming.models.ClSrvCommand
import ru.otus.swimming.models.ClSrvOrder
import ru.otus.swimming.models.ClSrvRequestId
import ru.otus.swimming.models.ClSrvUserId
import ru.otus.swimming.models.ClSrvWorkMode
import ru.otus.swimming.stubs.ClSrvStubs
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class OrderFindByUserIdStubTest {
    private val processor = ClSrvProcessor()
    private val userId = "123"

    @Test
    fun success() = runTest {
        // given
        val context = ClSrvContext(
            command = ClSrvCommand.SEARCH_BY_USER_ID,
            workMode = ClSrvWorkMode.STUB,
            stubCase = ClSrvStubs.SUCCESS,
            requestId = ClSrvRequestId(id = "123"),
            orderRequest = ClSrvOrder(
                userId = ClSrvUserId(userId)
            )
        )

        // when
        processor.process(context)

        // then
        with(context) {
            assertEquals(expected = userId, actual = ordersResponse.first().companyId.asString())
        }
    }

    @Test
    fun notFound() = runTest {
        // given
        val context = ClSrvContext(
            command = ClSrvCommand.SEARCH_BY_USER_ID,
            workMode = ClSrvWorkMode.STUB,
            stubCase = ClSrvStubs.NOT_FOUND,
            requestId = ClSrvRequestId(id = "1"),
            orderRequest = ClSrvOrder(
                userId = ClSrvUserId(userId)
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
    fun badUserId() = runTest {
        // given
        val context = ClSrvContext(
            command = ClSrvCommand.SEARCH_BY_USER_ID,
            workMode = ClSrvWorkMode.STUB,
            stubCase = ClSrvStubs.BAD_USER_ID,
            requestId = ClSrvRequestId(id = "1"),
            orderRequest = ClSrvOrder(
                userId = ClSrvUserId(userId)
            )
        )

        // when
        processor.process(context)

        // then
        with(context) {
            assertEquals(expected = ClSrvStubs.BAD_USER_ID.name, actual = errors.first().code)
        }
    }
}