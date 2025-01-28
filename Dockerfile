FROM clojure:tools-deps-1.11.1.1208 AS build

WORKDIR /app

ADD . /app

RUN clj -T:build uber

WORKDIR /app

FROM openjdk:17-jdk-slim

COPY resources ./resources
COPY --from=build /app/target/app.jar app.jar

EXPOSE 3000

CMD ["java", "-jar", "app.jar"]
