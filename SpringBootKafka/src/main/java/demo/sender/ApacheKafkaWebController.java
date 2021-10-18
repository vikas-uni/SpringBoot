package demo.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/javainuse-kafka/")
public class ApacheKafkaWebController {

	@Autowired
	KafkaSender kafkaSender;

	@GetMapping(value = "/producer")
	public String producer(@RequestParam("message") String message) {
		try {
			kafkaSender.send(message);
			return "Message sent-post to the Kafka Topic java_in_use_topic Successfully";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "Message sent-post to the Kafka Topic java_in_use_topic Failed";
	}

	@PostMapping("/producer")
	String postProducer(@RequestBody String message) {
		try {
			kafkaSender.send(message);
			return "Message sent-post to the Kafka Topic java_in_use_topic Successfully";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "Message sent-post to the Kafka Topic java_in_use_topic Failed";
	}

	/*
	 * @PostMapping(value = "/publish") public void
	 * sendMessageToKafkaTopic(@RequestParam("message") String message) {
	 * this.producerService.sendMessage(message); }
	 */
}