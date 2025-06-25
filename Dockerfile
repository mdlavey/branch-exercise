FROM maven:3.9.10-sapmachine-21 AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests
RUN echo `ls`
COPY ./target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]