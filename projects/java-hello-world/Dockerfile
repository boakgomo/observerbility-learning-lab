
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY HelloWorld.java .

RUN javac HelloWorld.java

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/HelloWorld.class .

EXPOSE 8080

CMD ["java", "HelloWorld"]

