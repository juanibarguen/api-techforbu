# Usa la imagen base de OpenJDK 21 y agrega Maven manualmente
FROM openjdk:21-jdk-slim AS build

# Instala Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Configura el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y descarga dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el resto del proyecto y compila
COPY . .
RUN mvn clean package -DskipTests

# Usa una imagen base liviana de Java para ejecutar la app
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/api-techforb-0.0.1-SNAPSHOT.jar app.jar

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
