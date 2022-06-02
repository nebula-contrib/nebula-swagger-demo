package com.example.nebula.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "nebula")
@EnableConfigurationProperties(NebulaGraphProperties.class)
@Data
public class NebulaGraphProperties {
    private String userName;
    private String password;
    /**
     * 格式：ip:prot
     */
    private List<String> hostAddresses;
    private int minConnsSize;
    private int maxConnSize;
    private int timeout;
    private int idleTime;
}
