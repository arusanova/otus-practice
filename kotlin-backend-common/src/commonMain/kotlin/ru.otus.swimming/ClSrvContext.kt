package ru.otus.swimming

import kotlinx.datetime.Instant
import ru.otus.swimming.models.ClSrvCommand
import ru.otus.swimming.models.ClSrvError
import ru.otus.swimming.models.ClSrvOrder
import ru.otus.swimming.models.ClSrvRequestId
import ru.otus.swimming.models.ClSrvState
import ru.otus.swimming.models.ClSrvWorkMode
import ru.otus.swimming.stubs.ClSrvStubs

data class ClSrvContext (
    var command: ClSrvCommand = ClSrvCommand.NONE,
    var state: ClSrvState = ClSrvState.NONE,
    val errors: MutableList<ClSrvError> = mutableListOf(),

    var workMode: ClSrvWorkMode = ClSrvWorkMode.PROD,
    var stubCase: ClSrvStubs = ClSrvStubs.NONE,

    var requestId: ClSrvRequestId = ClSrvRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var orderRequest: ClSrvOrder = ClSrvOrder(),
    var orderResponse: ClSrvOrder = ClSrvOrder(),
    val ordersResponse: MutableList<ClSrvOrder> = mutableListOf()
)