package net.dengzixu.maine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "maine-config")
public record MaineConfig(SnowFlake SnowFlake, AliyunSMS AliyunSMS, JWT JWT) {
    public record SnowFlake(Long DataCenterId, Long MachineId) {

    }

    public record AliyunSMS(String AccessKeyId, String AccessSecret) {

    }

    public record JWT(String Token, Integer TTL) {
    }
}
