package com.plm.poelman.java_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.plm.poelman.java_api.configs.EnvLoader;


@SpringBootApplication
public class PlmPoelmanJavaApiApplication {

    
    public static void main(String[] args) {
        EnvLoader.load();
        SpringApplication.run(PlmPoelmanJavaApiApplication.class, args);
    }

}
