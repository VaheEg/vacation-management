#!/bin/bash
# Creates one database per service, each owned by its own user.
set -euo pipefail

create_database() {
  local database=$1 user=$2 password=$3
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<SQL
CREATE USER ${user} WITH PASSWORD '${password}';
CREATE DATABASE ${database} OWNER ${user};
REVOKE ALL ON DATABASE ${database} FROM PUBLIC;
SQL
}

create_database org_db org_user "$ORG_DB_PASSWORD"
create_database leave_db leave_user "$LEAVE_DB_PASSWORD"
create_database approval_db approval_user "$APPROVAL_DB_PASSWORD"
create_database notify_db notify_user "$NOTIFY_DB_PASSWORD"
create_database keycloak_db keycloak_user "$KEYCLOAK_DB_PASSWORD"
