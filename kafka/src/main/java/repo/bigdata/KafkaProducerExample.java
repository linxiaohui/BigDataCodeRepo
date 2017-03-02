package repo.bigdata;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class KafkaProducerExample {
    public static void main(String [] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers","localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        /* partitioner.class默认
         * org.apache.kafka.clients.producer.internals.DefaultPartitioner:
         * Key非null, murmur2; Key为null随机
         * */
        props.put("partitioner.class", "repo.bigdata.KafkaSimplePartioner");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String> (props);

        ProducerRecord<String, String> record = new ProducerRecord<String, String>("topic","key","value");
        
        producer.send(record);
        
        try {
			producer.send(record).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
        
        producer.send(record, new Callback() {
        	@Override
        	public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        		if (e != null) {
        			e.printStackTrace();
        		}
        	}
        	}
        );
        
        producer.close();
    }
}