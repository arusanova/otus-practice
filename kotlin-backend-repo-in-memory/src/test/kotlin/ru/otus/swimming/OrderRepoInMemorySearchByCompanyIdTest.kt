package ru.otus.swimming

import RepoOrderSearchByCompanyIdTest
import ru.otus.swimming.repo.IOrderRepository

class OrderRepoInMemorySearchByCompanyIdTest : RepoOrderSearchByCompanyIdTest() {
    override val orderRepository: IOrderRepository = OrderRepoInMemory(initObjects = initObjects)
}