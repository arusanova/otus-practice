package ru.otus.swimming.workers

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.models.ClSrvState

fun ICorChainDsl<ClSrvContext>.initStatus(title: String) =
    worker {
        this.title = title
        on { state == ClSrvState.NONE }
        handle { state = ClSrvState.RUNNING }
    }