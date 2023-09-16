package ru.otus.swimming.models

import kotlinx.datetime.Instant
import ru.otus.swimming.NONE

data class ClSrvOrder (
    val userId: ClSrvUserId = ClSrvUserId.NONE,
    val companyId: ClSrvCompanyId = ClSrvCompanyId.NONE,
    val id: ClSrvOrderId = ClSrvOrderId.NONE,
    val dateTime: Instant = Instant.NONE,
    val address: String = "",
    val service: String = "",
)