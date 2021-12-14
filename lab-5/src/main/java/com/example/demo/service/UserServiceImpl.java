package com.example.demo.service;

import com.example.demo.entity.MyUserDetails;
import com.example.demo.entity.RoleType;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Noumert on 13.08.2021.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return mapUserToUserDetails(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password.")));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    private Collection<GrantedAuthority> mapRolesToAuthorities(RoleType role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    private MyUserDetails mapUserToUserDetails(User user) {
        return MyUserDetails.builder()
                .authorities(mapRolesToAuthorities(user.getRole()))
                .id(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }


}

