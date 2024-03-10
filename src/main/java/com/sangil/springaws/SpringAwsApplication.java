package com.sangil.springaws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class SpringAwsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAwsApplication.class, args);
    }

}
