package demo.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
	private final Logger logger = LoggerFactory.getLogger(Consumer .class);

	@KafkaListener(topics = {
			"${TOPIC_NAME}" }, groupId = "group1", id = "Consumer1"/*
																	 * , topicPartitions = @TopicPartition(topic =
																	 * "mytest", partitions = {"0"})
																	 */)
	public void consume(String message) {
		logger.info(String.format("Message recieved Consumer-> %s", message));
		System.out.println("syso Message recieved Consumer-> "+ message);
	}
}
