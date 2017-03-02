package repo.bigdata;

import java.io.IOException;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZooKeeperExample {

	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		ZooKeeper zk = new ZooKeeper("localhost:2181", 1000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event);
			}
		});
		
		//Create
		String path = zk.create("/Master", new byte[] {}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println(path);
		//Create Asynchronously
		zk.create("/Master2", new byte[] {} , ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new StringCallback() {
			@Override
			public void processResult(int rc, String path, Object ctx, String name) {
				Code.get(rc);
				System.out.println(rc);
				System.out.println(path);
				System.out.println(ctx);
				System.out.println(name);
			}}, "XYZ");
		
		//Get Data
		zk.getData("/Master", false, null);
		zk.getData("/Master2", false, new DataCallback() {
			@Override
			public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
				
			}} , "ZYX");
		
			
		
		Thread.sleep(10000);
		zk.close();
	}

}
