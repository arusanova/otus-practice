package ru.otus.swimming

import RepoOrderReadTest
import ru.otus.swimming.repo.IOrderRepository

class OrderRepoInMemoryReadTest : RepoOrderReadTest() {
    override val orderRepository: IOrderRepository = OrderRepoInMemory(initObjects = initObjects)
}