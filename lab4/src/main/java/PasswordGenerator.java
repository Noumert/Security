import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordGenerator {
    public static final String[] commonWords =
            {
                    "password",
                    "qwerty",
                    "dragon",
                    "pussy",
                    "baseball",
                    "football",
                    "letmein",
                    "monkey",
                    "mustang",
                    "michael",
                    "shadow",
                    "master",
                    "jennifer",
                    "jordan",
                    "superman",
                    "harley",
                    "hunter",
                    "fuckme",
                    "ranger",
                    "buster",
                    "thomas",
                    "tigger",
                    "robert",
                    "soccer",
                    "batman",
                    "test",
                    "pass",
                    "killer",
                    "hockey",
                    "george",
                    "charlie",
                    "andrew",
                    "michelle",
                    "love",
                    "sunshine",
                    "jessica",
                    "pepper",
                    "daniel",
                    "access",
                    "joshua",
                    "maggie",
                    "starwars",
                    "silver",
                    "yankees",
                    "hello",
                    "freedom"
            };

    public static final String[] commonPasswords =
            {
                    "123456",
                    "123456789",
                    "password",
                    "12345678",
                    "111111",
                    "123123",
                    "12345",
                    "1234567890",
                    "1234567",
                    "qwerty123",
                    "000000",
                    "1q2w3e",
                    "aa12345678",
                    "abc123",
                    "password1",
                    "1234",
                    "qwertyuiop",
                    "123321",
                    "password123",
                    "1q2w3e4r5t",
                    "iloveyou",
                    "654321",
                    "666666",
                    "987654321",
                    "123",
                    "123456a",
                    "qwe123",
                    "1q2w3e4r",
                    "7777777",
                    "1qaz2wsx",
                    "123qwe",
                    "zxcvbnm",
                    "121212",
                    "11111111",
                    "asdasd",
                    "a123456",
                    "555555",
                    "dragon",
                    "112233",
                    "123123123",
                    "monkey",
                    "asdfghjkl",
                    "222222",
                    "1234qwer",
                    "asdfgh"
            };

    static Random random = new Random();
    static SecureRandom security = new SecureRandom();

    public static List<String> createPasswords(int amount){
        List<String> result = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int decide = random.nextInt(100);
            if(decide<5){
                result.add(createStrongPasswords());
            }else if(decide>5 && decide<90){
                result.add(createWeakPasswords());
            }else {
                result.add(createSuperWeakPasswords());
            }
        }
        return result;
    }

    public static String createSuperWeakPasswords(){
        return commonPasswords[random.nextInt(commonPasswords.length)];
    }

    public static String createWeakPasswords(){
        return commonWords[random.nextInt(commonWords.length)]+random.nextInt(10000)
                + commonWords[random.nextInt(commonWords.length)] + random.nextInt(10000);
    }

    public static String createStrongPasswords(){
        int length = security.nextInt(8,20);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append((char)security.nextInt(21,125));
        }
        return result.toString();
    }
}
