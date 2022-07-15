package demo.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer2 {
	private final Logger logger = LoggerFactory.getLogger(Consumer2 .class);

	@KafkaListener(topics = "test", group = "group1", id="Consumer2")
	public void consume(String message) {
		logger.info(String.format("Message recieved Consumer2-> %s", message));
		System.out.println("syso Message recieved Consumer2-> "+ message);
	}
}
