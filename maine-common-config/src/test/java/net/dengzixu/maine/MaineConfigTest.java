package net.dengzixu.maine;

import net.dengzixu.maine.config.MaineConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MaineConfigTest {
    private final MaineConfig maineConfig;

    @Autowired
    public MaineConfigTest(MaineConfig maineConfig) {
        this.maineConfig = maineConfig;
    }

    @Test
    public void Test() {
        System.out.println(maineConfig);
    }
}
