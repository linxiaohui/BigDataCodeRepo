package repo.bigdata;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class StormSimpleSpout implements IRichSpout {

	public static final long serialVersionUID = 1L;

	SpoutOutputCollector collector;
	Map conf;
	TopologyContext context;

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		/* The first method called */
		this.collector = collector;
		this.conf = conf;
		this.context = context;
	}

	@Override
	public void close() {

	}

	@Override
	public void activate() {

	}

	@Override
	public void deactivate() {
	}

	@Override
	public void nextTuple() {
		/*
		 * called periodically from the same loop as the ack() and fail()
		 * methods.
		 */
		String p = this.conf.get("value-from-main").toString();
		this.collector.emit(
				new Values("nextTuple is called periodically from the same loop as the ack() and fail() methods"), p);
		this.collector.emit(new Values("p"));
	}

	@Override
	public void ack(Object msgId) {
		System.out.println(msgId);
	}

	@Override
	public void fail(Object msgId) {
		System.out.println(msgId);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}