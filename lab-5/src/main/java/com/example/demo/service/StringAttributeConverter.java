package com.example.demo.service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class StringAttributeConverter implements AttributeConverter<String, String> {

    Random random = new SecureRandom();
    private static final String AES = "AES";
    private final Key key;

    private final Cipher cipher;

    public static byte[] generateEncryptionKey() throws NoSuchAlgorithmException {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        return key;
    }

    public static SecretKeySpec generateKeyIfNotExist() throws NoSuchAlgorithmException, IOException {
        String file = "C:/key/key.txt";

        byte[] data = Files.readAllBytes(Paths.get(file));

        if (data.length!=0) {
            return new SecretKeySpec(data, AES);
        } else {
            byte[] newKey = generateEncryptionKey();
            Files.write(Path.of(file) , newKey);

            return new SecretKeySpec(newKey, AES);
        }

    }

    public StringAttributeConverter() throws Exception {
        key = generateKeyIfNotExist();
        cipher = Cipher.getInstance(AES);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}