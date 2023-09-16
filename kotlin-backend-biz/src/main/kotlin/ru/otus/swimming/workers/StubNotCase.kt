package ru.otus.swimming.workers

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.models.ClSrvError
import ru.otus.swimming.models.ClSrvState

fun ICorChainDsl<ClSrvContext>.stubNoCase(title: String) =
    worker {
        this.title = title
        on { state == ClSrvState.RUNNING }
        handle {
            errors.add(
                ClSrvError(
                    code = "validation",
                    group = "validation",
                    field = "stub",
                    message = "Отсутствует stub case",
                    exception = null,
                )
            )
            state = ClSrvState.FAILING
        }
    }