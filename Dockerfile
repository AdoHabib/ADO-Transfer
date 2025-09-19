# Utiliser l'image OpenJDK 17
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier pom.xml
COPY pom.xml .

# Installer Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Télécharger les dépendances (pour le cache Docker)
RUN mvn dependency:go-offline -B

# Copier le code source
COPY src ./src

# Construire l'application
RUN mvn clean package -DskipTests

# Exposer le port
EXPOSE 8080

# Commande pour démarrer l'application
CMD ["java", "-jar", "target/ado-transfer-app-0.0.1-SNAPSHOT.jar"]
