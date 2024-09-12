FROM maven:3.8-eclipse-temurin-21 as builder
WORKDIR /app
COPY . /app/.
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/app/*.jar"]