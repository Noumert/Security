
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class Casino {
    public static final long m = 4294967296L;
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static long[] egcd(long a,long b){
        if(a==0){
            return new long[]{b, 0, 1};
        }else {
            long[] l = egcd(b % a, a);
            return new long[]{l[0],l[2] - b/a*l[1],l[1]};
        }
    }

    public static long findA(long[] realNumbers) {
        return (egcd(realNumbers[1] - realNumbers[0] + m,m)[1]%m * ((realNumbers[2] - realNumbers[1])% m)) % m;
    }

    public static long findC(long a, long[] realNumbers) {
        return (realNumbers[1] - a*realNumbers[0]) % m;
    }

    private static long getLCG() throws Exception {

        HttpGet request = new HttpGet("http://95.217.177.249/casino/playLcg?id=5112&bet=1&number=34689329");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();;
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                CasinoAnswer a = objectMapper.readValue(result, CasinoAnswer.class);
                System.out.println(a.realNumber);
                return a.realNumber;
            }

        }

        return 0;
    }

    public static void breakLCG() throws Exception {
        long a = 0;
        long c = 0;
        long[] realNumbers = new long[3];

        realNumbers[0] = getLCG();
        realNumbers[1] = getLCG();
        realNumbers[2] = getLCG();
        a = findA(realNumbers);
        c = findC(a,realNumbers);
        System.out.println("a:" + a);
        System.out.println("c: " + c);

        LcgRandom lcgRandom = new LcgRandom(realNumbers[2],a,c);
        System.out.println(lcgRandom.next());
        System.out.println(lcgRandom.next());
        System.out.println(lcgRandom.next());
        System.out.println(lcgRandom.next());
        System.out.println(lcgRandom.next());
        System.out.println(lcgRandom.next());
        System.out.println(lcgRandom.next());
        System.out.println(lcgRandom.next());
        System.out.println(lcgRandom.next());

    }

    public static void register(){
        HttpGet request = new HttpGet("http://95.217.177.249/casino/playLcg?id=2&bet=1&number=34689329");

    }

    public static void main(String[] args) throws Exception {
        breakLCG();
    }
}
