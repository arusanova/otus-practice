package ru.otus.swimming.repo

import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.models.ClSrvState
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker

fun ICorChainDsl<ClSrvContext>.repoSearchByUserId(title: String) = worker {
    this.title = title
    description = "Получение заказа в БД"
    on { state == ClSrvState.RUNNING }
    handle {
        val request = DbUserIdRequest(orderRepoPrepare.userId)
        val result = orderRepo.searchByUserId(request)
        val resultOrder = result.data
        if (result.isSuccess && resultOrder != null) {
            ordersRepoDone = resultOrder.toMutableList()
        } else {
            state = ClSrvState.FAILING
            errors.addAll(result.errors)
        }
    }
}