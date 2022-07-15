package demo.sender;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaSender {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	String kafkaTopic = "test";

	public void send(String message) {

		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaTopic, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			public void onSuccess(final SendResult<String, String> message) {
				System.out.println("sent message= " + message.getProducerRecord().value() + " with offset= "
						+ message.getRecordMetadata().offset());
			}

			public void onFailure(final Throwable throwable) {
				System.out.println("unable to send message= " + message + "\n" + throwable);
			}
		});

		while (!future.isDone()) {
			System.out.println("Producer not done yet");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			ProducerRecord<String, String> record = future.get().getProducerRecord();
			RecordMetadata metadata = future.get().getRecordMetadata();

			System.out.println(record.key() + ", " + record.value() + ", " + record.topic() + "," + record.partition()
					+ "," + record.timestamp());

			System.out.println(metadata.checksum() + "," + metadata.timestamp());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}