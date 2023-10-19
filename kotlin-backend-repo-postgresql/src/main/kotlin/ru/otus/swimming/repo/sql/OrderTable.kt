package ru.otus.swimming.repo.sql

import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.swimming.models.*

object OrderTable : Table("order") {
    val id = varchar("id", 128)
    val companyId = varchar("companyId", 128)
    val userId = varchar("userId", 128)
    val address = varchar("address", 128)
    val datetime = varchar("datetime", 128)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = ClSrvOrder(
        id = ClSrvOrderId(res[id].toString()),
        lock = ClSrvOrderLock(res[lock]),
        userId = ClSrvUserId(id = res[userId]),
        companyId = ClSrvCompanyId(id = res[companyId]),
        dateTime = Instant.parse(res[datetime]),
        address = res[address]
    )

    fun from(res: ResultRow) = ClSrvOrder(
        id = ClSrvOrderId(res[id].toString()),
        lock = ClSrvOrderLock(res[lock]),
        userId = ClSrvUserId(id = res[userId]),
        companyId = ClSrvCompanyId(id = res[companyId]),
        dateTime = Instant.parse(res[datetime]),
        address = res[address]
    )

    fun to(it: UpdateBuilder<*>, order: ClSrvOrder, randomUuid: () -> String) {
        it[id] = order.id.takeIf { it != ClSrvOrderId.NONE }?.asString() ?: randomUuid()
        it[companyId] = order.companyId.takeIf { it != ClSrvCompanyId.NONE }?.asString() ?: throw RuntimeException("Отсутствует companyId")
        it[userId] = order.userId.takeIf { it != ClSrvUserId.NONE }?.asString() ?: throw RuntimeException("Отсутствует userId")
        it[address] = order.address
        it[datetime] = order.dateTime.toString()
        it[lock] = order.lock.takeIf { it != ClSrvOrderLock.NONE }?.asString() ?: randomUuid()
    }
}