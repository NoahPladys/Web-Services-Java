package edu.ap.spring.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import edu.ap.spring.redis.controller.RedisController;
import edu.ap.spring.redis.redis.RedisService;

@SpringBootApplication
public class RedisApplication {

	private String CHANNEL = "edu:ap:redis";
	@Autowired
	private RedisService service;
	
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new ChannelTopic(CHANNEL));

		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(RedisController controller) {
		return new MessageListenerAdapter(controller, "onMessage");
	}
	
	@Bean
  	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return (args) -> {
			// empty db
			this.service.flushDb();

			// messaging
	 		service.sendMessage(CHANNEL, "Hello from Spring Boot");	 			 		
		};
  }
	
	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}
}
