FROM openjdk:11
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew
RUN ./gradlew build
ENV JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ \
        "nohup", "java", "-jar", \
        "-Dspring.profiles.active=prod", \
        "-Dspring.config.location=file:prod-application.yml", \
        "./app.jar 2>&1 &" \
        ]