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

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Noumert on 13.08.2021.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    private static final String algorithmKey = "AES";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return mapUserToUserDetails(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password.")));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User saveEncrypted(User user) {
        return userRepository.save(encryptAndSaveKeyAndIv(user));
    }

    @Override
    public User findByEmail(String email) {
        return decryptData(userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid email")));
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

    public User decryptData(User user) {
        String fileKey = "C:/key/key" + user.getEmail() + ".txt";
        String fileIv = "C:/key/iv" + user.getEmail() + ".txt";

        try {
            byte[] dataKey = Files.readAllBytes(Paths.get(fileKey));
            byte[] dataIv = Files.readAllBytes(Paths.get(fileIv));
            SecretKey key;
            IvParameterSpec iv;
            key = new SecretKeySpec(dataKey, algorithmKey);
            iv = new IvParameterSpec(dataIv);
            user.setEmailSecret(SecretDataEncoder.decrypt(user.getEmailSecret(), key, iv));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User encryptAndSaveKeyAndIv(User user) {
        String fileKey = "C:/key/key" + user.getEmail() + ".txt";
        String fileIv = "C:/key/iv" + user.getEmail() + ".txt";


        try {
            File createFileKey = new File(fileKey);
            createFileKey.createNewFile();
            File createFileIv = new File(fileIv);
            createFileIv.createNewFile();
            byte[] dataKey = Files.readAllBytes(Paths.get(fileKey));
            byte[] dataIv = Files.readAllBytes(Paths.get(fileIv));
            SecretKey key;
            IvParameterSpec iv;
            if (dataKey.length != 0 || dataIv.length != 0) {
                key = new SecretKeySpec(dataKey, algorithmKey);
                iv = new IvParameterSpec(dataIv);
            } else {
                key = SecretDataEncoder.generateKey();
                iv = SecretDataEncoder.generateIv();
                Files.write(Path.of(fileKey), key.getEncoded());
                Files.write(Path.of(fileIv), iv.getIV());
            }
            user.setEmailSecret(SecretDataEncoder.encrypt(user.getEmailSecret(), key, iv));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}

