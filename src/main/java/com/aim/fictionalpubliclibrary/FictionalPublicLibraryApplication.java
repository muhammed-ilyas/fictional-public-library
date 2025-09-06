package com.aim.fictionalpubliclibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FictionalPublicLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(FictionalPublicLibraryApplication.class, args);
    }

}
