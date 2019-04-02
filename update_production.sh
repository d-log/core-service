#! /bin/bash

ssh pi@192.168.86.218 << EOF
    cd Documents/core-service
    git pull origin master
    ./kill_core-service.sh
    nohup mvn spring-boot:run &
    tail -f nohup.out
EOF