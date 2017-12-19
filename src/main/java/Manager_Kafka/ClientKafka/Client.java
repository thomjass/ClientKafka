package Manager_Kafka.ClientKafka;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


public class Client {
	private static ZooKeeper zk;
	public static ZooContact conn;
	public static LoginDialog log;
	public static String URL_Kafka;

	public static void main(String[] args) {
		URL_Kafka = args[1];
		conn = new ZooContact();
	    try {
	    	//Here the IP adress of the master
			zk = conn.connect(args[0]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	    try {
			WatcherConn.connectionLatch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(zk.getState());
		
		log = new LoginDialog(conn);
	    log.setVisible(true);
	
	}

}
