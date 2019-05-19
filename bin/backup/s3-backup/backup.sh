#! /bin/bash

# Absolute path to this script, e.g. /home/user/bin/foo.sh
SCRIPT=$(readlink -f "$0")
# Absolute path this script is in, thus /home/user/bin
SCRIPT_PATH=$(dirname "$SCRIPT")

printf "\n\nBacking Up  s3://repo.marcuschiu.com\n"

aws s3 sync s3://repo.marcuschiu.com $SCRIPT_PATH/repo.marcuschiu.com

timedatectl | grep "Local time: " > $SCRIPT_PATH/last-backup.txt

printf "\n"