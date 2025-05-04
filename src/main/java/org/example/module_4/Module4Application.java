package org.example.module_4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class Module4Application {

    public static void main(String[] args) {
        SpringApplication.run(Module4Application.class, args);
    }

}
