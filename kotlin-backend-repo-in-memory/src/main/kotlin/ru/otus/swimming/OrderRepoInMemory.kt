package ru.otus.swimming

import com.benasher44.uuid.uuid4
import ru.otus.swimming.model.OrderEntity
import ru.otus.swimming.models.ClSrvOrder
import ru.otus.swimming.repo.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import io.github.reactivecircus.cache4k.Cache
import ru.otus.swimming.models.ClSrvError
import ru.otus.swimming.models.ClSrvOrderId
import ru.otus.swimming.models.ClSrvOrderLock

class OrderRepoInMemory(
    initObjects: List<ClSrvOrder> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IOrderRepository {

    private val cache = Cache.Builder<String, OrderEntity>()
        .expireAfterWrite(ttl)
        .build()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(order: ClSrvOrder) {
        val entity = OrderEntity(order)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createOrder(dbOrderRequest: DbOrderRequest): DbOrderResponse {
        val key = randomUuid()
        val order = dbOrderRequest.order.copy(id = ClSrvOrderId(key), lock = ClSrvOrderLock(randomUuid()))
        val orderEntity = OrderEntity(order)
        cache.put(key, orderEntity)
        return DbOrderResponse(
            data = order,
            isSuccess = true
        )
    }

    override suspend fun readOrder(dbOrderIdRequest: DbOrderIdRequest): DbOrderResponse {
        return cache.get(dbOrderIdRequest.orderId.asString())
            ?.let { DbOrderResponse(
                data = it.toInternal(),
                isSuccess = true
            ) }
            ?: DbOrderResponse(
                isSuccess = false,
                errors = listOf(
                    ClSrvError(
                        code = "not-found",
                        field = "id",
                        message = "NOT FOUND",
                    )
                ),
                data = null
            )
    }

    override suspend fun searchByUserId(dbUserIdRequest: DbUserIdRequest): DbOrdersResponse {
        val userOrders = cache
            .asMap()
            .asSequence()
            .filter {
                it.value.userId == dbUserIdRequest.userId.asString()
            }.map { it.value.toInternal() }
            .toList()
        return DbOrdersResponse(
            data = userOrders,
            isSuccess = true
        )
    }

    override suspend fun searchByCompanyId(dbCompanyIdRequest: DbCompanyIdRequest): DbOrdersResponse {
        val companyOrders = cache
            .asMap()
            .asSequence()
            .filter {
                it.value.companyId == dbCompanyIdRequest.companyId.asString()
            }.map { it.value.toInternal() }
            .toList()
        return DbOrdersResponse(
            data = companyOrders,
            isSuccess = true
        )
    }

}