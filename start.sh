#!/bin/bash

# Start Spring Boot in background
java -jar target/*.jar &

# Start nginx in foreground
nginx -g 'daemon off;'
