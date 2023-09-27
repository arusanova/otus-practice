package ru.otus.swimming

class AppKafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
    val ordersTopicIn: String = ORDERS_TOPIC_IN,
    val ordersTopicOut: String = ORDERS_TOPIC_OUT,
) {
    companion object {
        const val KAFKA_HOST_VAR = "KAFKA_HOSTS"
        const val ORDERS_TOPIC_IN_VAR = "ORDERS_TOPIC_IN"
        const val ORDERS_TOPIC_OUT_VAR = "ORDERS_TOPIC_OUT"
        const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"

        val KAFKA_HOSTS by lazy { (System.getenv(KAFKA_HOST_VAR) ?: "localhost:9091").split("\\s*[,;]\\s*") }
        val KAFKA_GROUP_ID by lazy { System.getenv(KAFKA_GROUP_ID_VAR) ?: "cleaning-service" }
        val ORDERS_TOPIC_IN by lazy { System.getenv(ORDERS_TOPIC_IN_VAR) ?: "orders-topic-in" }
        val ORDERS_TOPIC_OUT by lazy { System.getenv(ORDERS_TOPIC_OUT_VAR) ?: "orders-topic-out" }
    }
}