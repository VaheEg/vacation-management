#!/bin/bash
# Starts the local infrastructure and waits until every container is healthy.
set -euo pipefail

compose_dir="$(cd "$(dirname "$0")/../deploy/compose" && pwd)"

if [ ! -f "$compose_dir/.env" ]; then
  cp "$compose_dir/.env.example" "$compose_dir/.env"
  echo "Created $compose_dir/.env from .env.example"
fi

compose() {
  docker compose --project-directory "$compose_dir" "$@"
}

compose up --detach --wait postgres kafka keycloak minio mailpit observability

# One-shot setup, safe to repeat: the Keycloak provisioning client and the MinIO buckets.
compose run --rm keycloak-init
compose run --rm minio-init

echo "Local infrastructure is up."
