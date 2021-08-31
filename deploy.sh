#!/bin/bash

sudo systemctl start docker
cd nginx-ssl
sudo ./init-letsencrypt.sh
cd ..
sudo docker build -t kwt1326/spring-shoppingmall .
sudo docker run --rm -d -p 8090:8090 --name deploy kwt1326/spring-shoppingmall
sudo docker system prune -f