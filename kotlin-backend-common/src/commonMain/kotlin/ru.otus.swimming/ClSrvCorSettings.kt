package ru.otus.swimming

import ru.otus.swimming.repo.IOrderRepository


data class ClSrvCorSettings(
    val repoStub: IOrderRepository = IOrderRepository.NONE,
    val repoTest: IOrderRepository = IOrderRepository.NONE,
    val repoProd: IOrderRepository = IOrderRepository.NONE,
) {
    companion object {
        val NONE = ClSrvCorSettings()
    }
}