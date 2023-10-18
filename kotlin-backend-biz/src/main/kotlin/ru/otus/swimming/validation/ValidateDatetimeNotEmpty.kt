package ru.otus.swimming.validation

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.NONE
import ru.otus.swimming.helpers.fail
import ru.otus.swimming.models.ClSrvError

fun ICorChainDsl<ClSrvContext>.validateDatetimeNotEmpty(title: String) = worker {
    this.title = title
    on {
        this.orderValidating.dateTime == Instant.NONE
    }
    handle {
        fail(
            ClSrvError(
                code = "badFormat",
                group = "validation",
                field = "datetime",
                message = "Дата заказа должна быть указана"
            )
        )
    }
}