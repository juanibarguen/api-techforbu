# Usar una imagen base de Maven para compilar
FROM maven:3.9.4-openjdk-17-slim AS build

# Configurar el directorio de trabajo
WORKDIR /app

# Copiar el pom.xml primero para aprovechar el caché de Docker
COPY pom.xml .

# Copiar el directorio src
COPY src ./src

# Compilar el proyecto y crear el archivo JAR
RUN mvn clean package -DskipTests

# Usar una imagen más ligera para la ejecución
FROM openjdk:17-jdk-slim

# Copiar el archivo JAR construido desde la etapa de compilación
COPY --from=build /app/target/api-techforb-0.0.1-SNAPSHOT.jar /app/app.jar

# Especificar el comando de inicio
CMD ["java", "-jar", "/app/app.jar"]
