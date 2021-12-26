package net.dengzixu.maine.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

public class RandomGenerator {
    private static final UniformRandomProvider rng = RandomSource.MT.create();

    public static String nextSMSCode() {
        return String.valueOf(rng.nextInt(900000) + 100000);
    }

    public static String nextPassword() {
        byte[] randomData = new byte[4096];
        rng.nextBytes(randomData);

        return Base64.encodeBase64String(randomData);
    }
}
