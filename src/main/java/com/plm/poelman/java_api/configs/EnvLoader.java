package com.plm.poelman.java_api.configs;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {

    public static void load() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(e ->
                System.setProperty(e.getKey(), e.getValue())
        );
    }
}
