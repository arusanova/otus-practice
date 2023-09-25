package ru.otus.swimming.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.swimming.ClSrvContext
import ru.otus.swimming.ClSrvProcessor
import ru.otus.swimming.api.v1.models.OrderCreateRequest
import ru.otus.swimming.api.v1.models.OrderReadRequest
import ru.otus.swimming.api.v1.models.OrderSearchByCompanyIdRequest
import ru.otus.swimming.api.v1.models.OrderSearchByUserIdRequest
import ru.otus.swimming.fromTransport
import ru.otus.swimming.toTransport

suspend fun ApplicationCall.create(processor: ClSrvProcessor) {
    val request = receive<OrderCreateRequest>()
    val context = ClSrvContext()
    context.fromTransport(request)
    processor.process(context)
    respond(context.toTransport())
}

suspend fun ApplicationCall.readById(processor: ClSrvProcessor) {
    val request = receive<OrderReadRequest>()
    val context = ClSrvContext()
    context.fromTransport(request)
    processor.process(context)
    respond(context.toTransport())
}

suspend fun ApplicationCall.searchByUserId(processor: ClSrvProcessor) {
    val request = receive<OrderSearchByUserIdRequest>()
    val context = ClSrvContext()
    context.fromTransport(request)
    processor.process(context)
    respond(context.toTransport())
}

suspend fun ApplicationCall.searchByCompanyId(processor: ClSrvProcessor) {
    val request = receive<OrderSearchByCompanyIdRequest>()
    val context = ClSrvContext()
    context.fromTransport(request)
    processor.process(context)
    respond(context.toTransport())
}