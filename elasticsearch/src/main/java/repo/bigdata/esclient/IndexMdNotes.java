/*
 * curl -XPUT  'http://localhost:9200/notes/'
 */
package repo.bigdata.esclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class IndexMdNotes {
	public TransportClient client;

	public IndexMdNotes() {
	}

	public void WalkDirectory(String root) throws IOException {
		System.out.println("Indexing Directory" + root);
		File input = new File(root);
		File[] files = input.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				if (f.getName().startsWith(".git")) {

				} else {
					WalkDirectory(f.getAbsolutePath());
				}
			} else {
				/* Open md notes */
				System.out.println("Indexing File" + f);
				Map<String, Object> json = new HashMap<String, Object>();
				json.put("title", f.getName());
				StringBuffer sb = new StringBuffer();
				BufferedReader reader = new BufferedReader(new FileReader(f));
				String r = null;
				while ((r = reader.readLine()) != null) {
					sb.append(r);
				}
				json.put("content", sb.toString());
				IndexResponse response = client.prepareIndex("notes", root).setSource(json).get();
			}
		}
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		/* Print Load CLASSPATH */
		for (String p : System.getProperty("java.class.path").split(":", -1)) {
			System.out.println(p);
		}
		IndexMdNotes es = new IndexMdNotes();
		Settings settings = Settings.builder().put("cluster.name", "escluster").build();

		es.client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		es.WalkDirectory(args[0]);
		es.client.close();
	}

}
