package ru.otus.swimming.validation

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.chain
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.models.ClSrvState

fun ICorChainDsl<ClSrvContext>.validation(
    title: String,
    block: ICorChainDsl<ClSrvContext>.() -> Unit
) = chain {
    this.title = title
    on { this.state == ClSrvState.RUNNING}
    block()
}