package edu.ap.spring.infraction;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.InputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import edu.ap.spring.infraction.jpa.*;

@SpringBootApplication
public class InfractionApplication implements CommandLineRunner {

	@Value("classpath:static/infractions.json")
    public InputStream data;

	@Autowired
	private InfractionRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(InfractionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        Infraction[] infractions = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            infractions = objectMapper.readValue(data, Infraction[].class);
        } catch (Exception e) {
             e.printStackTrace();
        } 
        for (int i = 0; i < infractions.length; i++) {
            repo.save(infractions[i]);
        }
	}
}
