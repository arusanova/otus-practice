package ru.otus.swimming

import RepoOrderSearchByUserIdTest
import ru.otus.swimming.repo.IOrderRepository

class OrderRepoInMemorySearchByUserIdTest : RepoOrderSearchByUserIdTest() {
    override val orderRepository: IOrderRepository = OrderRepoInMemory(initObjects = initObjects)
}