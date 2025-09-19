# Guide de Déploiement - ADO Transfer

Ce guide vous explique comment déployer l'application ADO Transfer sur Railway avec une base de données PostgreSQL.

## 🚀 Déploiement sur Railway

### 1. Préparation du Repository

1. **Pousser le code sur GitHub**
   ```bash
   git add .
   git commit -m "Initial commit - ADO Transfer app"
   git push origin main
   ```

### 2. Configuration sur Railway

1. **Se connecter à Railway**
   - Aller sur [railway.app](https://railway.app)
   - Se connecter avec GitHub

2. **Créer un nouveau projet**
   - Cliquer sur "New Project"
   - Sélectionner "Deploy from GitHub repo"
   - Choisir le repository ADO-Transfer

3. **Ajouter une base de données PostgreSQL**
   - Dans le dashboard du projet, cliquer sur "+ New"
   - Sélectionner "Database" → "PostgreSQL"
   - Railway créera automatiquement une base de données

4. **Configurer les variables d'environnement**
   - Aller dans "Variables" du projet
   - Ajouter les variables suivantes :

   ```
   DATABASE_URL=postgresql://username:password@host:port/database
   JWT_SECRET=your-super-secret-jwt-key-here-make-it-long-and-random
   MAIL_USERNAME=your-email@gmail.com
   MAIL_PASSWORD=your-app-password
   PORT=8080
   SPRING_PROFILES_ACTIVE=prod
   ```

### 3. Déploiement Automatique

Railway détectera automatiquement :
- Le fichier `Dockerfile`
- Le fichier `pom.xml` (Maven)
- Le fichier `railway.json`

Le déploiement se fera automatiquement à chaque push sur la branche main.

### 4. Vérification du Déploiement

1. **Vérifier les logs**
   - Aller dans l'onglet "Deployments"
   - Cliquer sur le déploiement le plus récent
   - Vérifier que l'application démarre sans erreur

2. **Tester l'API**
   - L'URL de l'application sera affichée dans le dashboard
   - Tester l'endpoint de santé : `https://your-app.railway.app/api/actuator/health`
   - Accéder à Swagger UI : `https://your-app.railway.app/swagger-ui.html`

## 📱 Configuration de l'Application Android

### 1. Mettre à jour l'URL de l'API

Dans le fichier `android/app/src/main/java/com/adotransfer/data/network/ApiClient.java` :

```java
private static final String BASE_URL = "https://your-railway-app.railway.app/api/";
```

Remplacer `your-railway-app` par l'URL réelle de votre application Railway.

### 2. Construire l'APK

```bash
cd android
./gradlew assembleRelease
```

L'APK sera généré dans `android/app/build/outputs/apk/release/`.

## 🔧 Configuration de la Base de Données

### 1. Migrations Automatiques

L'application utilise Flyway pour les migrations. Les scripts SQL sont dans `src/main/resources/db/migration/` :
- `V1__Create_initial_tables.sql` - Création des tables
- `V2__Insert_sample_data.sql` - Données d'exemple

### 2. Vérification des Tables

Se connecter à la base de données PostgreSQL et vérifier :

```sql
-- Vérifier les tables créées
\dt

-- Vérifier les données d'exemple
SELECT * FROM users;
SELECT * FROM accounts;
SELECT * FROM transactions;
```

## 🔒 Sécurité en Production

### 1. Variables d'Environnement Sensibles

- **JWT_SECRET** : Utiliser une clé de 256 bits minimum
- **DATABASE_URL** : Railway la génère automatiquement
- **MAIL_PASSWORD** : Utiliser un mot de passe d'application Gmail

### 2. Configuration HTTPS

Railway fournit automatiquement un certificat SSL. L'application sera accessible via HTTPS.

### 3. Monitoring

- Les logs sont disponibles dans le dashboard Railway
- Utiliser les métriques pour surveiller les performances

## 🧪 Tests de l'API

### 1. Test d'Inscription

```bash
curl -X POST https://your-app.railway.app/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Test",
    "lastName": "User",
    "phoneNumber": "+1234567890",
    "email": "test@example.com",
    "password": "password123"
  }'
```

### 2. Test de Connexion

```bash
curl -X POST https://your-app.railway.app/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "phoneNumber": "+1234567890",
    "password": "password123"
  }'
```

### 3. Test de Transfert

```bash
curl -X POST https://your-app.railway.app/api/transactions/transfer \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "recipientPhoneNumber": "+0987654321",
    "amount": 100.00,
    "description": "Test transfer",
    "pin": "123456"
  }'
```

## 📊 Monitoring et Maintenance

### 1. Logs

- Accéder aux logs via le dashboard Railway
- Surveiller les erreurs et les performances

### 2. Sauvegarde de la Base de Données

Railway effectue des sauvegardes automatiques, mais vous pouvez aussi :

```bash
# Exporter la base de données
pg_dump $DATABASE_URL > backup.sql

# Importer la base de données
psql $DATABASE_URL < backup.sql
```

### 3. Mise à Jour de l'Application

1. Modifier le code localement
2. Pousser les changements sur GitHub
3. Railway redéploiera automatiquement

## 🚨 Dépannage

### Problèmes Courants

1. **Application ne démarre pas**
   - Vérifier les logs dans Railway
   - Vérifier les variables d'environnement
   - Vérifier la configuration de la base de données

2. **Erreur de connexion à la base de données**
   - Vérifier la variable `DATABASE_URL`
   - Vérifier que la base de données PostgreSQL est active

3. **Erreurs JWT**
   - Vérifier la variable `JWT_SECRET`
   - S'assurer qu'elle est suffisamment longue et aléatoire

4. **Erreurs de validation**
   - Vérifier les contraintes de la base de données
   - Vérifier les validations côté application

### Support

- Consulter les logs Railway pour plus de détails
- Vérifier la documentation Spring Boot
- Consulter la documentation Railway

## 📈 Optimisations

### 1. Performance

- Configurer le cache Redis (optionnel)
- Optimiser les requêtes de base de données
- Utiliser la pagination pour les listes

### 2. Sécurité

- Implémenter la limitation de taux (rate limiting)
- Ajouter la validation des entrées
- Configurer les en-têtes de sécurité

### 3. Monitoring

- Intégrer des outils de monitoring (New Relic, DataDog)
- Configurer des alertes
- Surveiller les métriques de performance

---

**Félicitations !** Votre application ADO Transfer est maintenant déployée sur Railway et prête à être utilisée ! 🎉
