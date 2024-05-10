FROM openjdk:21-jdk-slim
LABEL authors="choi"

WORKDIR /

COPY ./target/webpos-products-0.0.1.jar /app.jar

CMD ["java", "-jar", "app.jar"]
