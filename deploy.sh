#!/bin/bash

REPOSITORY=/home/opc/app/git
PROJECT_NAME=shoppingmall_spring_boot

cd $REPOSITORY/$PROJECT_NAME/

chmod +x ./gradlew

echo "*** git pulling project... ***"

git pull

echo "*** project building ***"

./gradlew build

cd $REPOSITORY/$PROJECT_NAME

echo "*** build jar copy to parent Repo ***"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/$PROJECT_NAME

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "*** current app pid : $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo "*** current not running application, try run app ***"
else
        echo "*** kill -15 $CURRENT_PID ***"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo "*** deploy new application ***"

JAR_NAME=$(ls -tr $REPOSITORY/$PROJECT_NAME | grep jar | tail -n 1)

echo "*** jar name : $JAR_NAME ***"

nohup java -jar \
        -Dspring.profiles.active=prod \
        -Dspring.config.location=file:prod-application.yml \
        $JAR_NAME 2>&1 &

# Docker build test

# sudo systemctl start docker
# cd nginx-ssl
# sudo ./init-letsencrypt.sh
# cd ..
# sudo docker build -t kwt1326/spring-shoppingmall .
# sudo docker run --rm -d -p 8090:8090 --name deploy kwt1326/spring-shoppingmall
# sudo docker system prune -f
