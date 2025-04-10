package com.dac.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dac.config.UserInfoConfig;
import com.dac.model.User;
import com.dac.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUserName(username);
        

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with userName: " + username);
        }

        System.out.println("Loaded user: " + user.get());

        return new UserInfoConfig(user.get());
    }
}
