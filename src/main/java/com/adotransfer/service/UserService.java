package com.adotransfer.service;

import com.adotransfer.dto.RegisterRequest;
import com.adotransfer.dto.UserResponse;
import com.adotransfer.model.Account;
import com.adotransfer.model.User;
import com.adotransfer.repository.AccountRepository;
import com.adotransfer.repository.UserRepository;
import com.adotransfer.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@Profile("!no-db")
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EncryptionUtil encryptionUtil;


    public UserResponse registerUser(RegisterRequest request) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new RuntimeException("Un utilisateur avec ce numéro de téléphone existe déjà");
        }

        if (request.getEmail() != null && userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }

        // Créer l'utilisateur
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Sauvegarder l'utilisateur
        user = userRepository.save(user);

        // Créer le compte associé
        String accountNumber = generateUniqueAccountNumber();
        Account account = new Account(user, accountNumber);
        accountRepository.save(account);

        return new UserResponse(user);
    }

    public UserResponse getUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return new UserResponse(user);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return new UserResponse(user);
    }

    public UserResponse updateUser(Long id, RegisterRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user = userRepository.save(user);
        return new UserResponse(user);
    }

    public void setUserPin(Long userId, String pin) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String encryptedPin = encryptionUtil.encryptPin(pin);
        user.setPin(encryptedPin);
        userRepository.save(user);
    }

    public boolean verifyPin(Long userId, String pin) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (user.getPin() == null) {
            return false;
        }

        String decryptedPin = encryptionUtil.decryptPin(user.getPin());
        return decryptedPin.equals(pin);
    }

    public void verifyUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setIsVerified(true);
        userRepository.save(user);
    }

    public void verifyKyc(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setKycVerified(true);
        userRepository.save(user);
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = encryptionUtil.generateAccountNumber();
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}
