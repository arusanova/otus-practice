package ru.otus.swimming.repo

import ru.otus.swimming.models.ClSrvError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<ClSrvError>
}
