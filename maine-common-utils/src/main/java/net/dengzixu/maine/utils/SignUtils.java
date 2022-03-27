package net.dengzixu.maine.utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

public class SignUtils {

    public static String sign(String text, String secretKey) {
        return HmacSHA1(text, secretKey);
    }

    public static String sign(TreeMap<String, String> keyPair, String secretKey) {
        var entrySet = keyPair.entrySet();

        StringBuilder text = new StringBuilder();

        entrySet.forEach(item -> {
            text.append(item.getKey());
            text.append("=");
            text.append(item.getValue());
            text.append("&");
        });

        return sign(text.substring(0, text.length() - 1), secretKey);
    }


    private static String HmacSHA1(String message, String key) {
        String ALGORITHM = "HmacSHA1";
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);

        String result = null;
        try {
            Mac mac = Mac.getInstance(ALGORITHM);

            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, ALGORITHM);
            mac.init(signingKey);
            result = Hex.encodeHexString(mac.doFinal(messageBytes));
        } catch (NoSuchAlgorithmException | InvalidKeyException | NullPointerException e) {
            e.printStackTrace();
        }

        return result;
    }

}
