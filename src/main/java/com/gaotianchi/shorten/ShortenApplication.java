package com.gaotianchi.shorten;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ShortenApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortenApplication.class, args);
    }

}
