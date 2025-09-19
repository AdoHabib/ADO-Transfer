 ADO Transfer - Application de Transfert d'Argent

Une application de transfert d'argent moderne similaire à Orange Money, développée avec Spring Boot et PostgreSQL, déployée sur Railway.

## 🚀 Fonctionnalités

### Fonctionnalités Principales
- **Inscription et Connexion** : Authentification sécurisée avec JWT
- **Gestion des Comptes** : Création automatique de comptes avec numéros uniques
- **Transferts d'Argent** : Transferts sécurisés entre utilisateurs
- **Historique des Transactions** : Suivi complet des opérations
- **Sécurité Avancée** : Chiffrement des données sensibles, validation des PIN
- **API REST Complète** : Documentation Swagger intégrée

### Sécurité
- Authentification JWT
- Chiffrement des mots de passe avec BCrypt
- Chiffrement des PIN avec AES
- Validation des données d'entrée
- Protection CORS configurée

## 🛠️ Stack Technologique

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL**
- **JWT (JSON Web Tokens)**
- **Swagger/OpenAPI 3**

### Déploiement
- **Railway** pour l'hébergement
- **PostgreSQL** sur Railway
- **Docker** pour la containerisation

## 📋 Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- PostgreSQL 12 ou supérieur
- Docker (optionnel)

## 🚀 Installation et Démarrage

### Développement Local

1. **Cloner le repository**
   ```bash
   git clone <repository-url>
   cd ADO-Transfer
   ```

2. **Configurer la base de données**
   - Créer une base de données PostgreSQL
   - Mettre à jour les paramètres dans `src/main/resources/application.yml`

3. **Construire et démarrer l'application**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Accéder à l'API**
   - API : http://localhost:8080/api
   - Swagger UI : http://localhost:8080/swagger-ui.html

### Déploiement sur Railway

1. **Connecter le repository à Railway**
   - Se connecter à [Railway](https://railway.app)
   - Créer un nouveau projet
   - Connecter le repository GitHub

2. **Configurer les variables d'environnement**
   ```
   DATABASE_URL=postgresql://username:password@host:port/database
   JWT_SECRET=your-secret-key-here
   MAIL_USERNAME=your-email@gmail.com
   MAIL_PASSWORD=your-app-password
   ```

3. **Déployer**
   - Railway détectera automatiquement le Dockerfile
   - L'application sera déployée automatiquement

## 📚 API Documentation

### Endpoints Principaux

#### Authentification
- `POST /api/auth/register` - Inscription
- `POST /api/auth/login` - Connexion
- `POST /api/auth/verify-phone` - Vérification téléphone
- `POST /api/auth/verify-otp` - Vérification OTP

#### Utilisateurs
- `GET /api/users/profile` - Profil utilisateur
- `PUT /api/users/profile` - Mise à jour profil
- `POST /api/users/set-pin` - Définir PIN
- `POST /api/users/verify-pin` - Vérifier PIN

#### Comptes
- `GET /api/accounts/balance` - Solde du compte
- `GET /api/accounts/details` - Détails du compte
- `POST /api/accounts/deposit` - Dépôt
- `POST /api/accounts/withdraw` - Retrait

#### Transactions
- `POST /api/transactions/transfer` - Transfert d'argent
- `GET /api/transactions` - Historique des transactions
- `GET /api/transactions/sent` - Transactions envoyées
- `GET /api/transactions/received` - Transactions reçues
- `GET /api/transactions/{id}` - Détails d'une transaction
- `POST /api/transactions/{id}/cancel` - Annuler une transaction

### Exemple d'utilisation

#### Inscription
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+1234567890",
    "email": "john.doe@example.com",
    "password": "password123"
  }'
```

#### Connexion
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "phoneNumber": "+1234567890",
    "password": "password123"
  }'
```

#### Transfert d'argent
```bash
curl -X POST http://localhost:8080/api/transactions/transfer \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "recipientPhoneNumber": "+0987654321",
    "amount": 100.00,
    "description": "Paiement de facture",
    "pin": "123456"
  }'
```

## 🔧 Configuration

### Variables d'Environnement

| Variable | Description | Valeur par défaut |
|----------|-------------|-------------------|
| `DATABASE_URL` | URL de la base de données PostgreSQL | `jdbc:postgresql://localhost:5432/money_transfer` |
| `JWT_SECRET` | Clé secrète pour JWT | `mySecretKey123456789012345678901234567890` |
| `MAIL_USERNAME` | Email pour l'envoi de notifications | - |
| `MAIL_PASSWORD` | Mot de passe de l'email | - |
| `PORT` | Port de l'application | `8080` |

### Configuration de la Base de Données

L'application utilise Flyway pour les migrations de base de données. Les scripts SQL sont dans `src/main/resources/db/migration/`.

## 🧪 Tests

```bash
# Exécuter tous les tests
mvn test

# Exécuter les tests avec couverture
mvn test jacoco:report
```

## 📱 Application Mobile

L'application mobile Android sera développée séparément et communiquera avec cette API REST.

## 🔒 Sécurité

- Tous les endpoints (sauf authentification) nécessitent un token JWT
- Les mots de passe sont chiffrés avec BCrypt
- Les PIN sont chiffrés avec AES
- Validation des données d'entrée
- Protection contre les attaques CSRF
- Configuration CORS sécurisée

## 📈 Monitoring

- Logs structurés avec Spring Boot
- Métriques de santé avec Actuator
- Monitoring des performances

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 📞 Support

Pour toute question ou problème, veuillez ouvrir une issue sur GitHub ou contacter l'équipe de développement.

---

**ADO Transfer** - Révolutionner les transferts d'argent en Afrique et dans le monde 🌍
