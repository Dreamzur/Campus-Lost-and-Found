package com.example.demo.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepo;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public User register(String username, String rawPassword) {
    if (userRepo.existsByUsername(username)) {
      throw new IllegalArgumentException("Username already taken: " + username);
    }
    String encoded = passwordEncoder.encode(rawPassword);
    User u = new User();
    u.setUsername(username);
    u.setPasswordHash(encoded);
    return userRepo.save(u);
  }

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {

    User u = userRepo.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("No user: " + username));

    return new org.springframework.security.core.userdetails.User(
        u.getUsername(),
        u.getPasswordHash(),
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
  }

  public boolean usernameExists(String username) {
    return userRepo.existsByUsername(username);
  }
}