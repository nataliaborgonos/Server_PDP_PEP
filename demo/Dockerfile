# Usa una imagen de Java como base
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor en el directorio /app
COPY build/libs/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Copia el archivo serverErat.ks al contenedor en el directorio /app/crypto
COPY crypto/serverErat.ks /app/crypto/serverErat.ks
COPY crypto/ec-cacert.pem /app/crypto/ec-cacert-pem
COPY crypto/ec-cakey.jks /app/crypto/ec-cakey.jks

# Expone el puerto 5000
EXPOSE 5000

# Define los argumentos de construcción
ARG PDP_PORT
ARG PDP_CONFIG
ARG PDP_KS
ARG PDP_PW
ARG PDP_ALIAS
ARG DLT_IP
ARG DLT_PORT
ARG IDAGENT_KS
ARG IDAGENT_PW
ARG IDAGENT_ALIAS
ARG IDAGENT_CERT

# Establece las variables de entorno
ENV PDP_PORT=${PDP_PORT}
ENV PDP_CONFIG=${PDP_CONFIG}
ENV PDP_KS=${PDP_KS}
ENV PDP_PW=${PDP_PW}
ENV PDP_ALIAS=${PDP_ALIAS}
ENV DLT_IP=${DLT_IP}
ENV DLT_PORT=${DLT_PORT}
ENV IDAGENT_KS=${IDAGENT_KS}
ENV IDAGENT_PW=${IDAGENT_PW}
ENV IDAGENT_ALIAS=${IDAGENT_ALIAS}
ENV IDAGENT_CERT=${IDAGENT_CERT}

# Establece el punto de entrada para la aplicación
CMD ["java", "-jar", "demo.jar"]
