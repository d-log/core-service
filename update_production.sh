#! /bin/bash

ssh pi@192.168.86.218 << EOF
  bash
  cd Documents/core-service
  git pull origin master
  kill $(ps aux | grep java | grep maven | awk  '{print $2}')
  nohup mvn spring-boot:run &
  tail -f nohup.out
EOF