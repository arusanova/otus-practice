package ru.otus.swimming.repo.sql

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.swimming.helpers.asClSrvError
import ru.otus.swimming.models.ClSrvOrder
import ru.otus.swimming.repo.*

class RepoOrderSql(
    properties: SqlProperties,
    initObjects: Collection<ClSrvOrder> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : IOrderRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
        }

        Database.connect(
            properties.url, driver, properties.user, properties.password
        )

        transaction {
            if (properties.dropDatabase) SchemaUtils.drop(OrderTable)
            SchemaUtils.create(OrderTable)
            initObjects.forEach { createOrder(it) }
        }
    }

    private fun createOrder(order: ClSrvOrder) : ClSrvOrder {
        val res = OrderTable.insert {
            to(it, order, randomUuid)
        }

        return OrderTable.from(res)
    }

    override suspend fun createOrder(dbOrderRequest: DbOrderRequest): DbOrderResponse = transactionWrapper { DbOrderResponse.success(dbOrderRequest.order) }

    override suspend fun readOrder(dbOrderIdRequest: DbOrderIdRequest): DbOrderResponse = transactionWrapper {
        val res = OrderTable.select {
            OrderTable.id eq dbOrderIdRequest.orderId.asString()
        }.singleOrNull() ?: return@transactionWrapper DbOrderResponse.errorNotFound
        DbOrderResponse.success(OrderTable.from(res))
    }

    override suspend fun searchByUserId(dbUserIdRequest: DbUserIdRequest): DbOrdersResponse = transactionWrapper ({
        val res = OrderTable.select {
            OrderTable.userId eq dbUserIdRequest.userId.asString()
        }.map { OrderTable.from(it) }
        DbOrdersResponse(data = res, errors = emptyList(), isSuccess = true)
    }) {
        DbOrdersResponse.error(it.asClSrvError())
    }

    override suspend fun searchByCompanyId(dbCompanyIdRequest: DbCompanyIdRequest): DbOrdersResponse = transactionWrapper ({
        val res = OrderTable.select {
            OrderTable.companyId eq dbCompanyIdRequest.companyId.asString()
        }.map { OrderTable.from(it) }
        DbOrdersResponse(data = res, errors = emptyList(), isSuccess = true)
    }) {
        DbOrdersResponse.error(it.asClSrvError())
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbOrderResponse): DbOrderResponse =
        transactionWrapper(block) { DbOrderResponse.error(it.asClSrvError()) }

}