package net.dengzixu.maine;

import net.dengzixu.maine.utils.SnowFlake;
import org.junit.jupiter.api.Test;

public class SnowFlakeTest {
    @Test
    void testSnowFlake() {
        SnowFlake snowFlake = new SnowFlake(0, 0);

        for (int i = 0; i < 10000; i++) {
            System.out.println(i + ":" + snowFlake.nextId());
        }

    }
}
