package ru.otus.swimming.validation

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.models.ClSrvState

fun ICorChainDsl<ClSrvContext>.finishOrderValidation(title: String) = worker {
    this.title = title
    on { state == ClSrvState.RUNNING }
    handle {
        orderValidated = orderValidating
    }
}