package com.vn.newspaperbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan("com.vn.newspaperbe.entity")
public class NewspaperBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewspaperBeApplication.class, args);
    }
}
