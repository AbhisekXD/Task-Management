package com.dac.config;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dac.model.User;


public class UserInfoConfig implements UserDetails {

    private static final long serialVersionUID = 1L;
    
    private UUID id;
    private String userName;
    private String password;
    private List<GrantedAuthority> authorities;
    
   
    public UserInfoConfig(User user) {
    	this.id = user.getId();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        
        if (user.getRole() == null) {
            throw new IllegalArgumentException("User's Role is missing");
        }
        
        
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

   
    
    public UUID getId() {
        return id;
    }

   
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

   
    @Override
    public String getUsername() {
        return userName;
    }
    
  
    @Override
    public String getPassword() {
        return password;
    }
}
