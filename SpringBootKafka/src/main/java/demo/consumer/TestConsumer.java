package demo.consumer;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

//this also works 
public class TestConsumer {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "[::1]:9092");
		props.put("group.id", "group1");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(Collections.singletonList("mytest"));// name of topic

		try {
			while (true) {

				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					System.out.println("topic = " + record.topic() + ", partition = " + record.partition()
							+ ", offset = " + record.offset() + ", value = " + record.value()

					);

					int updatedCount = 1;
				}

			}

		} finally {
			consumer.close();
		}

	}

}
