package repo.bigdata.esclient;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ClientDemo {
	public static void mian(String [] args) throws UnknownHostException {
		Settings settings = Settings.builder()
		        .put("cluster.name", "escluster").build();
		@SuppressWarnings("resource")
		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host1"), 9300))
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host2"), 9300));
		
		String json = "{" +
		        "\"user\":\"kimchy\"," +
		        "\"postDate\":\"2013-01-30\"," +
		        "\"message\":\"trying out Elasticsearch\"" +
		    "}";

		IndexResponse response = client.prepareIndex("twitter", "tweet")
		        .setSource(json)
		        .get();
		
		GetResponse get = client.prepareGet("twitter", "tweet", "1").setOperationThreaded(false).get();
		
		DeleteResponse del = client.prepareDelete("twitter", "tweet", "1").get();

		// on shutdown
		client.close();

	}
}
