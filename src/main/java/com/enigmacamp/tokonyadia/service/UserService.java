package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.entity.AppUser;
import com.enigmacamp.tokonyadia.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser loadUserById(String id);
}
