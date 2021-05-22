FROM gradle:jdk11 as builder
WORKDIR /app
COPY src ./src
COPY build.gradle .
COPY gradle.properties .
RUN gradle build

FROM openjdk:11.0.11
LABEL maintainer="{Put name here}"
COPY --from=builder /app/build/quarkus-app/ /book/
ENTRYPOINT ["java","-jar","/book/quarkus-run.jar"]

