import jakarta.xml.bind.DatatypeConverter;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.xml.crypto.dsig.DigestMethod.SHA1;

public class PasswordGeneratorMain {
    public static List<String> getMD5(List<String> passwords) throws NoSuchAlgorithmException {
        List<String> result = new ArrayList<>();
        for (String password :
                passwords) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
            result.add(myHash);
        }

        return result;
    }

    public static List<String> getBCrypt(List<String> passwords) {
        List<String> result = new ArrayList<>();
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(5);
        result = passwords.stream().parallel()
                .map(bcrypt::encode)
                .map(h -> h.substring(0, 30) + ";" + h).toList();
        return result;
    }


    private static void saveToCSV(List<String> lines, String filename) throws FileNotFoundException {
        File csvOutputFile = new File(filename + ".csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            lines.forEach(pw::println);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException {
        saveToCSV(getMD5(PasswordGenerator.createPasswords(100000)),"weak");
        saveToCSV(getBCrypt(PasswordGenerator.createPasswords(100000)),"strong");
    }
}
