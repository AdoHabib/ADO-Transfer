package com.adotransfer.model;

public enum TransactionStatus {
    PENDING,            // En attente
    PROCESSING,         // En cours de traitement
    COMPLETED,          // Terminé avec succès
    FAILED,             // Échec
    CANCELLED,          // Annulé
    REVERSED            // Inversé
}
