package demo.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
	private final Logger logger = LoggerFactory.getLogger(Consumer .class);

	@KafkaListener(topics = "test", group = "group1")
	public void consume(String message) {
		logger.info(String.format("Message recieved -> %s", message));
		System.out.println("Message recieved -> "+ message);
	}
}
