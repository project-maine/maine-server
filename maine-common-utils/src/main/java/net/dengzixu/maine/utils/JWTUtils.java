package net.dengzixu.maine.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dengzixu.maine.config.MaineConfig;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JWTUtils {
    private final Algorithm algorithm;

    private final MaineConfig maineConfig;

    @Autowired
    public JWTUtils(MaineConfig maineConfig) {
        this.maineConfig = maineConfig;

        this.algorithm = Algorithm.HMAC512(maineConfig.JWT().Token());
    }

    public String encode(Map<String, Object> payload) {
        return JWT.create()
                .withExpiresAt(this.getExpireTime())
                .withIssuer(String.valueOf(System.currentTimeMillis()))
                .withPayload(payload)
                .sign(algorithm);
    }

    public String encode(long id) {
        return this.encode(new LinkedHashMap<>() {{
            put("id", id);
        }});
    }

    public boolean verify(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public long decode(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, Object> payloadMap = objectMapper.readValue(new String(Base64.decode(decodedJWT.getPayload())),
                    new TypeReference<>() {
                    });

            return null != payloadMap.get("id") ? ((Number) payloadMap.get("id")).longValue() : -1;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return -1;
        }

//        return decodedJWT.getPayload();
    }

    private Date getExpireTime() {
        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, maineConfig.JWT().TTL());

        return calendar.getTime();
    }
}
