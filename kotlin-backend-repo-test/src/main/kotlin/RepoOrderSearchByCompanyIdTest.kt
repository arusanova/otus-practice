import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.datetime.Clock
import ru.otus.swimming.models.*
import ru.otus.swimming.repo.DbCompanyIdRequest
import ru.otus.swimming.repo.DbUserIdRequest
import ru.otus.swimming.repo.IOrderRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoOrderSearchByCompanyIdTest {
    abstract val orderRepository: IOrderRepository

    @Test
    fun searchSuccess() = runRepoTest {
        val result = orderRepository.searchByCompanyId(DbCompanyIdRequest(ClSrvCompanyId(companyId)))

        assertTrue { result.isSuccess }
        assertEquals(expected = 2, actual = result.data!!.size)
        assertEquals(expected = emptyList(), actual = result.errors)
    }

    @Test
    fun searchEmpty() = runRepoTest {
        val result = orderRepository.searchByCompanyId(DbCompanyIdRequest(ClSrvCompanyId("200")))

        assertTrue { result.isSuccess }
        assertEquals(expected = 0, actual = result.data!!.size)
        assertEquals(expected = emptyList(), actual = result.errors)
    }

    companion object : IInitObjects<ClSrvOrder> {
        private const val companyId = "12"
        override val initObjects: List<ClSrvOrder> = listOf(
            ClSrvOrder(
                userId = ClSrvUserId(id = "123"),
                companyId = ClSrvCompanyId(id = companyId),
                id = ClSrvOrderId(id = "123"),
                dateTime = Clock.System.now(),
                address = "Москва, ул Зорге 3к1",
                lock = ClSrvOrderLock(id = "123")
            ),
            ClSrvOrder(
                userId = ClSrvUserId(id = "123"),
                companyId = ClSrvCompanyId(id = companyId),
                id = ClSrvOrderId(id = "234"),
                dateTime = Clock.System.now(),
                address = "Москва, ул Зорге 3к1",
                lock = ClSrvOrderLock(id = "123")
            ),
            ClSrvOrder(
                userId = ClSrvUserId(id = "123"),
                companyId = ClSrvCompanyId(id = "123"),
                id = ClSrvOrderId(id = "345"),
                dateTime = Clock.System.now(),
                address = "Москва, ул Зорге 3к1",
                lock = ClSrvOrderLock(id = "123")
            )
        )

    }
}