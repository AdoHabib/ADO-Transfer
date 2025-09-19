package com.adotransfer.service;

import com.adotransfer.dto.RegisterRequest;
import com.adotransfer.dto.UserResponse;
import com.adotransfer.model.Account;
import com.adotransfer.model.User;
import com.adotransfer.repository.AccountRepository;
import com.adotransfer.repository.UserRepository;
import com.adotransfer.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));
        
        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(),
                user.getPassword(),
                user.getStatus().name().equals("ACTIVE"),
                true,
                true,
                !user.getStatus().name().equals("BLOCKED"),
                getAuthorities(user)
        );
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        if (user.getKycVerified()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_VERIFIED_USER"));
        }
        
        if (user.getIsVerified()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        
        authorities.add(new SimpleGrantedAuthority("ROLE_BASIC"));
        
        return authorities;
    }

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
