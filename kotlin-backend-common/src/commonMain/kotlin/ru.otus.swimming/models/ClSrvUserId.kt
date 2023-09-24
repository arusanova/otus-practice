package ru.otus.swimming.models

import kotlin.jvm.JvmInline

@JvmInline
value class ClSrvUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ClSrvUserId("")
    }
}