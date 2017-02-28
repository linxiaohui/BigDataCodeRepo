package repo.bigdata.flume.selector;

import java.util.List;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.channel.AbstractChannelSelector;

import java.util.ArrayList;

import java.util.Random;

public class RandomChannelSelector extends AbstractChannelSelector {

	Random random = new Random();
	int channelSize = getAllChannels().size();
	ArrayList<Channel> emptyChannels = new ArrayList<Channel>();

	@Override
	public List<Channel> getRequiredChannels(Event event) {
		int index = random.nextInt(channelSize);
		return getAllChannels().subList(index, index+1);
	}

	@Override
	public List<Channel> getOptionalChannels(Event event) {
		return emptyChannels;
	}

	@Override
	public void configure(Context context) {
		long  seed = context.getInteger("randome.seed", 0);
		random.setSeed(seed);
	}

	public static void main(String[] args) {

	}

}
