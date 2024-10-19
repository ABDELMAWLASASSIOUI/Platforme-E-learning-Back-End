# Utiliser une image Java officielle (OpenJDK)
FROM openjdk:11-jdk-slim

# Installer Maven
RUN apt-get update && apt-get install -y maven

# Définir le répertoire de travail dans le container
WORKDIR /app

# Copier le fichier pom.xml et les fichiers source dans le répertoire de travail
COPY pom.xml .
COPY src ./src

# Compiler et packager l'application Java
RUN mvn clean package

# Exposer le port 8080 (ou tout autre port utilisé par votre application)
EXPOSE 8080

# Exécuter l'application JAR
CMD ["java", "-jar", "target/your-app.jar"]
