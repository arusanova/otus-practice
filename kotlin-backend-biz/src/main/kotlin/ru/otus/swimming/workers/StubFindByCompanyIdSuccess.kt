package ru.otus.swimming.workers

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.ClSrvStub
import ru.otus.swimming.models.ClSrvState
import ru.otus.swimming.stubs.ClSrvStubs

fun ICorChainDsl<ClSrvContext>.stubFindByCompanyIdSuccess(title: String) =
    worker {
        this.title = title
        on { stubCase == ClSrvStubs.SUCCESS && state == ClSrvState.RUNNING }
        handle {
            ordersResponse.addAll(ClSrvStub.searchByCompanyIdStub(orderRequest))
            state = ClSrvState.FINISHING
        }
    }