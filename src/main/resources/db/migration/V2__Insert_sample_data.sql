-- Insertion de données d'exemple pour les tests

-- Utilisateurs d'exemple
INSERT INTO users (first_name, last_name, phone_number, email, password, status, is_verified, kyc_verified) 
VALUES 
    ('John', 'Doe', '+1234567890', 'john.doe@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ACTIVE', true, true),
    ('Jane', 'Smith', '+0987654321', 'jane.smith@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ACTIVE', true, true),
    ('Alice', 'Johnson', '+1122334455', 'alice.johnson@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ACTIVE', true, false);

-- Comptes d'exemple
INSERT INTO accounts (user_id, balance, account_number, status) 
VALUES 
    (1, 1000.00, 'ADO123456789012', 'ACTIVE'),
    (2, 500.00, 'ADO987654321098', 'ACTIVE'),
    (3, 250.00, 'ADO112233445566', 'ACTIVE');

-- Transactions d'exemple
INSERT INTO transactions (transaction_id, sender_id, receiver_id, amount, fee, description, type, status, created_at, completed_at) 
VALUES 
    ('TXN170312345678901234', 1, 2, 100.00, 0.50, 'Paiement de facture', 'TRANSFER', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '2 days', CURRENT_TIMESTAMP - INTERVAL '2 days'),
    ('TXN170312345678901235', 2, 3, 50.00, 0.25, 'Remboursement', 'TRANSFER', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '1 day'),
    ('TXN170312345678901236', 3, 1, 25.00, 0.13, 'Partage de frais', 'TRANSFER', 'PENDING', CURRENT_TIMESTAMP - INTERVAL '1 hour', NULL);
