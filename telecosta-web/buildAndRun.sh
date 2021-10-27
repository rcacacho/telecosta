#!/bin/sh
mvn clean package && docker build -t com.telecosta/telecosta-web .
docker rm -f telecosta-web || true && docker run -d -p 9080:9080 -p 9443:9443 --name telecosta-web com.telecosta/telecosta-web