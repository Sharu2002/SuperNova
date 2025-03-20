package com.supernova.ai;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class AiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiApplication.class, args);
	}

	private final VectorStore vectorStore;

	public AiApplication(VectorStore vectorStore) {

		this.vectorStore = vectorStore;
	}
	@Configuration
	@Profile("llama")
	class AppConfig {

	}

}
