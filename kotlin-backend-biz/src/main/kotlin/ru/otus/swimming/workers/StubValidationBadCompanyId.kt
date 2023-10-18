package ru.otus.swimming.workers

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.models.ClSrvError
import ru.otus.swimming.models.ClSrvState
import ru.otus.swimming.stubs.ClSrvStubs

fun ICorChainDsl<ClSrvContext>.stubValidationBadCompanyId(title: String) =
    worker {
        this.title = title
        on { stubCase == ClSrvStubs.BAD_COMPANY_ID && state == ClSrvState.RUNNING }
        handle {
            errors.add(
                ClSrvError(
                    code = ClSrvStubs.BAD_COMPANY_ID.name,
                    group = "validation",
                    field = "companyId",
                    message = "Не правильно указан идентификатор компании",
                    exception = null,
                )
            )
            state = ClSrvState.FAILING
        }
    }