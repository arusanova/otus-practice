package ru.otus.swimming.validation

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.helpers.fail
import ru.otus.swimming.models.ClSrvError
import ru.otus.swimming.models.ClSrvUserId

fun ICorChainDsl<ClSrvContext>.validateUserIdFormat(title: String) = worker {
    this.title = title
    on {
        val id = this.orderValidating.userId.asString().toLongOrNull()
        this.orderValidating.userId == ClSrvUserId.NONE || id == null || id <= 0
    }
    handle {
        fail(
            ClSrvError(
                code = "badFormat",
                group = "validation",
                field = "userId",
                message = "Иденификатор клиента должен быть целочисленым"
            )
        )
    }
}