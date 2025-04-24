#!/bin/bash

# Start Spring Boot (nền)
java -jar /app/app.jar &

# Start nginx (foreground)
nginx -g 'daemon off;'
