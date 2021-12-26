package net.dengzixu.maine;

import net.dengzixu.maine.config.MaineConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MaineConfig.class)
public class MaineCommonConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaineCommonConfigApplication.class, args);
    }

}
