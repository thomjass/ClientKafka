package Manager_Kafka.ClientKafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Producer {
    KafkaProducer<String, String> prod ;
    String URL_Kafka;
    public Producer(){
	if (Client.URL_Kafka == null){
		URL_Kafka = TestWatcher.URL_Kafka;
	}else{
		URL_Kafka = Client.URL_Kafka;
	}
        Properties configProperties = new Properties();
        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, URL_Kafka);
        configProperties.put("acks", "all");
        configProperties.put("retries", 0);
        configProperties.put("batch.size", 16384);
        configProperties.put("buffer.memory", 33554432);
        configProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        configProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prod = new KafkaProducer<String, String>(configProperties);
    }

    public void sendMessage(String topic, String message){
        prod.send(new ProducerRecord<String, String>(topic,message));
    }
}
