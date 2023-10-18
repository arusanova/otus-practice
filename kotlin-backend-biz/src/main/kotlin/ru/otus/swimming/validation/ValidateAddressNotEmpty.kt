package ru.otus.swimming.validation

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.helpers.fail
import ru.otus.swimming.models.ClSrvError

fun ICorChainDsl<ClSrvContext>.validateAddressNotEmpty(title: String) = worker {
    this.title = title
    on { this.orderValidating.address.isEmpty() }
    handle {
        fail(
            ClSrvError(
                code = "badFormat",
                group = "validation",
                field = "address",
                message = "Аддрес не должен быть пустым"
            )
        )
    }
}