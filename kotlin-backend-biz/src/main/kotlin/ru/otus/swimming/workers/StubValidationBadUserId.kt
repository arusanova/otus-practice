package ru.otus.swimming.workers

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.models.ClSrvError
import ru.otus.swimming.models.ClSrvState
import ru.otus.swimming.stubs.ClSrvStubs

fun ICorChainDsl<ClSrvContext>.stubValidationBadUserId(title: String) =
    worker {
        this.title = title
        on { stubCase == ClSrvStubs.BAD_USER_ID && state == ClSrvState.RUNNING }
        handle {
            errors.add(
                ClSrvError(
                    code = ClSrvStubs.BAD_USER_ID.name,
                    group = "validation",
                    field = "userId",
                    message = "Неправильно указан идентификатор пользователя",
                    exception = null,
                )
            )
            state = ClSrvState.FAILING
        }
    }