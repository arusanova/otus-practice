package ru.otus.swimming

import ru.otus.swimming.v1.v1Order

import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.event.Level

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args = args)

fun Application.moduleJvm() {
    val processor = ClSrvProcessor()

    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost() // TODO remove
    }

    install(CallLogging) {
        level = Level.INFO
    }

    @Suppress("OPT_IN_USAGE")
    install(Locations)

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        route("v1") {
            install(ContentNegotiation) {
                jackson {
                    setConfig(apiV1Mapper.serializationConfig)
                    setConfig(apiV1Mapper.deserializationConfig)
                }
            }

            v1Order(processor)
        }

        static("static") {
            resources("static")
        }
    }
}