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
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JWTUtils {
    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    private final Algorithm algorithm;

    private final MaineConfig maineConfig;

    @Autowired
    public JWTUtils(MaineConfig maineConfig) {
        this.maineConfig = maineConfig;

        this.algorithm = Algorithm.HMAC512(maineConfig.JWT().Token());
    }

    public String encode(Map<String, String> payload) {
        return JWT.create()
                .withExpiresAt(this.getExpireTime())
                .withIssuer(String.valueOf(System.currentTimeMillis()))
                .withPayload(payload)
                .sign(algorithm);
    }

    public String encode(long id) {

        return this.encode(String.valueOf(id));
//        return this.encode(new LinkedHashMap<>() {{
//            put("id", id);
//        }});
    }

    public String encode(String id) {
        return this.encode(new LinkedHashMap<>() {{
            put("id", id);
        }});
    }

    public boolean verify(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public OptionalLong decode(String token) {
        if (!this.verify(token)) {
            return OptionalLong.empty();
        }

        DecodedJWT decodedJWT = JWT.decode(token);

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(decodedJWT.getPayload());

        try {
            Map<String, Object> payloadMap = objectMapper.readValue(new String(Base64.decodeBase64(decodedJWT.getPayload())),
                    new TypeReference<>() {
                    });
            return OptionalLong.of(Long.parseLong((String) payloadMap.get("id")));
        } catch (JsonProcessingException e) {
            logger.error("JWT 解码失败", e);
            return OptionalLong.empty();
        }
    }

    private Date getExpireTime() {
        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, maineConfig.JWT().TTL());

        return calendar.getTime();
    }
}
