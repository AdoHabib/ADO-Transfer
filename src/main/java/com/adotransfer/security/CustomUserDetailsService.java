package com.adotransfer.security;

import com.adotransfer.model.User;
import com.adotransfer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

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
        
        // Ajouter des rôles basés sur le statut de l'utilisateur
        if (user.getKycVerified()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_VERIFIED_USER"));
        }
        
        if (user.getIsVerified()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        
        // Rôle par défaut
        authorities.add(new SimpleGrantedAuthority("ROLE_BASIC"));
        
        return authorities;
    }
}
