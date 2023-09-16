package ru.otus.swimming

import ru.otus.swimming.api.v1.models.IResponse
import ru.otus.swimming.api.v1.models.OrderCreateResponse
import ru.otus.swimming.api.v1.models.OrderReadResponse
import ru.otus.swimming.api.v1.models.OrderResponseObject
import ru.otus.swimming.api.v1.models.OrderSearchByCompanyIdResponse
import ru.otus.swimming.api.v1.models.OrderSearchByUserIdResponse
import ru.otus.swimming.exception.UnknownClSrvCommand
import ru.otus.swimming.models.ClSrvCommand
import ru.otus.swimming.models.ClSrvCompanyId
import ru.otus.swimming.models.ClSrvError
import ru.otus.swimming.models.ClSrvOrder
import ru.otus.swimming.models.ClSrvOrderId
import ru.otus.swimming.models.ClSrvUserId

fun ClSrvContext.toTransport(): IResponse = when (val cmd = command) {
    ClSrvCommand.CREATE -> toTransportCreate()
    ClSrvCommand.READ -> toTransportRead()
    ClSrvCommand.SEARCH_BY_COMPANY_ID -> toTransportSearchByCompanyId()
    ClSrvCommand.SEARCH_BY_USER_ID -> toTransportSearchByUserId()
    ClSrvCommand.NONE -> throw UnknownClSrvCommand(cmd)
}

fun ClSrvContext.toTransportCreate() = OrderCreateResponse(
    responseType = "create",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    errors = this.errors.toTransportErrors(),
    order = orderResponse.toTransportOrder(),
)

fun ClSrvContext.toTransportRead() = OrderReadResponse(
    responseType = "read",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    errors = this.errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun ClSrvContext.toTransportSearchByCompanyId() = OrderSearchByCompanyIdResponse(
    responseType = "search_by_company_id",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    errors = this.errors.toTransportErrors(),
    orders = ordersResponse.toTransportOrder()
)

fun ClSrvContext.toTransportSearchByUserId() = OrderSearchByUserIdResponse(
    responseType = "search_by_user_id",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    errors = this.errors.toTransportErrors(),
    orders = ordersResponse.toTransportOrder()
)

fun List<ClSrvOrder>.toTransportOrder(): List<OrderResponseObject>? = this
    .map { it.toTransportOrder() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ClSrvOrder.toTransportOrder(): OrderResponseObject = OrderResponseObject(
    id = id.takeIf { it != ClSrvOrderId.NONE }?.asString(),
    userId = userId.takeIf { it != ClSrvUserId.NONE }?.asString(),
    companyId = companyId.takeIf { it != ClSrvCompanyId.NONE }?.asString(),
    address = address,
    dateTime = dateTime.toString()
)

private fun List<ClSrvError>.toTransportErrors(): List<ru.otus.swimming.api.v1.models.Error>? = this
    .map { it.toTransportOrder() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ClSrvError.toTransportOrder() = ru.otus.swimming.api.v1.models.Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)