package am.vacation.common.messaging;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;

/** Messaging beans every service that uses Kafka gets by depending on this library. */
@AutoConfiguration(
    afterName = "org.springframework.boot.kafka.autoconfigure.KafkaAutoConfiguration")
@ConditionalOnBean(KafkaAdmin.class)
public class MessagingAutoConfiguration {

  /** Registered as the {@code kafka} health component, part of the readiness group. */
  @Bean
  KafkaHealthIndicator kafkaHealthIndicator(KafkaAdmin kafkaAdmin) {
    return new KafkaHealthIndicator(kafkaAdmin.getConfigurationProperties());
  }
}
