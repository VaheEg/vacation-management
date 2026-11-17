package am.vacation.common.test;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.postgresql.PostgreSQLContainer;

/** One PostgreSQL container per test JVM, shared by every test class. */
public final class PostgresContainerSupport {

  private static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer("postgres:17.12");

  static {
    POSTGRES.start();
  }

  private PostgresContainerSupport() {}

  /** Call from a {@code @DynamicPropertySource} method. */
  public static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRES::getUsername);
    registry.add("spring.datasource.password", POSTGRES::getPassword);
  }
}
