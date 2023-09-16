package ru.otus.swimming.helpers

import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.models.ClSrvError
import ru.otus.swimming.models.ClSrvState

fun ClSrvContext.fail(error: ClSrvError) {
    addError(error)
    this.state = ClSrvState.FAILING
}

fun ClSrvContext.addError(error: ClSrvError) = this.errors.add(error)