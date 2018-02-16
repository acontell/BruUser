#!/bin/sh
mvn clean package && docker build -t com.apps/bruuser .
docker rm -f bruuser || true && docker run -d -p 8080:8080 -p 4848:4848 --name bruuser com.apps/bruuser 
