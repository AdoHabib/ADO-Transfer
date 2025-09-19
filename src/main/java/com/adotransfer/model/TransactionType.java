package com.adotransfer.model;

public enum TransactionType {
    TRANSFER,           // Transfert entre utilisateurs
    DEPOSIT,            // Dépôt/recharge
    WITHDRAWAL,         // Retrait
    PAYMENT,            // Paiement de facture
    REFUND,             // Remboursement
    FEE                 // Frais
}
