FROM openjdk:11
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew
RUN ./gradlew build
ENV JAR_NAME=app.jar
COPY app/build/libs/*.jar $JAR_NAME
RUN echo "jar name $JAR_NAME"
RUN nohup java -jar \
        -Dspring.profiles.active=prod \
        -Dspring.config.location=file:/app/application.yml \
        /app/$JAR_NAME 2>&1 &