FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests

RUN cp target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
