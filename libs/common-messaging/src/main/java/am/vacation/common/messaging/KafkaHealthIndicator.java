package am.vacation.common.messaging;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.health.contributor.AbstractHealthIndicator;
import org.springframework.boot.health.contributor.Health;

/** Reports Kafka as up when the cluster answers a metadata request within the timeout. */
public class KafkaHealthIndicator extends AbstractHealthIndicator implements DisposableBean {

  private static final int TIMEOUT_MS = 3000;

  private final Admin admin;

  public KafkaHealthIndicator(Map<String, Object> adminProperties) {
    super("Kafka health check failed");
    this.admin = Admin.create(adminProperties);
  }

  @Override
  protected void doHealthCheck(Health.Builder builder) throws Exception {
    var cluster = admin.describeCluster(new DescribeClusterOptions().timeoutMs(TIMEOUT_MS));
    int nodes = cluster.nodes().get(TIMEOUT_MS, TimeUnit.MILLISECONDS).size();
    builder.up().withDetail("nodes", nodes);
  }

  @Override
  public void destroy() {
    admin.close();
  }
}
