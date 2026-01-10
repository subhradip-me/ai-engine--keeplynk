package com.keeplynk.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
	basePackages = "com.keeplynk.ai",
	excludeFilters = @ComponentScan.Filter(
		type = FilterType.REGEX,
		pattern = "com\\.keeplynk\\.ai\\.memory\\..*"
	)
)
public class AiEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiEngineApplication.class, args);
	}

}
