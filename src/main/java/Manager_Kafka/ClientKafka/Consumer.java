package Manager_Kafka.ClientKafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

public class Consumer {
    KafkaConsumer<String,String> consumer;

    public Consumer(String group_id, String topic) {
        Properties configProperties = new Properties();
        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Client.URL_Kafka);
        configProperties.put("auto.offset.reset", "earliest");
        configProperties.put("enable.auto.commit","true");
        configProperties.put("auto.commit.interval.ms", "1000");
        configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, group_id);
        consumer = new KafkaConsumer<String, String>(configProperties);
        consumer.subscribe(Arrays.asList(topic));
    }

    public void readMessages(){
        ConsumerRecords<String, String> records = consumer.poll(10);
        for(ConsumerRecord<String, String> record : records){
            //FOR TEST
            ReceiveInterface.message_receive.append("   ("+(new Timestamp(System.currentTimeMillis())+")" + record.value()+"\n"));
            //ReceiveInterface.message_receive.append("   "+record.value().split("/")[0]+" ("+(new Date()).toString()+"):  "+record.value().split("/")[1]+"\n");
        }
    }
}
