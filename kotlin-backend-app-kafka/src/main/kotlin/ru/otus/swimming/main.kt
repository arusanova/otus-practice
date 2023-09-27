package ru.otus.swimming

fun main() {
    val config = AppKafkaConfig()
    val listener = OrderListener(config = config, processor = ClSrvProcessor())
    listener.run()
}