-- Tables every service database has: transactional outbox, scheduler locks
-- and (for consuming services) the idempotent-consumer log.

CREATE TABLE outbox_event (
    id           uuid         PRIMARY KEY,
    topic        varchar(200) NOT NULL,
    message_key  varchar(100) NOT NULL,
    event_type   varchar(100) NOT NULL,
    payload      jsonb        NOT NULL,
    created_at   timestamptz  NOT NULL,
    published_at timestamptz
);

CREATE INDEX outbox_event_unpublished_idx ON outbox_event (created_at) WHERE published_at IS NULL;

CREATE TABLE shedlock (
    name       varchar(64)  PRIMARY KEY,
    lock_until timestamp(3) NOT NULL,
    locked_at  timestamp(3) NOT NULL,
    locked_by  varchar(255) NOT NULL
);
