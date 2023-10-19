package ru.otus.swimming.model

import kotlinx.datetime.Instant
import ru.otus.swimming.NONE
import ru.otus.swimming.models.*

data class OrderEntity(
    val userId: String? = null,
    val companyId: String? = null,
    val id: String? = null,
    val address: String? = null,
    val lock: String? = null,
    val datetime: Instant? = null
) {
    constructor(clSrvOrder: ClSrvOrder) : this(
        userId = clSrvOrder.userId.asString().takeIf { it.isNotBlank() },
        companyId = clSrvOrder.companyId.asString().takeIf { it.isNotBlank() },
        id = clSrvOrder.id.asString().takeIf { it.isNotBlank() },
        address = clSrvOrder.address.takeIf { it.isNotBlank() },
        lock = clSrvOrder.lock.asString().takeIf { it.isNotBlank() },
        datetime = clSrvOrder.dateTime.takeIf { it != Instant.NONE }
    )

    fun toInternal() = ClSrvOrder(
        userId = userId?.let { ClSrvUserId(id = it) } ?: ClSrvUserId.NONE,
        companyId = companyId?.let { ClSrvCompanyId(id = it) } ?: ClSrvCompanyId.NONE,
        id = id?.let { ClSrvOrderId(id = it) } ?: ClSrvOrderId.NONE,
        dateTime = datetime ?: Instant.NONE,
        address = address ?: "",
        lock = lock?.let { ClSrvOrderLock(id = it) } ?: ClSrvOrderLock.NONE,
    )
}