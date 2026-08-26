package dev.iamforyy.taskmanagementapi;

import dev.iamforyy.taskmanagementapi.auth.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class TaskManagementApiApplication {

    static void main(String[] args) {
        SpringApplication.run(TaskManagementApiApplication.class, args);
    }

}
