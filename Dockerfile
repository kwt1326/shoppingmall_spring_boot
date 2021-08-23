FROM openjdk:11
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew
RUN ./gradlew build
COPY . build/libs/*.jar
RUN export JAR_NAME=$(ls -tr /app/ | grep jar | tail -n 1)
RUN echo "jar name $JAR_NAME"
RUN nohup java -jar \
        -Dspring.profiles.active=prod \
        -Dspring.config.location=file:/home/ubuntu/app/config/application.yml \
        /app/$JAR_NAME 2>&1 &