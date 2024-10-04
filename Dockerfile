# Utiliza la imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Configurar el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY target/Ecomerce-backend-0.0.1-SNAPSHOT.jar /app/Ecomerce-backend.jar

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/Ecomerce-backend.jar"]
