package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.models.enums.Role;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) return false;
        if (user == null) {
            log.error("User object is null");
            return false;
        }
        if (user.getEmail() == null) {
            log.error("User email is null");
            return false;
        }
        String email = user.getEmail();
        user.setActive(true);
        user.getRoles().add(Role.ROLE_ADMIN);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        log.info("Saving new user with email {}", email);
        userRepository.save(user);
        return true;
    }
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("Ban user with id:" + user.getId() + "and email:" + user.getEmail());
            } else {
                user.setActive(true);
                log.info("Unban user with id:" + user.getId() + "and email:" + user.getEmail());
            }
        }
        userRepository.save(user);
    }

    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }
}
