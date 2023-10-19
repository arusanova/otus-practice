package ru.otus.swimming.repo

import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.models.ClSrvState
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker

fun ICorChainDsl<ClSrvContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Получение заказа в БД"
    on { state == ClSrvState.RUNNING }
    handle {
        val request = DbOrderIdRequest(orderRepoPrepare.id)
        val result = orderRepo.readOrder(request)
        val resultOrder = result.data
        if (result.isSuccess && resultOrder != null) {
            orderRepoDone = resultOrder
        } else {
            state = ClSrvState.FAILING
            errors.addAll(result.errors)
        }
    }
}