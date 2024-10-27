# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR generado a la imagen
COPY target/api-techforb-0.0.1-SNAPSHOT.jar app.jar

# Define el comando de entrada para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
