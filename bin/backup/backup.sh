#! /bin/bash

# Absolute path to this script, e.g. /home/user/bin/foo.sh
SCRIPT=$(readlink -f "$0")
# Absolute path this script is in, thus /home/user/bin
SCRIPT_PATH=$(dirname "$SCRIPT")


$SCRIPT_PATH/mongo-db-backup/backup.sh
$SCRIPT_PATH/s3-backup/backup.sh