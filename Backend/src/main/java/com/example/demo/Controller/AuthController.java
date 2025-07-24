package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import com.example.demo.web.dto.LoginRequest;
import com.example.demo.web.dto.LoginResponse;
import com.example.demo.web.dto.RegisterDto;

@RestController
@RequestMapping("/api")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  private final UserService userService;

  public AuthController(UserService userService,
      JwtTokenProvider jwtTokenProvider,
      AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest body) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword()));
    } catch (BadCredentialsException ex) {
      return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body("Invalid username or password");
    }

    UserDetails user = userDetailsService.loadUserByUsername(body.getUsername());
    String token = jwtTokenProvider.generateToken(user);

    return ResponseEntity.ok(new LoginResponse(token));
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterDto dto) {
    if (userService.usernameExists(dto.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body("Username is already taken");
    }

    // new usee
    User created = userService.register(dto.getUsername(), dto.getPassword());

    System.out.println("Registered user: " + created.getUsername() + ", role: " + created.getRole());

    // auth registered user
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
    } catch (BadCredentialsException ex) {
      return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body("Authentication failed after registration");
    }

    // load & gen token
    UserDetails user = userDetailsService.loadUserByUsername(dto.getUsername());
    String token = jwtTokenProvider.generateToken(user);

    // return token
    return ResponseEntity.ok(new LoginResponse(token));
  }

}