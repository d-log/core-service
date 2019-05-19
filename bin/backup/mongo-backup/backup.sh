#!/bin/bash

# Absolute path to this script, e.g. /home/user/bin/foo.sh
SCRIPT=$(readlink -f "$0")
# Absolute path this script is in, thus /home/user/bin
SCRIPT_PATH=$(dirname "$SCRIPT")

# REMOTE MONGO SERVER
#ssh -i ~/.ssh/ubuntu-server-root pi@192.168.86.219 mongodump -d logger-project -o /home/pi/Documents/
#scp -i ~/.ssh/ubuntu-server-root -r pi@192.168.86.219:/home/pi/Documents/logger-project $SCRIPT_PATH

# LOCAL MOPNGO SERVER
mongodump -d logger-project -o $SCRIPT_PATH

timedatectl | grep "Local time: " > $SCRIPT_PATH/last-backup.txt

# to restore from dump/
# mongorestore --db databasename --verbose \path\to\dump\file
# the command above requires mongod instance