package ru.otus.swimming

import kotlinx.datetime.Instant
import ru.otus.swimming.api.v1.models.IRequest
import ru.otus.swimming.api.v1.models.OrderCreateObject
import ru.otus.swimming.api.v1.models.OrderCreateRequest
import ru.otus.swimming.api.v1.models.OrderDebug
import ru.otus.swimming.api.v1.models.OrderReadObject
import ru.otus.swimming.api.v1.models.OrderReadRequest
import ru.otus.swimming.api.v1.models.OrderRequestDebugMode
import ru.otus.swimming.api.v1.models.OrderRequestDebugStubs
import ru.otus.swimming.api.v1.models.OrderSearchByCompanyIdObject
import ru.otus.swimming.api.v1.models.OrderSearchByCompanyIdRequest
import ru.otus.swimming.api.v1.models.OrderSearchByUserIdObject
import ru.otus.swimming.api.v1.models.OrderSearchByUserIdRequest
import ru.otus.swimming.exception.UnknownRequestClass
import ru.otus.swimming.models.ClSrvCommand
import ru.otus.swimming.models.ClSrvCompanyId
import ru.otus.swimming.models.ClSrvOrder
import ru.otus.swimming.models.ClSrvOrderId
import ru.otus.swimming.models.ClSrvRequestId
import ru.otus.swimming.models.ClSrvUserId
import ru.otus.swimming.models.ClSrvWorkMode
import ru.otus.swimming.stubs.ClSrvStubs

fun ClSrvContext.fromTransport(request: IRequest) = when(request) {
    is OrderCreateRequest -> fromTransport(request)
    is OrderReadRequest -> fromTransport(request)
    is OrderSearchByCompanyIdRequest -> fromTransport(request)
    is OrderSearchByUserIdRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

fun ClSrvContext.fromTransport(request: OrderCreateRequest) {
    command = ClSrvCommand.CREATE
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    orderRequest = request.order?.toInternal() ?: orderRequest
}

fun ClSrvContext.fromTransport(request: OrderReadRequest) {
    command = ClSrvCommand.READ
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    orderRequest = request.order?.toInternal() ?: orderRequest
}

fun ClSrvContext.fromTransport(request: OrderSearchByCompanyIdRequest) {
    command = ClSrvCommand.SEARCH_BY_COMPANY_ID
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    orderRequest = request.order?.toInternal() ?: orderRequest
}

fun ClSrvContext.fromTransport(request: OrderSearchByUserIdRequest) {
    command = ClSrvCommand.SEARCH_BY_USER_ID
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    orderRequest = request.order?.toInternal() ?: orderRequest
}

private fun OrderCreateObject.toInternal() = ClSrvOrder(
    userId = userId?.takeIf { it.isNotBlank() }?.let { ClSrvUserId(id = it) } ?: ClSrvUserId.NONE,
    companyId = companyId?.takeIf { it.isNotBlank() }?.let { ClSrvCompanyId(id = it) } ?: ClSrvCompanyId.NONE,
    dateTime = dateTime?.takeIf { it.isNotBlank() }?.let { Instant.parse(it) } ?: Instant.NONE,
    address = address ?: "",
    service = service ?: "",
)

private fun OrderReadObject.toInternal() = ClSrvOrder(id = this.id?.let { ClSrvOrderId(it) } ?: ClSrvOrderId.NONE)

private fun OrderSearchByCompanyIdObject.toInternal() = ClSrvOrder(
    companyId = companyId?.takeIf { it.isNotBlank() }?.let { ClSrvCompanyId(id = it) } ?: ClSrvCompanyId.NONE
)

private fun OrderSearchByUserIdObject.toInternal() = ClSrvOrder(
    userId = userId?.takeIf { it.isNotBlank() }?.let { ClSrvUserId(id = it) } ?: ClSrvUserId.NONE
)

private fun IRequest?.requestId() = this?.requestId?.let { ClSrvRequestId(it) } ?: ClSrvRequestId.NONE

private fun OrderDebug?.transportToWorkMode(): ClSrvWorkMode = when (this?.mode) {
    OrderRequestDebugMode.PROD -> ClSrvWorkMode.PROD
    OrderRequestDebugMode.TEST -> ClSrvWorkMode.TEST
    OrderRequestDebugMode.STUB -> ClSrvWorkMode.STUB
    null -> ClSrvWorkMode.PROD
}

private fun OrderDebug?.transportToStubCase(): ClSrvStubs = when (this?.stub) {
    OrderRequestDebugStubs.SUCCESS -> ClSrvStubs.SUCCESS
    OrderRequestDebugStubs.NOT_FOUND -> ClSrvStubs.NOT_FOUND
    OrderRequestDebugStubs.BAD_ID -> ClSrvStubs.BAD_ID
    OrderRequestDebugStubs.BAD_COMPANY_ID -> ClSrvStubs.BAD_COMPANY_ID
    OrderRequestDebugStubs.BAD_USER_ID -> ClSrvStubs.BAD_USER_ID
    OrderRequestDebugStubs.BAD_DATE_TIME -> ClSrvStubs.BAD_DATE_TIME
    null -> ClSrvStubs.NONE
}