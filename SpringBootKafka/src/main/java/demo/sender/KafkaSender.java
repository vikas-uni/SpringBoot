package demo.sender;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaSender {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${TOPIC_NAME}")
	private String kafkaTopic;// = "test";

	public void send(String message) {

		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaTopic, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			public void onSuccess(final SendResult<String, String> message) {
				System.out.println("sent message= " + message.getProducerRecord().value() + ", partition= "
						+ message.getProducerRecord().partition() + " , offset= "
						+ message.getRecordMetadata().offset());
			}

			public void onFailure(final Throwable throwable) {
				System.out.println("unable to send message= " + message + "\n" + throwable);
			}
		});

		try {
			while (!future.isDone()) {
				System.out.println("Producer not done yet");
				TimeUnit.SECONDS.sleep(1);
			}
			System.out.println("Producer done !!!");
			ProducerRecord<String, String> producerRecord = future.get().getProducerRecord();
			RecordMetadata metadata = future.get().getRecordMetadata();

			System.out.println("Producer***********");
			System.out.println("Key: " + producerRecord.key() + ", Value: " + producerRecord.value() + ", topic: "
					+ producerRecord.topic() + ", partition: " + producerRecord.partition() + ", timestamp: "
					+ producerRecord.timestamp());

			System.out.println("ser value size : " + metadata.serializedValueSize() + ", RecordMetadata timestamp:"
					+ metadata.timestamp() + ", RecordMetadata partition: " + metadata.partition());
			System.out.println("Producer***********");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}