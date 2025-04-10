package com.dac.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dac.dto.UserCreateDTO;
import com.dac.dto.UserResponseDTO;
import com.dac.model.User;
import com.dac.model.enums.Role;
import com.dac.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private ModelMapper modelMapper;
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserResponseDTO registerUser(UserCreateDTO userCreateDTO) {
        try {
           
            if (userRepo.existsByUserName(userCreateDTO.getUserName())) {
                logger.warn("User registration failed: Username already exists: {}", userCreateDTO.getUserName());
                throw new DataIntegrityViolationException("User already exists with user name: " + userCreateDTO.getUserName());
            }
            
            User user = modelMapper.map(userCreateDTO, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            
            if (userRepo.count() == 0) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.USER);
            }
            
            User registeredUser = userRepo.save(user);

            return modelMapper.map(registeredUser, UserResponseDTO.class);

        } catch (DataIntegrityViolationException e) {
            logger.error("User registration failed: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error during user registration", e);
            throw new RuntimeException("Unexpected error during user registration", e);
        }
    }

}
