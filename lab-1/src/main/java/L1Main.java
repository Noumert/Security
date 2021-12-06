import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class L1Main {
    public static final String textToEncode1 = "7958401743454e175617455247525" +
            "6435e59501a5c524e176f786517545e475f5245191772195019175e4317" +
            "445f58425b531743565c521756174443455e595017d5b7ab5f525b5b58174058" +
            "455b53d5b7aa175659531b17505e41525917435f52175c524e175e4417d5b7ab5c52" +
            "4ed5b7aa1b174f584517435f5217515e454443175b524343524517d5b7ab5fd5b7aa17405e435f" +
            "17d5b7ab5cd5b7aa1b17435f5259174f584517d5b7ab52d5b7aa17405e435f17d5b7ab52d5b7aa1b17435f525917d" +
            "5b7ab5bd5b7aa17405e435f17d5b7ab4ed5b7aa1b1756595317435f5259174f58451759524f4317545f564" +
            "517d5b7ab5bd5b7aa17405e435f17d5b7ab5cd5b7aa175650565e591b17435f525917d5b7ab58d5b7aa17405e4" +
            "35f17d5b7ab52d5b7aa1756595317445817585919176e5842175a564e17424452175659175e5953524f17585" +
            "11754585e59545e53525954521b177f565a5a5e595017535e4443565954521b177c56445e445c5e17524f565a5e" +
            "5956435e58591b17444356435e44435e54565b17435244434417584517405f564352415245175a52435f5853174e" +
            "5842175152525b174058425b5317445f584017435f52175552444317455244425b4319";

    public static final String textToEncode2 = "G0IFOFVMLRAPI1QJbEQDbFEYOFEPJxAfI10JbEMFIUAAKRAfOVIfOFkYOUQFI" +
            "15ML1kcJFUeYhA4IxAeKVQZL1VMOFgJbFMDIUAAKUgFOElMI1ZMOFgFPxADIlVMO1VMO1kAIBAZP1" +
            "VMI14ANRAZPEAJPlMNP1VMIFUYOFUePxxMP19MOFgJbFsJNUMcLVMJbFkfbF8CIElMfgZNbGQDbFcJOBA" +
            "YJFkfbF8CKRAeJVcEOBANOUQDIVEYJVMNIFwVbEkDORAbJVwAbEAeI1INLlwVbF4JKVRMOF9MOUMJbEMDIVVMP18eOBADKh" +
            "ALKV4JOFkPbFEAK18eJUQEIRBEO1gFL1hMO18eJ1UIbEQEKRAOKUMYbFwNP0RMNVUNPhlAbEMFIUUALUQJKBANIl4JLVwFIldMI" +
            "0JMK0INKFkJIkRMKFUfL1UCOB5MH1UeJV8ZP1wVYBAbPlkYKRAFOBAeJVcEOBACI0dAbEkDORAbJVwAbF4JKVRMJURMOF9MKFU" +
            "PJUAEKUJMOFgJbF4JNERMI14JbFEfbEcJIFxCbHIJLUJMJV5MIVkCKBxMOFgJPlVLPxACIxAfPFEPKUN" +
            "CbDoEOEQcPwpDY1QDL0NCK18DK1wJYlMDIR8II1MZIVUCOB8IYwEkFQcoIB1ZJUQ1CAMvE1cHOVUuOkYuCkA4eHMJL3c" +
            "8JWJffHIfDWIAGEA9Y1UIJURTOUMccUMELUIFIlc=";

    public static final String textToEncode3 = "EFFPQLEKVTVPCPYFLMVHQLUEWCNVW" +
            "FYGHYTCETHQEKLPVMSAKSPVPAPVYWMVHQLUSPQLYWLASLFVWPQLMVHQLUPLRPSQLULQES" +
            "PBLWPCSVRVWFLHLWFLWPUEWFYOTCMQYSLWOYWYETHQEKLPVMSAKSPVPAPVYWHEPPLUWSGYULEMQTLPP" +
            "LUGUYOLWDTVSQETHQEKLPVPVSMTLEUPQEPCYAMEWWYTYWDLUULTCYWPQLSEOLSVOHTLUYAPVWLYGDALSSVWDPQLNLCKCLRQE" +
            "ASPVILSLEUMQBQVMQCYAHUYKEKTCASLFPYFLMVHQLUPQLHULIVYASHEUEDUEHQBVTTPQLVWFLRYGMYVWMVFLWMLSPVTTBYUNESESADD" +
            "LSPVYWCYAMEWPUCPYFVIVFLPQLOLSSEDLVWHEUPSKCPQLWAOKLUYGMQEUEMPLUSVWENLCEWFEHHTCGULXALWMCEWETCSVSPYLEMQYG" +
            "PQLOMEWCYAGVWFEBECPYASLQVDQLUYUFLUGULXALWMCSPEPVSPVMSB" +
            "VPQPQVSPCHLYGMVHQLUPQLWLRPOEDVMETBYUFBVTTPENLPYPQLWLRPTEKLWZYCKVPTCSTESQPBYMEHVPETCMEHVPETZMEHVPETK" +
            "TMEHVPETCMEHVPETT";

    public static final String textToEncode4 = "UMUPLYRXOYRCKTYYPDYZTOUYDZHYJYUNTOMYTOLTKAOHOKZCMKAVZDYBR" +
            "ORPTHQLSERUOERMKZGQJOIDJUDNDZATUVOTTLMQBOWNMERQTDTUFKZCMTAZMEOJJJOXMERKJHACMTAZATIZOEPPJKIJJNOCFEP" +
            "LFBUNQHHPPKYYKQAZKTOTIKZNXPGQZQAZKTOTIZYNIUISZIAELMKSJOYUYYTHNEIEOESULOXLUEYGBEUGJLHAJTGGOEOSMJHN" +
            "FJALFBOHOKAGPTIHKNMKTOUUUMUQUDATUEIRBKYUQTWKJKZNLDRZBLTJJJIDJYSULJARKHKUKBISBLTOJRATIOITHYULFBITOV" +
            "HRZIAXFDRNIORLZEYUUJGEBEYLNMYCZDITKUXSJEJCFEUGJJOTQEZNORPNUDPNQIAYPEDYPDYTJAIGJYUZBLTJJYYNTMSEJYFN" +
            "KHOTJARNLHHRXDUPZIALZEDUYAOSBBITKKYLXKZNQEYKKZTOKHWCOLKURTXSKKAGZEPLSYHTMKRKJIIQZDTNHDYXMEIRMROGJ" +
            "YUMHMDNZIOTQEKURTXSKKAGZEPLSYHTMKRKJIIQZDTNROAUYLOTIMDQJYQXZDPUMYMYPYRQNYFNUYUJJEBEOMDNIYUOHYYYJHAO" +
            "QDRKKZRRJEPCFNRKJUHSJOIRQYDZBKZURKDNNEOYBTKYPEJCMKOAJORKTKJLFIOQHYPNBTAVZEUOBTKKBOWSBKOSKZUOZIHQSLI" +
            "JJMSURHYZJJZUKOAYKNIYKKZNHMITBTRKBOPNUYPNTTPOKKZNKKZNLKZCFNYTKKQNUYGQJKZNXYDNJYYMEZRJJJOXMERKJVOSJIO" +
            "SIQAGTZYNZIOYSMOHQDTHMEDWJKIULNOTBCALFBJNTOGSJKZNEEYYKUIXLEUNLNHNMYUOMWHHOOQ" +
            "NUYGQJKZLZJZLOLATSEHQKTAYPYRZJYDNQDTHBTKYKYFGJRRUFEWNT" +
            "HAXFAHHODUPZMXUMKXUFEOTIMUNQIHGPAACFKATIKIZBTOTIKZNKKZNLORUKMLLFBUUQKZNLEOH" +
            "IEOHEDRHXOTLMIRKLEAHUYXCZYTGUYXCZYTIUYXCZYTCVJOEBKOHE";




    public static void main(String[] args) throws DecoderException {
        System.out.println("1-1");
        System.out.println("7 7 7 7 7 7 7 7 7 7 7 7 7 7");
        byte[] bytes = Hex.decodeHex(textToEncode1.toCharArray());
        System.out.println();
        System.out.println("Decrypted String");
        MyEncryptors.xorSingleEncoder(new String(bytes));
    }


}
