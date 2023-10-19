package ru.otus.swimming

import RepoOrderCreateTest
import ru.otus.swimming.repo.IOrderRepository

class OrderRepoInMemoryCreateTest : RepoOrderCreateTest() {
    override val orderRepository: IOrderRepository = OrderRepoInMemory(initObjects = initObjects)
}