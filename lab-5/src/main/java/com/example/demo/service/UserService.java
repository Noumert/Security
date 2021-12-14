package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Noumert on 20.08.2021.
 */
public interface UserService extends UserDetailsService {

    User save(User user);
}

