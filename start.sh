#!/bin/bash

# Start app
java -jar /app/app.jar &

# Đợi 10s cho app boot xong
sleep 10

# Start nginx
nginx -g 'daemon off;'
