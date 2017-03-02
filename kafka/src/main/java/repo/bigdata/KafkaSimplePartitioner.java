package repo.bigdata;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;

public class KafkaSimplePartitioner implements Partitioner {
	public void configure(Map<String, ?> configs) {
	}

	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
		int numPartitions = partitions.size();
		if ((keyBytes == null) || (!(key instanceof String)))
			throw new InvalidRecordException("all messages to have name as key");
		if (((String) key).equals("specialkey"))
			return numPartitions; // specialkey will always go to last partition
		// Other records will get hashed to the rest of the partitions
		return (Math.abs(Utils.murmur2(keyBytes)) % (numPartitions - 1));
	}

	public void close() {}
}