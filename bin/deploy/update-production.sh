#! /bin/bash

ssh ubuntu-server << EOF
    cd core-service/core-service
    git pull origin master
    ./bin/deploy/kill_core-service.sh
# systemd redeploys
#    nohup mvn spring-boot:run &
#    tail -f nohup.out
EOF