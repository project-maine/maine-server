package net.dengzixu.maine;

import net.dengzixu.maine.utils.JWTUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTTest {
    private final JWTUtils jwtUtils;

    @Autowired
    public JWTTest(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Test
    void testJWT() {
        String token = jwtUtils.encode(10000000);

        System.out.println(token);

        Assertions.assertTrue(jwtUtils.verify(token));

        long id = jwtUtils.decode(token);

        System.out.println(id);
    }

    @Test
    void decodeTest() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiIxNjQwNjE3OTgwODAyIiwiaWQiOjEwMDAwMDAwLCJleHAiOjE2NDA2MTcyMDB9.EGIJV_SC34ea0e0m0OcUWXasgIfx2mIGalzLhHMdNvfcSJ5dR4URC0HSLK2UzhWztq1SsymfHAEoK6lUATDJBg";

        Assertions.assertFalse(jwtUtils.verify(token));

        long decode = jwtUtils.decode(token);

        System.out.println(decode);
    }
}
