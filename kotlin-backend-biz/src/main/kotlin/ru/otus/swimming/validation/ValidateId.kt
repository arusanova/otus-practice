package ru.otus.swimming.validation

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.helpers.fail
import ru.otus.swimming.models.ClSrvError
import ru.otus.swimming.models.ClSrvOrderId

fun ICorChainDsl<ClSrvContext>.validateIdFormat(title: String) = worker {
    this.title = title
    on {
        val id = this.orderValidating.id.asString().toLongOrNull()
        this.orderValidating.id == ClSrvOrderId.NONE || id == null || id <= 0
    }
    handle {
        fail(
            ClSrvError(
                code = "badFormat",
                group = "validation",
                field = "id",
                message = "Иденификатор заявки должен быть целым числом"
            )
        )
    }
}