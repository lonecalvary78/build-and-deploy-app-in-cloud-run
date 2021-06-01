FROM gradle:jdk11 as builder
WORKDIR /app
COPY src ./src
COPY build.gradle .
COPY gradle.properties .
RUN gradle build

FROM openjdk:11.0.11
LABEL maintainer="{Put name here}"
ENV USER_MEM_ARGS=""
COPY --from=builder /app/build/quarkus-app/ /book/
ENTRYPOINT ["/bin/bash","-c","java $USER_MEM_ARGS -jar /book/quarkus-run.jar"]

