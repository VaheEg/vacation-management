package am.vacation.common.test;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.kafka.KafkaContainer;

/** One Kafka container (KRaft mode) per test JVM, shared by every test class. */
public final class KafkaContainerSupport {

  private static final KafkaContainer KAFKA = new KafkaContainer("apache/kafka:4.4.0");

  static {
    KAFKA.start();
  }

  private KafkaContainerSupport() {}

  /** Call from a {@code @DynamicPropertySource} method. */
  public static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
  }
}
