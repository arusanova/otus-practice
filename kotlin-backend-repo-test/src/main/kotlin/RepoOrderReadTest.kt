import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.datetime.Clock
import ru.otus.swimming.models.*
import ru.otus.swimming.repo.DbOrderIdRequest
import ru.otus.swimming.repo.IOrderRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoOrderReadTest {
    abstract val orderRepository: IOrderRepository

    @Test
    fun readSuccess() = runRepoTest {
        val readOrder = initObjects[0]
        val result = orderRepository.readOrder(DbOrderIdRequest(readOrder.id))

        assertTrue { result.isSuccess }
        assertEquals(expected = readOrder, actual = result.data)
        assertEquals(expected = emptyList(), actual = result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = orderRepository.readOrder(DbOrderIdRequest(notFoundOrder.id))

        assertFalse { result.isSuccess }
        assertEquals(expected = result.errors.first().code, actual = "not-found")
    }

    companion object : IInitObjects<ClSrvOrder> {
        override val initObjects: List<ClSrvOrder> = listOf(
            ClSrvOrder(
                userId = ClSrvUserId(id = "123"),
                companyId = ClSrvCompanyId(id = "123"),
                id = ClSrvOrderId(id = "123"),
                dateTime = Clock.System.now(),
                address = "Москва, ул Зорге 3к1",
                lock = ClSrvOrderLock(id = "123")
            )
        )

        val notFoundOrder = ClSrvOrder(
            userId = ClSrvUserId(id = "234"),
            companyId = ClSrvCompanyId(id = "234"),
            id = ClSrvOrderId(id = "234"),
            dateTime = Clock.System.now(),
            address = "",
            lock = ClSrvOrderLock(id = "234")
        )
    }
}