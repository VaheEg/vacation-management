package am.vacation.common.events;

/** Names of the Kafka topics, one per publishing service. */
public final class Topics {

  public static final String ORGANIZATION = "vms.organization.events.v1";
  public static final String LEAVE = "vms.leave.events.v1";
  public static final String APPROVAL = "vms.approval.events.v1";

  private Topics() {}
}
