FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY . .

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "DLUT-EDA_MCP-1.1.0.jar"]
