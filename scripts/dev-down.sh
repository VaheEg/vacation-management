#!/bin/bash
# Stops the local infrastructure. Pass --volumes to also delete its data.
set -euo pipefail

compose_dir="$(cd "$(dirname "$0")/../deploy/compose" && pwd)"

docker compose --project-directory "$compose_dir" down "$@"
