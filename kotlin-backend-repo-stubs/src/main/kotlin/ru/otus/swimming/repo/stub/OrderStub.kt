package ru.otus.swimming.repo.stub

import ru.otus.swimming.ClSrvStub
import ru.otus.swimming.repo.DbCompanyIdRequest
import ru.otus.swimming.repo.DbOrderIdRequest
import ru.otus.swimming.repo.DbOrderRequest
import ru.otus.swimming.repo.DbOrderResponse
import ru.otus.swimming.repo.DbOrdersResponse
import ru.otus.swimming.repo.DbUserIdRequest
import ru.otus.swimming.repo.IOrderRepository

class OrderStub : IOrderRepository {
    override suspend fun createOrder(dbOrderRequest: DbOrderRequest): DbOrderResponse {
        return DbOrderResponse(
            data = ClSrvStub.createOrderStub(dbOrderRequest.order),
            isSuccess = true,
            errors = emptyList()
        )
    }

    override suspend fun readOrder(dbOrderIdRequest: DbOrderIdRequest): DbOrderResponse {
        return DbOrderResponse(
            data = ClSrvStub.get(),
            isSuccess = true,
            errors = emptyList()
        )
    }

    override suspend fun searchByUserId(dbUserIdRequest: DbUserIdRequest): DbOrdersResponse {
        return DbOrdersResponse(
            data = listOf(ClSrvStub.get()),
            isSuccess = true,
            errors = emptyList()
        )
    }

    override suspend fun searchByCompanyId(dbCompanyIdRequest: DbCompanyIdRequest): DbOrdersResponse {
        return DbOrdersResponse(
            data = listOf(ClSrvStub.get()),
            isSuccess = true,
            errors = emptyList()
        )
    }
}