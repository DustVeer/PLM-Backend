package com.plm.poelman.java_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.plm.poelman.java_api.configs.EnvLoader;

@SpringBootTest
class PlmPoelmanJavaApiApplicationTests {

	@Test
	public static void contextLoads() {
		EnvLoader.load();
	}

}
