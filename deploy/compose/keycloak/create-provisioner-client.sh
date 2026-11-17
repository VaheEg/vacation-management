#!/bin/bash
# Creates the confidential client organization-service uses to create and manage tenant realms.
# It lives in the master realm because only master-realm principals may create realms.
set -euo pipefail

kcadm=/opt/keycloak/bin/kcadm.sh

$kcadm config credentials --server http://keycloak:8080 --realm master \
  --user "$KEYCLOAK_ADMIN" --password "$KEYCLOAK_ADMIN_PASSWORD"

if $kcadm get clients -r master -q clientId=vms-provisioner --fields clientId | grep -q vms-provisioner; then
  echo "Client vms-provisioner already exists"
else
  $kcadm create clients -r master \
    -s clientId=vms-provisioner \
    -s enabled=true \
    -s publicClient=false \
    -s serviceAccountsEnabled=true \
    -s standardFlowEnabled=false \
    -s directAccessGrantsEnabled=false \
    -s "secret=$KEYCLOAK_PROVISIONER_SECRET"
fi

$kcadm add-roles -r master --uusername service-account-vms-provisioner --rolename admin
