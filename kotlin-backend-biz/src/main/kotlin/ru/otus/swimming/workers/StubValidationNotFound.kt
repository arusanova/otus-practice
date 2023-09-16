package ru.otus.swimming.workers

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.models.ClSrvError
import ru.otus.swimming.models.ClSrvState
import ru.otus.swimming.stubs.ClSrvStubs

fun ICorChainDsl<ClSrvContext>.stubValidationNotFound(title: String) =
    worker {
        this.title = title
        on { stubCase == ClSrvStubs.NOT_FOUND && state == ClSrvState.RUNNING }
        handle {
            errors.add(
                ClSrvError(
                    code = ClSrvStubs.NOT_FOUND.name,
                    group = "validation",
                    field = "",
                    message = "Ресурс не найден",
                    exception = null,
                )
            )
            state = ClSrvState.FAILING
        }
    }