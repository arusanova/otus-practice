package ru.otus.swimming.validation

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.helpers.fail
import ru.otus.swimming.models.ClSrvCompanyId
import ru.otus.swimming.models.ClSrvError

fun ICorChainDsl<ClSrvContext>.validateCompanyIdFormat(title: String) = worker {
    this.title = title
    on {
        val id = this.orderValidating.companyId.asString().toLongOrNull()
        this.orderValidating.companyId == ClSrvCompanyId.NONE || id == null || id <= 0
    }
    handle {
        fail(
            ClSrvError(
                code = "badFormat",
                group = "validation",
                field = "companyId",
                message = "Иденификатор компании должен быть целочисленым"
            )
        )
    }
}