#### Binaries to Install
- java 
- maven
- awscli


#### Setup and Deployment
- aws configure
- create /tmp directory based on the application.properties
- mvn clean install
- mvn spring-boot:run


- nohup mvn spring-boot:run &


#### Auto Deploy Scripts
- ./bin/deploy

#### Auto Backup Scripts
- ./bin/backup