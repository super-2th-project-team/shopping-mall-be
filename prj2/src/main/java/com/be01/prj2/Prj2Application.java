package com.be01.prj2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Prj2Application {

    public static void main(String[] args) {
        SpringApplication.run(Prj2Application.class, args);
    }

}
