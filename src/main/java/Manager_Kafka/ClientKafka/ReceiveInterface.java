package Manager_Kafka.ClientKafka;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import org.apache.zookeeper.KeeperException;

public class ReceiveInterface {
	public static JTextField message;
	public static JComboBox combo1 = new JComboBox();
	public static JComboBox combo = new JComboBox();
	public static JPanel onglet1 = new JPanel();
	public static JTextArea message_receive = new JTextArea();
	public static JScrollPane scroller = new JScrollPane(message_receive, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	public Producer producer = new Producer();
	public Consumer consumer = new Consumer(LoginDialog.ID.getText(),LoginDialog.ID.getText());


	public ReceiveInterface() {

		
		JFrame f = new JFrame(LoginDialog.ID.getText()+" - Messagerie");
	    f.setSize(1000, 600);
	    JPanel pannel = new JPanel();
	    

	    
	    JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);
	    JScrollPane scroller = new JScrollPane(message_receive);
	    scroller.setPreferredSize(new Dimension(800, 390));
	    onglet1.setPreferredSize(new Dimension(800, 400));
	    onglet1.add(scroller, BorderLayout.WEST);
	    onglets.addTab("Receive", onglet1);
	    
	    
	    
	    
	      JPanel onglet2 = new JPanel();
	     
	      JPanel pan_choice = new JPanel(new GridLayout(2,1));
	      JPanel pan_label = new JPanel(new GridLayout(2,1));
	      JPanel pan_textField = new JPanel(new GridLayout(2,1));
	      JPanel pan_button = new JPanel(new GridLayout(1,2));
	      onglet2.add(pan_choice, BorderLayout.NORTH);
	      onglet2.add(pan_label, BorderLayout.WEST);
	      onglet2.add(pan_textField, BorderLayout.CENTER);
	      onglet2.add(pan_button, BorderLayout.SOUTH);
	      pan_label.add(new JLabel(" Message: " ));
	   
	      //--------------------------------------------------------------//

	      //Choix de l'action
	      
	      
	      //Boutons ok et annuler
	      final JButton send = new JButton("Send" );
	      JButton annuler = new JButton("Annuler" );
	      send.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  producer.sendMessage(String.valueOf(combo.getSelectedItem()),LoginDialog.ID.getText()+"/"+message.getText());
		      }
	      });
	 
	      annuler.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
				  System.exit(0);
		      }
	      });
	     
	      //Champ de saisie du message
	      
	      message = new JTextField(20);

	    
	      // Ajout des elements aux panneaux
	      //-------------------------------------------------------//
	      
	      pan_choice.add(combo);
		  pan_textField.add(message);
		  pan_button.add(annuler);
		  pan_button.add(send);
		  
		  //--------------------------------------------------------//
	      
	      onglets.addTab("Send", onglet2);

	    onglets.setOpaque(true);
	    pannel.add(onglets);
	    f.getContentPane().add(pannel);
	    f.setVisible(true);
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			public void run() {
				List<String> listOfUsersOnline = null;
				try {
					listOfUsersOnline = ZooContact.zoo.getChildren("/online", new WatcherWorker());
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				ReceiveInterface.combo.removeAllItems();
				for (String str:listOfUsersOnline){
					if (!str.equals( LoginDialog.ID.getText())) {
						ReceiveInterface.combo.addItem(str);
					}
				}
			}
		}, 0, 5, TimeUnit.SECONDS);
	    while (true){
			consumer.readMessages();
		}

	  }
	
}
