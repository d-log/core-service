#! /bin/bash

ssh pi@192.168.86.28 << EOF
    cd core-service/core-service
    git pull origin master
    ./bin/deploy/kill_core-service.sh
    nohup mvn spring-boot:run &
    tail -f nohup.out
EOF