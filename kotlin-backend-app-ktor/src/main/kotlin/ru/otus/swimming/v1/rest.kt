package ru.otus.swimming.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.swimming.ClSrvProcessor

fun Route.v1Order(processor: ClSrvProcessor) {
    route("order") {
        post("create") {
            call.create(processor)
        }
        post("readById") {
            call.readById(processor)
        }
        post("searchByUserId") {
            call.searchByUserId(processor)
        }
        post("searchByCompanyId") {
            call.searchByCompanyId(processor)
        }
    }
}