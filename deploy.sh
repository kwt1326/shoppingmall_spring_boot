#!/bin/bash

cd nginx-ssl
sudo ./init-letsencrypt.sh
cd ..
docker build -t kwt1326/spring-shoppingmall .
docker run --rm -d -p 8090:8090 --name deploy kwt1326/spring-shoppingmall
docker system prune -f