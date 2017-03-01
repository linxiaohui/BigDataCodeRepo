package api.bigdata.hdfs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class HDFSDemo {

	static String dst = "";
	static String local = "";
	public static void HDFSRead() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		FSDataInputStream in = fs.open(new Path(dst));
		byte [] b = new byte[1024];
		int size = in.read(b, 0, 1024);
		System.out.println(size);
	}

	public static void HDFSWrite() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
        InputStream in = new BufferedInputStream(new FileInputStream(local));
        OutputStream out = fs.create(new Path(dst), new Progressable() {
            public void progress() {
                System.out.print(".");
            }
        });
        IOUtils.copyBytes(in, out, 4096, true);
	}

	public static void HDFSDelete() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		fs.delete(new Path(dst), true);
	}

	public static void HDFSList() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		FileStatus [] statuses = fs.listStatus(new Path(dst), new PathFilter() { public boolean accept(Path path) {return true;}});
		for(FileStatus status:statuses) {
			System.out.println(status.toString());
		}
	}

	public static void main(String[] args) {

	}

}
