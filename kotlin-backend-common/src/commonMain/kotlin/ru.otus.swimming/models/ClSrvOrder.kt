package ru.otus.swimming.models

import kotlinx.datetime.Instant
import ru.otus.swimming.NONE

data class ClSrvOrder (
    var userId: ClSrvUserId = ClSrvUserId.NONE,
    var companyId: ClSrvCompanyId = ClSrvCompanyId.NONE,
    var id: ClSrvOrderId = ClSrvOrderId.NONE,
    var dateTime: Instant = Instant.NONE,
    var address: String = "",
    var service: String = "",
    var lock: ClSrvOrderLock = ClSrvOrderLock.NONE,
)