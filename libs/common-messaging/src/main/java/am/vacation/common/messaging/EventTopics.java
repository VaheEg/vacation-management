package am.vacation.common.messaging;

import java.time.Duration;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.kafka.config.TopicBuilder;

/** Builds the topic definitions a publishing service declares as beans. */
public final class EventTopics {

  public static final Duration UNLIMITED_RETENTION = Duration.ofMillis(-1);
  public static final Duration DEFAULT_RETENTION = Duration.ofDays(30);
  public static final String DEAD_LETTER_SUFFIX = ".dlt";

  private static final int EVENT_PARTITIONS = 6;
  private static final int DEAD_LETTER_PARTITIONS = 1;

  private EventTopics() {}

  public static NewTopic events(String name, Duration retention) {
    return topic(name, EVENT_PARTITIONS, retention);
  }

  public static NewTopic deadLetter(String eventTopicName) {
    return topic(eventTopicName + DEAD_LETTER_SUFFIX, DEAD_LETTER_PARTITIONS, DEFAULT_RETENTION);
  }

  private static NewTopic topic(String name, int partitions, Duration retention) {
    return TopicBuilder.name(name)
        .partitions(partitions)
        .config(TopicConfig.RETENTION_MS_CONFIG, Long.toString(retention.toMillis()))
        .build();
  }
}
