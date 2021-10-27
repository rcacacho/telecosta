@echo off
call mvn clean package
call docker build -t com.telecosta/telecosta-web .
call docker rm -f telecosta-web
call docker run -d -p 9080:9080 -p 9443:9443 --name telecosta-web com.telecosta/telecosta-web