package am.vacation.organization;

import static org.assertj.core.api.Assertions.assertThat;

import am.vacation.common.test.KafkaContainerSupport;
import am.vacation.common.test.PostgresContainerSupport;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class BaselineMigrationIT {

  @Autowired private JdbcTemplate jdbcTemplate;

  @DynamicPropertySource
  static void infrastructure(DynamicPropertyRegistry registry) {
    PostgresContainerSupport.registerProperties(registry);
    KafkaContainerSupport.registerProperties(registry);
  }

  @Test
  void flywayAppliesTheBaselineMigration() {
    List<String> appliedVersions =
        jdbcTemplate.queryForList(
            "SELECT version FROM flyway_schema_history WHERE success ORDER BY installed_rank",
            String.class);
    List<String> tables =
        jdbcTemplate.queryForList(
            "SELECT table_name FROM information_schema.tables"
                + " WHERE table_schema = 'public' AND table_name <> 'flyway_schema_history'",
            String.class);

    assertThat(appliedVersions).containsExactly("1");
    assertThat(tables).containsExactlyInAnyOrder("outbox_event", "shedlock");
  }
}
