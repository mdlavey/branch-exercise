FROM alpine/java:21-jdk
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY ./target/githubdata-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]