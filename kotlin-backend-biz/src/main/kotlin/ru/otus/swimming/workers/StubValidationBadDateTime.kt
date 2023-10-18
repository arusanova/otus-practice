package ru.otus.swimming.workers

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.models.ClSrvError
import ru.otus.swimming.models.ClSrvState
import ru.otus.swimming.stubs.ClSrvStubs

fun ICorChainDsl<ClSrvContext>.stubValidationBadDateTime(title: String) =
    worker {
        this.title = title
        on { stubCase == ClSrvStubs.BAD_DATE_TIME && state == ClSrvState.RUNNING }
        handle {
            errors.add(
                ClSrvError(
                    code = ClSrvStubs.BAD_DATE_TIME.name,
                    group = "validation",
                    field = "datetime",
                    message = "Неправильно указана дата",
                    exception = null,
                )
            )
            state = ClSrvState.FAILING
        }
    }