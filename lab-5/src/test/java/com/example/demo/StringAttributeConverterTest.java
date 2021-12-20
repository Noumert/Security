package com.example.demo;

import com.example.demo.entity.RoleType;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.SecretDataEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


@SpringBootTest
class StringAttributeConverterTest {

    private static final Logger log = LoggerFactory.getLogger(StringAttributeConverterTest.class);

    @Autowired
    private UserRepository userRepository;

    @Test
    void testDataReadDecrypted() {
        User user = new User(null, "test@gmail1.com","test@gmail1.com" , "1234567812345678", RoleType.ROLE_USER);
        userRepository.save(user);

        User user1 = userRepository.findById(user.getId()).orElse(new User());
        Assertions.assertEquals(user.getEmail(), user1.getEmail());
        userRepository.delete(user);
    }

    @Test
    public void testEncryptDataStore() throws SQLException {
        User user = new User(null, "test@gmail2.com","test@gmail1.com" , "1234567812345678", RoleType.ROLE_USER);
        userRepository.save(user);

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab5", "root", "HegJVCa-XvkFSlzOdQKwd6bd4MTyOuAF");

        PreparedStatement stmt = con.prepareStatement("select * from user where id = ?");
        stmt.setLong(1, user.getId());

        ResultSet rs = stmt.executeQuery();
        rs.next();
        String email = rs.getString("email");
        Assertions.assertNotEquals(user.getEmail(), email);
        log.info("Email in database: {}", email);
        userRepository.delete(user);
    }

    @Test
    public void test() throws SQLException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String test = "Hello";
        String algorithm = "AES";
        SecretKey key = SecretDataEncoder.generateKey();
        IvParameterSpec iv = SecretDataEncoder.generateIv();
        String encoded = SecretDataEncoder.encrypt(test,key,iv);
        System.out.println(encoded);
        SecretKey key2 = new SecretKeySpec(key.getEncoded(),algorithm);
        String decoded = SecretDataEncoder.decrypt(encoded,key2,iv);
        System.out.println(decoded);



    }
}