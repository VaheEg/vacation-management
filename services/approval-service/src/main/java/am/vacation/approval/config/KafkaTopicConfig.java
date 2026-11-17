package am.vacation.approval.config;

import am.vacation.common.events.Topics;
import am.vacation.common.messaging.EventTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** The topic this service publishes to, and its dead-letter topic. */
@Configuration
class KafkaTopicConfig {

  @Bean
  NewTopic approvalEventsTopic() {
    return EventTopics.events(Topics.APPROVAL, EventTopics.DEFAULT_RETENTION);
  }

  @Bean
  NewTopic approvalEventsDeadLetterTopic() {
    return EventTopics.deadLetter(Topics.APPROVAL);
  }
}
