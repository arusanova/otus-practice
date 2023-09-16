package ru.otus.swimming

import kotlinx.datetime.Instant
import ru.otus.swimming.models.ClSrvCompanyId
import ru.otus.swimming.models.ClSrvOrder
import ru.otus.swimming.models.ClSrvOrderId
import ru.otus.swimming.models.ClSrvUserId

object ClSrvStub {
    fun createOrderStub(clSrvRequest: ClSrvOrder): ClSrvOrder {
        return ClSrvOrder(
            userId = ClSrvUserId(id = clSrvRequest.userId.asString()),
            companyId = ClSrvCompanyId(id = clSrvRequest.companyId.asString()),
            id = ClSrvOrderId(id = "123"),
            dateTime = clSrvRequest.dateTime,
            address = clSrvRequest.address,
            service = clSrvRequest.service
        )
    }

    fun readOrderStub(clSrvRequest: ClSrvOrder): ClSrvOrder {
        return ClSrvOrder(
            userId = ClSrvUserId(id = "123"),
            companyId = ClSrvCompanyId(id = "123"),
            id = ClSrvOrderId(id = clSrvRequest.id.asString()),
            dateTime = Instant.parse("2023-09-01T21:00:00Z"),
            address = "Москва, ул Зорге 3к1",
            service = "Аквакомплекс Лужники"
        )
    }

    fun searchByUserIdStub(clSrvRequest: ClSrvOrder): List<ClSrvOrder>{
        return ClSrvOrder(
            userId = ClSrvUserId(id = clSrvRequest.userId.asString()),
            companyId = ClSrvCompanyId(id = "123"),
            id = ClSrvOrderId(id = "123"),
            dateTime = Instant.parse("2023-09-01T21:00:00Z"),
            address = "Москва, ул Зорге 3к1",
            service = "Аквакомплекс Лужники"
        ).let { listOf(it) }
    }

    fun searchByCompanyIdStub(clSrvRequest: ClSrvOrder): List<ClSrvOrder> {
        return ClSrvOrder(
            userId = ClSrvUserId(id = "123"),
            companyId = ClSrvCompanyId(id = clSrvRequest.companyId.asString()),
            id = ClSrvOrderId(id = "123"),
            dateTime = Instant.parse("2023-09-01T21:00:00Z"),
            address = "Москва, ул Зорге 3к1",
            service = "Аквакомплекс Лужники"
        ).let { listOf(it) }
    }
}