package repo.bigdata;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseExample {

	public static void main(String[] args) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		Configuration config = HBaseConfiguration.create();
		for (Entry<String, String> entry : config) {
			System.out.printf("%s=%s\n", entry.getKey(), entry.getValue());
		}
		System.out.println(System.getProperty("java.class.path"));

		Connection connection = ConnectionFactory.createConnection(config);
		// Create table
		Admin admin = connection.getAdmin();
		/* HBaseAdmin admin = new HBaseAdmin(config); */
		TableName tableName = TableName.valueOf("test");
		HTableDescriptor htd = new HTableDescriptor(tableName);
		/* HTableDescriptor htd = new HTableDescriptor("test"); */
		HColumnDescriptor hcd = new HColumnDescriptor("data");
		htd.addFamily(hcd);
		admin.createTable(htd);

		HTableDescriptor[] tables = admin.listTables();
		if (tables.length != 1 && Bytes.equals(tableName.getName(), tables[0].getTableName().getName()))
		/*
		 * if(tables.length!=1 && Bytes.equals(tableName, tables[0].getName()))
		 */
		{
			throw new IOException("Failed create table");
		}

		Table table = connection.getTable(tableName);
		/* HTable table = new HTable(config,tableName); */

		byte[] row1 = Bytes.toBytes("row1");
		Put p1 = new Put(row1);
		byte[] databytes = Bytes.toBytes("data");
		p1.addColumn(databytes, Bytes.toBytes("1"), Bytes.toBytes("Value 1"));
		/* p1.add(databytes,Bytes.toBytes("1"), Bytes.toBytes("Value 1")); */
		table.put(p1);

		Get g = new Get(row1);
		Result result = table.get(g);
		System.out.println("Get: " + result);

		Scan scan = new Scan();
		ResultScanner scanner = table.getScanner(scan);

		try {
			for (Result scannerResult : scanner) {
				System.out.println("Scan: " + scannerResult);
			}
		} finally {
			scanner.close();
		}
		/*
		 * admin.disableTable(tableName); admin.deleteTable(tableName);
		 */
	}

}
