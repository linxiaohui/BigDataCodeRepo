package repo.bigdata;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class StormSimpleMain {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("StormSimpleMain Start");
		// Topology definition
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("input-spout", new StormSimpleSpout());
		builder.setBolt("sentence-spilt", new StormSimpleSplitBolt()).shuffleGrouping("input-spout");

		// Configuration
		Config conf = new Config();
		conf.setDebug(false);
		conf.put("value-from-main", "这里的参数会被Spout和Bolt获取");

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Getting-Started-Toplogie", conf, builder.createTopology());
		Thread.sleep(10000);
		cluster.shutdown();
	}

}
