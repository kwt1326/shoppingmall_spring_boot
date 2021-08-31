FROM openjdk:11
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew
RUN ./gradlew build
COPY build/libs/*.jar app.jar
RUN sudo ./nginx-ssl/init-letsencrypt.sh
ENTRYPOINT [ \
        "java", "-jar", \
        "-Dspring.profiles.active=prod", \
        "-Dspring.config.location=file:prod-application.yml", \
        "app.jar" \
        ]