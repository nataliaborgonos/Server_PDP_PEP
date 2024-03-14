# Usa una imagen de Java como base
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor en el directorio /app
COPY build/libs/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Copia el archivo serverErat.ks al contenedor en el directorio /app/crypto
COPY crypto/serverErat.ks /app/crypto/serverErat.ks

# Expone el puerto 8080
EXPOSE 8080

ENV SERVER_PORT=8080
ENV PDP_CONFIG=test

# Ejecuta la aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "demo.jar"]

