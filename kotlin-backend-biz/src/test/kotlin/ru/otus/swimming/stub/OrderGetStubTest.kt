package ru.otus.swimming.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.ClSrvProcessor
import ru.otus.swimming.models.ClSrvCommand
import ru.otus.swimming.models.ClSrvOrder
import ru.otus.swimming.models.ClSrvOrderId
import ru.otus.swimming.models.ClSrvRequestId
import ru.otus.swimming.models.ClSrvWorkMode
import ru.otus.swimming.stubs.ClSrvStubs
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class OrderGetStubTest {
    private val processor = ClSrvProcessor()
    private val orderId = "123"

    @Test
    fun success() = runTest {
        // given
        val context = ClSrvContext(
            command = ClSrvCommand.READ,
            workMode = ClSrvWorkMode.STUB,
            stubCase = ClSrvStubs.SUCCESS,
            requestId = ClSrvRequestId(id = "123"),
            orderRequest = ClSrvOrder(
                id = ClSrvOrderId(orderId)
            )
        )

        // when
        processor.process(context)

        // then
        with(context) {
            assertEquals(expected = orderId, actual = orderResponse.id.asString())
        }
    }

    @Test
    fun notFound() = runTest {
        // given
        val context = ClSrvContext(
            command = ClSrvCommand.READ,
            workMode = ClSrvWorkMode.STUB,
            stubCase = ClSrvStubs.NOT_FOUND,
            requestId = ClSrvRequestId(id = "123"),
            orderRequest = ClSrvOrder(
                id = ClSrvOrderId(orderId)
            )
        )

        // when
        processor.process(context)

        // then
        with(context) {
            assertEquals(expected = ClSrvStubs.NOT_FOUND.name, actual = errors.first().code)
        }
    }
}