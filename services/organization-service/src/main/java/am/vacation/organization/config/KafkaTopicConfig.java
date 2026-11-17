package am.vacation.organization.config;

import am.vacation.common.events.Topics;
import am.vacation.common.messaging.EventTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** The topic this service publishes to, and its dead-letter topic. */
@Configuration
class KafkaTopicConfig {

  @Bean
  NewTopic organizationEventsTopic() {
    return EventTopics.events(Topics.ORGANIZATION, EventTopics.UNLIMITED_RETENTION);
  }

  @Bean
  NewTopic organizationEventsDeadLetterTopic() {
    return EventTopics.deadLetter(Topics.ORGANIZATION);
  }
}
