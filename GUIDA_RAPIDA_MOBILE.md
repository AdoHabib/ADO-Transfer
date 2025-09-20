# 🚀 Guida Rapida - ADO Transfer Mobile

## 📱 Accesso Immediato

### **1. Apri il Browser del tuo Smartphone**
- Chrome, Safari, Firefox, o qualsiasi browser moderno

### **2. Vai a questo indirizzo:**
```
https://ado-transfer.up.railway.app/
```

### **3. Verifica che funzioni:**
- Dovresti vedere le informazioni dell'applicazione
- Se vedi "Not Found", prova: `https://ado-transfer.up.railway.app/api/health/simple`

## 🔧 Test Rapido dell'API

### **1. Documentazione Interattiva:**
```
https://ado-transfer.up.railway.app/api/swagger-ui.html
```

### **2. Endpoint di Test:**
- **Health Check:** `GET /api/health/simple` → Dovrebbe restituire "OK"
- **Status App:** `GET /api/health/status` → Informazioni dettagliate

## 📲 Aggiungi alla Home Screen

### **Android:**
1. Menu (⋮) → "Aggiungi alla schermata Home"
2. Tocca "Aggiungi"

### **iPhone:**
1. Pulsante Condividi (⬆️) → "Aggiungi alla schermata Home"
2. Tocca "Aggiungi"

## 🔐 Test di Autenticazione

### **1. Registrazione (Mock):**
```bash
POST /api/auth/register
{
  "fullName": "Test User",
  "phoneNumber": "+33123456789",
  "email": "test@example.com",
  "password": "password123"
}
```

### **2. Login:**
```bash
POST /api/auth/login
{
  "phoneNumber": "+33123456789",
  "password": "password123"
}
```

### **3. Impostare PIN:**
```bash
POST /api/users/set-pin
{
  "pin": "1234"
}
```

## 💰 Test Trasferimento (Mock)

### **Trasferimento Denaro:**
```bash
POST /api/transactions/transfer
{
  "recipientPhoneNumber": "+33987654321",
  "amount": 50.00,
  "pin": "1234"
}
```

## 📊 Monitoraggio App

### **Controlli Rapidi:**
- **App Online:** `https://ado-transfer.up.railway.app/api/health/simple`
- **Profilo Attivo:** `https://ado-transfer.up.railway.app/api/health/status`
- **Info App:** `https://ado-transfer.up.railway.app/`

## ⚠️ Risoluzione Problemi Veloce

| Problema | Soluzione |
|----------|-----------|
| "Not Found" | Usa `/api/health/simple` |
| "Access Denied" | Verifica autenticazione |
| App non carica | Svuota cache browser |
| "500 Error" | Controlla log applicazione |

## 🎯 URL Essenziali

- **App:** `https://ado-transfer.up.railway.app/`
- **API Docs:** `https://ado-transfer.up.railway.app/api/swagger-ui.html`
- **Health:** `https://ado-transfer.up.railway.app/api/health/simple`
- **Status:** `https://ado-transfer.up.railway.app/api/health/status`

---

**💡 Suggerimento:** Usa Swagger UI per testare tutti gli endpoint in modo interattivo direttamente dal tuo smartphone!
