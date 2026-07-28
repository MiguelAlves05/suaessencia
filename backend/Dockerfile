# ── Etapa 1: Build com Maven ──────────────────
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copia o pom e baixa dependências primeiro (cache de layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B 2>/dev/null || true

# Instala Maven
RUN apk add --no-cache maven

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# ── Etapa 2: Imagem de execução leve ──────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia apenas o JAR gerado
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
