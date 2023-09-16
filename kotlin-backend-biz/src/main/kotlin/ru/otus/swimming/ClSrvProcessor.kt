package ru.otus.swimming

import ru.otus.swimming.models.ClSrvCommand
import ru.otus.swimming.models.ClSrvWorkMode

class ClSrvProcessor {
    suspend fun process(ctx: ClSrvContext) {
        require(ctx.workMode == ClSrvWorkMode.STUB) {
            "Currently working only in STUB mode."
        }
        when(ctx.command) {
            ClSrvCommand.NONE -> throw RuntimeException("Неправильно выставлена операция")
            ClSrvCommand.CREATE -> ctx.orderResponse = ClSrvStub.createOrderStub(ctx.orderRequest)
            ClSrvCommand.READ -> ctx.orderResponse = ClSrvStub.readOrderStub(ctx.orderRequest)
            ClSrvCommand.SEARCH_BY_USER_ID -> ctx.ordersResponse.addAll(ClSrvStub.searchByUserIdStub(ctx.orderRequest))
            ClSrvCommand.SEARCH_BY_COMPANY_ID -> ctx.ordersResponse.addAll(ClSrvStub.searchByCompanyIdStub(ctx.orderRequest))
        }
    }
}