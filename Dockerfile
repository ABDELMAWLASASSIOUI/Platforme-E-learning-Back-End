# Utiliser une image de base avec OpenJDK 17
FROM openjdk:17-jdk-slim AS build

# Installer Maven
RUN apt-get update && apt-get install -y maven

# Répertoire de travail
WORKDIR /app

# Copier le fichier pom.xml et les sources du projet
COPY pom.xml .
COPY src ./src

# Télécharger les dépendances Maven
RUN mvn dependency:go-offline

# Compiler et packager l'application Java sans exécuter les tests
RUN mvn clean package -DskipTests

# Exposer le port utilisé par l'application (par défaut 8080)
EXPOSE 8080

# Lancer l'application (modifier selon votre fichier JAR)
CMD ["java", "-jar", "target/pharmacy_online_platforme.jar"]
