package ru.otus.swimming.repo

interface IOrderRepository {
    suspend fun createOrder(dbOrderRequest: DbOrderRequest): DbOrderResponse
    suspend fun readOrder(dbOrderIdRequest: DbOrderIdRequest): DbOrderResponse
    suspend fun searchByUserId(dbUserIdRequest: DbUserIdRequest): DbOrdersResponse
    suspend fun searchByCompanyId(dbCompanyIdRequest: DbCompanyIdRequest): DbOrdersResponse

    companion object {
        val NONE = object : IOrderRepository {
            override suspend fun createOrder(dbOrderRequest: DbOrderRequest): DbOrderResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readOrder(dbOrderIdRequest: DbOrderIdRequest): DbOrderResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchByUserId(dbUserIdRequest: DbUserIdRequest): DbOrdersResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchByCompanyId(dbCompanyIdRequest: DbCompanyIdRequest): DbOrdersResponse {
                TODO("Not yet implemented")
            }

        }
    }
}