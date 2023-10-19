import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.datetime.Clock
import ru.otus.swimming.models.*
import ru.otus.swimming.repo.DbOrderRequest
import ru.otus.swimming.repo.IOrderRepository
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoOrderCreateTest {
    abstract val orderRepository: IOrderRepository

    protected open val lockNew: ClSrvOrderLock = ClSrvOrderLock("20000000-0000-0000-0000-000000000002")

    private val createOrder = ClSrvOrder(
        userId = ClSrvUserId(id = "123"),
        companyId = ClSrvCompanyId(id = "123"),
        dateTime = Clock.System.now(),
        address = "Москва, ул Зорге 3к1",
        lock = ClSrvOrderLock(id = "123")
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = orderRepository.createOrder(DbOrderRequest(createOrder))
        assertTrue { result.isSuccess }
        assertNotNull(result.data?.id)
        assertEquals(expected = createOrder.address, actual = result.data!!.address)
        assertEquals(expected = createOrder.dateTime, actual = result.data!!.dateTime)
        assertEquals(expected = createOrder.companyId, actual = result.data!!.companyId)
        assertEquals(expected = createOrder.userId, actual = result.data!!.userId)
    }

    companion object : IInitObjects<ClSrvOrder> {
        override val initObjects: List<ClSrvOrder> = emptyList()
    }
}