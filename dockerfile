# Etapa 1: Compilar con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar el pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código y compilar
COPY src ./src
RUN mvn package -DskipTests && ls -l target

# Etapa 2: Imagen ligera para ejecutar
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# ⚠️ Ajusta el nombre del JAR según lo que Maven genere en target/
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]