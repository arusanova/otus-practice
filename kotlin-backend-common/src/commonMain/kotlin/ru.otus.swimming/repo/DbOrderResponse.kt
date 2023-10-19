package ru.otus.swimming.repo

import ru.otus.swimming.helpers.errorEmptyId
import ru.otus.swimming.models.ClSrvError
import ru.otus.swimming.models.ClSrvOrder
import ru.otus.swimming.helpers.errorNotFound as clSrvErrorNotFound

data class DbOrderResponse(
    override val data: ClSrvOrder?,
    override val isSuccess: Boolean,
    override val errors: List<ClSrvError> = emptyList()
) : IDbResponse<ClSrvOrder> {
    companion object {
        fun success(result: ClSrvOrder) = DbOrderResponse(result, true)
        fun error(errors: List<ClSrvError>, data: ClSrvOrder? = null) = DbOrderResponse(data, false, errors)
        fun error(error: ClSrvError, data: ClSrvOrder? = null) = DbOrderResponse(data, false, listOf(error))

        val errorNotFound = error(clSrvErrorNotFound)
        val errorBadId = error(errorEmptyId)
    }
}