package com.dac.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dac.config.security.JWTUtil;
import com.dac.dto.LoginCredentials;
import com.dac.dto.UserCreateDTO;
import com.dac.dto.UserResponseDTO;
import com.dac.model.User;
import com.dac.repository.UserRepository;
import com.dac.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired
    private JWTUtil jwtUtil;
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> registerHandler(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        logger.info("Received registration request for username: {}", userCreateDTO.getUserName());

        try {
           
            UserResponseDTO registeredUser = userService.registerUser(userCreateDTO);

            String token = jwtUtil.generateToken(registeredUser.getId(), registeredUser.getUserName(), registeredUser.getRole());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userName", registeredUser.getUserName());
            response.put("role", registeredUser.getRole());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (DataIntegrityViolationException e) {
            logger.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error occurred during registration", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "An unexpected error occurred"));
        }
    }

    
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginHandler(@Valid @RequestBody LoginCredentials credentials) {
        try {
            
            UsernamePasswordAuthenticationToken authCredentials = new UsernamePasswordAuthenticationToken(
                    credentials.getUserName(), credentials.getPassword());

            Authentication auth = authenticationManager.authenticate(authCredentials);

            
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String userName = userDetails.getUsername();

            Optional<User> optionalUser = userRepository.findByUserName(userName);

            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "User not found"));
            }

            User user = optionalUser.get();
            UUID id = user.getId();

            String role = userDetails.getAuthorities().stream().findFirst()
                    .map(authority -> authority.getAuthority().replace("ROLE_", "")).orElse("USER");

            String token = jwtUtil.generateToken(id, userName, role);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid user name or password"));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "An unexpected error occurred"));
        }
    }

}
