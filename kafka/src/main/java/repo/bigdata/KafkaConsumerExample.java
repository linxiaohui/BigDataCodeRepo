package repo.bigdata;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaConsumerExample {
	public static void main(String [] args) {
    	Properties props = new Properties();
    	props.put("bootstrap.servers", "localhost:9092");
    	props.put("group.id", "GroupId");
    	props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    	props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    	KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(props);
    	
    	consumer.subscribe(Collections.singletonList("topic"));
    	
    	try {
    		while (true) {
    			ConsumerRecords<String, String> records = consumer.poll(100);
    			for (ConsumerRecord<String, String> record : records) {
    				System.out.printf("topic = %s, partition = %s, offset = %d, customer = %s,country = %s\n",
    						record.topic(), record.partition(), record.offset(), record.key(), record.value());
    			}
    		}
    	} 
    	finally {
    		consumer.close();
    	}
    }
}