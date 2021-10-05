package sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootKafkaApp {

	public static void main(String[] args) {

		SpringApplication.run(new Object[] { SpringBootKafkaApp.class }, args);
	}

}
