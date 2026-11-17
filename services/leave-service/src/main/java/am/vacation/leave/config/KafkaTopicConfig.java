package am.vacation.leave.config;

import am.vacation.common.events.Topics;
import am.vacation.common.messaging.EventTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** The topic this service publishes to, and its dead-letter topic. */
@Configuration
class KafkaTopicConfig {

  @Bean
  NewTopic leaveEventsTopic() {
    return EventTopics.events(Topics.LEAVE, EventTopics.DEFAULT_RETENTION);
  }

  @Bean
  NewTopic leaveEventsDeadLetterTopic() {
    return EventTopics.deadLetter(Topics.LEAVE);
  }
}
