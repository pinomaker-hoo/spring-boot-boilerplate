FROM azul/zulu-openjdk:17 as builder

WORKDIR /app

COPY . /app

RUN ./gradlew clean build -x test

FROM azul/zulu-openjdk:17

WORKDIR /app

COPY --from=builder /app/build/libs/mars-application-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod
ENV TZ=Asia/Seoul
ENV JAVA_OPTS="-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -Dfile.encoding=UTF-8"

ENTRYPOINT ["java", "-jar", "app.jar"]