FROM gradle:jdk11 as builder
WORKDIR /app
COPY src ./src/
COPY gradle.properties .
COPY build.gradle .
COPY settings.gradle .
RUN gradle build

FROM openjdk:11.0.11
LABEL mantainer="Put your name and e-mail address here"
ENV USER_MEM_ARGS=""
COPY --from=builder /app/build/book-*.jar /app/book-app.jar
ENTRYPOINT ["/bin/bash","-c","java $USER_MEM_ARGS -jar /app/book-app.jar"]