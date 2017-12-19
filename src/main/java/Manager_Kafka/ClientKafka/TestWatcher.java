package Manager_Kafka.ClientKafka;

import org.apache.zookeeper.ZooKeeper;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TestWatcher {
    private static ZooKeeper zk;
    public static ZooContact conn;
    public static Producer producer;
	public static String URL_Kafka;	

    public static void main(String[] args) {
	URL_Kafka = args[0];
        ArrayList<Producer> producers = new ArrayList<Producer>();
        for(int i=0;i<100;i++){
            producer = new Producer();
            producers.add(producer);
        }
        System.out.println("Begining of the sending: " + (new Timestamp(System.currentTimeMillis())).toString());
        for(int j=0;j<100;j++){
            producers.get(j).sendMessage("henri",String.valueOf(j));
        }

    }
}
