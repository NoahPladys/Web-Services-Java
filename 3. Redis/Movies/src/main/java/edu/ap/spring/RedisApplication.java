package edu.ap.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import edu.ap.spring.redis.RedisService;

@SpringBootApplication
public class RedisApplication {
	
	@Autowired
	private RedisService service;
		
	@Bean
  	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return (args) -> {

			// empty db
			this.service.flushDb();

			// movies
			this.service.setKey("moviescount", "0");
			this.service.rpush("movies:1998:The Big Lebowski", "Jeff Bridges");
			this.service.rpush("movies:1998:The Big Lebowski", "John Goodman");
			this.service.rpush("movies:1998:The Big Lebowski", "John Turturro");
			this.service.rpush("movies:1998:The Big Lebowski", "Steve Buscemi");
	 		/*actors.add("Jeff Bridges");
			actors.add("actor2", "John Goodman");
			actors.add("actor3", "John Turturro");
			actors.add("actor4", "Steve Buscemi");
	 		this.service.hset("movies:1998:The Big Lebowski", actors);
			service.incr("moviescount");
	 		actors.clear();
	 		actors.add("actor1", "Billy Bob Thornton");
			actors.add("actor2", "Tony Cox");
			actors.add("actor3", "Lauren Graham");
	 		service.add("movies:2003:Bad Santa", actors);*/
			this.service.incr("moviescount");
			
			System.out.println("Total movies : " + service.getKey("moviescount"));
		};
  }
	
	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}
}
