package repo.bigdata;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class StormSimpleSplitBolt implements IRichBolt {

	OutputCollector collector;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		System.out.println("in Bolt prepare.===" + stormConf.get("value-from-main").toString());
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		String sentence = input.getString(0);
		String[] words = sentence.split(" ", -1);
		for (String word : words) {
			this.collector.emit(new Values(word));
			// System.out.println(word);
		}
	}

	@Override
	public void cleanup() {

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("field-bolt"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
