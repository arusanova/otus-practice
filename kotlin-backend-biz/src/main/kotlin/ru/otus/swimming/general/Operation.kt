package ru.otus.swimming.groups

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.chain
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.models.ClSrvCommand
import ru.otus.swimming.models.ClSrvState


fun ICorChainDsl<ClSrvContext>.operation(
    title: String,
    command: ClSrvCommand,
    block: ICorChainDsl<ClSrvContext>.() -> Unit
) {
    chain {
        this.title = title
        on { this.command == command && this.state == ClSrvState.RUNNING}
        block()
    }
}