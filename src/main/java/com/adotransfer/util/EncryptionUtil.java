package com.adotransfer.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String SECRET_KEY = "MySecretKey12345"; // En production, utiliser une clé sécurisée

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Chiffre un mot de passe avec BCrypt
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Vérifie un mot de passe avec BCrypt
     */
    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Chiffre un PIN avec AES
     */
    public String encryptPin(String pin) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(pin.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chiffrement du PIN", e);
        }
    }

    /**
     * Déchiffre un PIN avec AES
     */
    public String decryptPin(String encryptedPin) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedPin));
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du déchiffrement du PIN", e);
        }
    }

    /**
     * Génère un numéro de compte unique
     */
    public String generateAccountNumber() {
        SecureRandom random = new SecureRandom();
        StringBuilder accountNumber = new StringBuilder();
        
        // Format: ADO + 12 chiffres
        accountNumber.append("ADO");
        for (int i = 0; i < 12; i++) {
            accountNumber.append(random.nextInt(10));
        }
        
        return accountNumber.toString();
    }

    /**
     * Génère un ID de transaction unique
     */
    public String generateTransactionId() {
        SecureRandom random = new SecureRandom();
        StringBuilder transactionId = new StringBuilder();
        
        // Format: TXN + timestamp + 6 chiffres aléatoires
        transactionId.append("TXN");
        transactionId.append(System.currentTimeMillis());
        for (int i = 0; i < 6; i++) {
            transactionId.append(random.nextInt(10));
        }
        
        return transactionId.toString();
    }

    /**
     * Génère un code OTP de 6 chiffres
     */
    public String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    /**
     * Chiffre des données sensibles
     */
    public String encryptSensitiveData(String data) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chiffrement des données", e);
        }
    }

    /**
     * Déchiffre des données sensibles
     */
    public String decryptSensitiveData(String encryptedData) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du déchiffrement des données", e);
        }
    }
}
