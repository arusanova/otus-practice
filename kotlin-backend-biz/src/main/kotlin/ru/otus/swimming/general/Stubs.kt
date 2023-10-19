package ru.otus.swimming.groups

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.chain
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.models.ClSrvWorkMode

fun ICorChainDsl<ClSrvContext>.stubs(
    title: String,
    block: ICorChainDsl<ClSrvContext>.() -> Unit
) {
    chain {
        this.title = title
        on { this.workMode == ClSrvWorkMode.STUB }
        block()
    }
}