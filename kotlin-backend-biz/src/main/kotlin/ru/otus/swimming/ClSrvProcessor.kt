package ru.otus.swimming

import ru.otus.swimming.models.ClSrvCommand
import ru.otus.swimming.models.ClSrvOrderId
import ru.otus.swimming.validation.finishOrderValidation
import ru.otus.swimming.validation.validation
import ru.otus.swimming.workers.initStatus
import ru.otus.swimming.workers.stubCreateSuccess
import ru.otus.swimming.workers.stubNoCase
import ru.otus.otuskotlin.marketplace.cor.rootChain
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.swimming.groups.operation
import ru.otus.swimming.groups.stubs
import ru.otus.swimming.validation.validateAddressNotEmpty
import ru.otus.swimming.validation.validateCompanyIdFormat
import ru.otus.swimming.validation.validateDatetimeNotEmpty
import ru.otus.swimming.validation.validateIdFormat
import ru.otus.swimming.validation.validateServiceNotEmpty
import ru.otus.swimming.validation.validateUserIdFormat
import ru.otus.swimming.workers.stubFindByCompanyIdSuccess
import ru.otus.swimming.workers.stubFindByUserIdSuccess
import ru.otus.swimming.workers.stubReadSuccess
import ru.otus.swimming.workers.stubValidationBadCompanyId
import ru.otus.swimming.workers.stubValidationBadDateTime
import ru.otus.swimming.workers.stubValidationBadUserId
import ru.otus.swimming.workers.stubValidationNotFound

class ClSrvProcessor {
    suspend fun process(ctx: ClSrvContext) = BusinessChain.exec(ctx)

    companion object {
        private val BusinessChain = rootChain {
            initStatus("Инициализация контекста")
            operation("Создание заказа", ClSrvCommand.CREATE) {
                stubs(title = "Обработка стабов") {
                    stubCreateSuccess("Имитация создания заказа")
                    stubValidationBadDateTime("Имитация неправильной даты заказа")
                    stubValidationBadCompanyId("Имитация неправльного идентификатора компании")
                    stubValidationBadUserId("Имитация неправильного идентификатороа пользователя")
                    stubNoCase("Ошибка: Запрошенный stub отсутствует")
                }
                validation("Валидация создания заказа") {
                    worker("Копируем поля в orderValidating") { orderValidating = orderRequest }
                    worker("Очистка id") { orderValidating.id = ClSrvOrderId.NONE }
                    worker("Очистка аддрес") { orderValidating.address = orderValidating.address.trim() }
                    validateAddressNotEmpty("Проверка, что адрес указан")
                    validateServiceNotEmpty("Проверка, что услуга указана")
                    validateCompanyIdFormat("Проверка формата идентификатора компании")
                    validateUserIdFormat("Проверка формата идентификатора пользователя")
                    validateDatetimeNotEmpty("Проверка наличия времени заказа")
                    finishOrderValidation("Завершение валидации")
                }
            }

            operation("Получение заказа", ClSrvCommand.READ) {
                stubs(title = "Обработка стабов") {
                    stubReadSuccess("Получение заказа по идентификатору")
                    stubValidationNotFound("Имитация отсутстие заказа")
                    stubNoCase("Ошибка: Запрошенный stub отсутствует")
                }
                validation("Валидация получения заказа") {
                    worker("Копируем поля в orderValidating") { orderValidating = orderRequest }
                    validateIdFormat("Проверка формата идентификатора пользователя")
                    finishOrderValidation("Завершение валидации")
                }
            }

            operation("Получение заказов компании", ClSrvCommand.SEARCH_BY_COMPANY_ID) {
                stubs(title = "Обработка стабов") {
                    stubFindByCompanyIdSuccess("Получение заказов по идентификатору компании")
                    stubValidationNotFound("Имитация отсутствия компании")
                    stubValidationBadCompanyId("Имитация неправильного идентификатора компании")
                    stubNoCase("Ошибка: Запрошенный stub отсутствует")
                }
                validation("Валидация получение заказов по идентификатору компании") {
                    worker("Копируем поля в orderValidating") { orderValidating = orderRequest }
                    validateCompanyIdFormat("Проверка формата идентификатора компании")
                    finishOrderValidation("Завершение валидации")
                }
            }

            operation("Получение заказов пользователя", ClSrvCommand.SEARCH_BY_USER_ID) {
                stubs(title = "Обработка стабов") {
                    stubFindByUserIdSuccess("Получение заказов по идентификатору клиента")
                    stubValidationNotFound("Имитация отсутстия пользователя")
                    stubValidationBadUserId("Имитация неправильного идентификатороа пользователя")
                    stubNoCase("Ошибка: Запрошенный stub отсутствует")
                }
                validation("Валидация получение заказов по идентификатору пользователя") {
                    worker("Копируем поля в orderValidating") { orderValidating = orderRequest }
                    validateUserIdFormat("Проверка формата идентификатора пользователя")
                    finishOrderValidation("Завершение валидации")
                }
            }
        }.build()
    }
}