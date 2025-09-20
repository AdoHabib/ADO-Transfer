# 📱 Guida Mobile - ADO Transfer App

## 🚀 Panoramica
ADO Transfer è un'applicazione di trasferimento di denaro simile a Orange Money, disponibile come applicazione web responsive che funziona perfettamente su smartphone.

## 📲 Come Accedere all'App su Smartphone

### 🌐 Metodo 1: Accesso Web Mobile (Consigliato)

#### **Passo 1: Apri il Browser**
- Apri il browser del tuo smartphone (Chrome, Safari, Firefox, etc.)
- Vai all'indirizzo: `https://ado-transfer.up.railway.app/`

#### **Passo 2: Verifica la Connessione**
- Dovresti vedere la pagina di benvenuto con le informazioni dell'applicazione
- Se vedi le informazioni dell'app, la connessione è riuscita ✅

#### **Passo 3: Accedi alla Documentazione API**
- Per testare l'API, vai a: `https://ado-transfer.up.railway.app/api/swagger-ui.html`
- Qui puoi vedere tutti gli endpoint disponibili e testare l'API

### 📱 Metodo 2: Aggiungere alla Home Screen (PWA)

#### **Su Android (Chrome):**
1. Apri l'app nel browser Chrome
2. Tocca il menu (⋮) in alto a destra
3. Seleziona "Aggiungi alla schermata Home"
4. Tocca "Aggiungi"
5. L'icona apparirà sulla tua home screen

#### **Su iPhone (Safari):**
1. Apri l'app nel browser Safari
2. Tocca il pulsante "Condividi" (⬆️)
3. Scorri verso il basso e tocca "Aggiungi alla schermata Home"
4. Tocca "Aggiungi"
5. L'icona apparirà sulla tua home screen

## 🔐 Come Usare l'App

### **1. Registrazione**
```bash
POST https://ado-transfer.up.railway.app/api/auth/register
```
**Dati richiesti:**
- Nome completo
- Numero di telefono
- Email
- Password

### **2. Login**
```bash
POST https://ado-transfer.up.railway.app/api/auth/login
```
**Dati richiesti:**
- Numero di telefono o email
- Password

### **3. Verifica PIN**
```bash
POST https://ado-transfer.up.railway.app/api/users/verify-pin
```
**Dati richiesti:**
- PIN a 4 cifre

### **4. Trasferimento Denaro**
```bash
POST https://ado-transfer.up.railway.app/api/transactions/transfer
```
**Dati richiesti:**
- Numero di telefono del destinatario
- Importo
- PIN di conferma

### **5. Consulta Saldo**
```bash
GET https://ado-transfer.up.railway.app/api/accounts/balance
```

### **6. Cronologia Transazioni**
```bash
GET https://ado-transfer.up.railway.app/api/transactions/history
```

## 🛠️ Per Sviluppatori

### **Test dell'API su Mobile**

#### **Usando Swagger UI:**
1. Vai a: `https://ado-transfer.up.railway.app/api/swagger-ui.html`
2. Tocca "Authorize" e inserisci il tuo token JWT
3. Testa gli endpoint direttamente dal browser

#### **Usando Postman Mobile:**
1. Scarica Postman dall'App Store/Google Play
2. Crea una nuova richiesta
3. Usa l'URL base: `https://ado-transfer.up.railway.app/api`
4. Aggiungi l'header: `Authorization: Bearer YOUR_JWT_TOKEN`

### **Endpoint Principali**

| Endpoint | Metodo | Descrizione |
|----------|--------|-------------|
| `/api/health/simple` | GET | Controllo salute app |
| `/api/auth/register` | POST | Registrazione utente |
| `/api/auth/login` | POST | Login utente |
| `/api/users/profile` | GET | Profilo utente |
| `/api/accounts/balance` | GET | Saldo conto |
| `/api/transactions/transfer` | POST | Trasferimento denaro |
| `/api/transactions/history` | GET | Cronologia transazioni |

## 🔧 Risoluzione Problemi

### **"Not Found" Error:**
- Verifica che l'URL sia corretto: `https://ado-transfer.up.railway.app/`
- Controlla la connessione internet

### **"Access Denied" Error:**
- Assicurati di essere autenticato
- Verifica che il token JWT sia valido

### **App Non Carica:**
- Prova a svuotare la cache del browser
- Riavvia il browser
- Controlla se l'app è online: `https://ado-transfer.up.railway.app/api/health/simple`

## 📱 Compatibilità

### **Browser Supportati:**
- ✅ Chrome (Android/iOS)
- ✅ Safari (iOS)
- ✅ Firefox (Android/iOS)
- ✅ Edge (Android/iOS)

### **Sistemi Operativi:**
- ✅ Android 7.0+
- ✅ iOS 12.0+
- ✅ Windows Mobile
- ✅ Altri browser moderni

## 🔒 Sicurezza

### **Raccomandazioni:**
1. **Usa sempre HTTPS** - L'app è protetta con SSL
2. **Non condividere il tuo PIN** - Mantieni il PIN privato
3. **Logout dopo l'uso** - Chiudi sempre la sessione
4. **Verifica l'URL** - Assicurati di essere su `ado-transfer.up.railway.app`

### **Autenticazione:**
- L'app usa JWT (JSON Web Tokens) per l'autenticazione
- I token scadono automaticamente per sicurezza
- Ogni richiesta protetta richiede un token valido

## 📞 Supporto

### **In caso di problemi:**
1. Controlla lo stato dell'app: `https://ado-transfer.up.railway.app/api/health/simple`
2. Verifica la documentazione API: `https://ado-transfer.up.railway.app/api/swagger-ui.html`
3. Controlla i log dell'applicazione per errori specifici

### **Endpoint di Monitoraggio:**
- **Health Check:** `GET /api/health/simple`
- **Status App:** `GET /api/health/status`
- **Info App:** `GET /api/health`

## 🎯 Funzionalità Future

### **Prossime Versioni:**
- 📱 App nativa Android/iOS
- 💳 Integrazione con carte di credito
- 🏦 Collegamento con banche
- 📊 Dashboard analytics
- 🔔 Notifiche push
- 💱 Supporto multi-valuta

---

## 📝 Note Tecniche

**Versione:** 1.0.0  
**Ultimo Aggiornamento:** Settembre 2025  
**Ambiente:** Production (Railway)  
**Profilo Attivo:** no-db (Modalità Mock per testing)

**URL Base:** `https://ado-transfer.up.railway.app/api`  
**Documentazione:** `https://ado-transfer.up.railway.app/api/swagger-ui.html`  
**Health Check:** `https://ado-transfer.up.railway.app/api/health/simple`

---

*Questa documentazione è aggiornata per la versione corrente dell'applicazione ADO Transfer.*
