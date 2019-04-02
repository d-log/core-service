#! /bin/bash

kill $(ps aux | grep java | grep core-service | awk  '{print $2}')