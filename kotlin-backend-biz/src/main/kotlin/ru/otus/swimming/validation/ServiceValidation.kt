package ru.otus.swimming.validation

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.helpers.fail
import ru.otus.swimming.models.ClSrvError

fun ICorChainDsl<ClSrvContext>.validateServiceNotEmpty(title: String) = worker {
    this.title = title
    on { this.orderValidating.service.isEmpty() }
    handle {
        fail(
            ClSrvError(
                code = "badFormat",
                group = "validation",
                field = "service",
                message = "Услуга не должен быть пустой"
            )
        )
    }
}