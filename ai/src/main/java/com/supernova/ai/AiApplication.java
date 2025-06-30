package com.supernova.ai;

import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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

	@Bean
	QuestionAnswerAdvisor questionAnswerAdvisor() {
		return new QuestionAnswerAdvisor(this.vectorStore);
	}
	@Configuration
	@Profile("llama")
	class AppConfig {

	}



}
