import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class ClientGUI implements ActionListener{

	public JTextArea chat = null;
	public JTextField newMessage = null;
	public JButton sendButton = null;
	String toAddr;
	ObjectOutputStream messageOut = null;
	ObjectInputStream  messageIn = null;
	//DataOutputStream messageOut = null;
	ClientGUI(String toAddr,ObjectOutputStream messageOut, ObjectInputStream  messageIn) {
		
		this.toAddr = toAddr;
		this.messageOut = messageOut;
		this.messageIn = messageIn;
		//createAndShowGUI();
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("[=] Client [=]");

		// Create and set up the content pane.
		//ClientGUI client = new ClientGUI(toAddr);
		frame.setContentPane(this.createContentPane());

		//frame.setDefaultCloseOperation(JFrame.ABORT);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(100, 100);
		frame.setSize(400, 400);
		frame.setVisible(true);
		new Messaging(this, messageIn);
	}

	private JPanel createContentPane() {

		// We create a bottom JPanel to place everything on.
		JPanel totalGUI = new JPanel();
		totalGUI.setLayout(null);

		// Creation of a Panel to contain the title labels
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(null);
		titlePanel.setLocation(150, 10);
		titlePanel.setSize(400, 30);
		totalGUI.add(titlePanel);

		JLabel titleLabel = new JLabel("Welcome to Chat");
		titleLabel.setLocation(0, 0);
		titleLabel.setSize(100, 30);
		titleLabel.setHorizontalAlignment(0);
		titleLabel.setForeground(Color.blue);
		titlePanel.add(titleLabel);

		// Creation of a Panel to contain the score labels.
		JPanel chatPanel = new JPanel();
		chatPanel.setLayout(null);
		chatPanel.setLocation(10, 50);
		chatPanel.setSize(390, 250);
		totalGUI.add(chatPanel);

		chat = new JTextArea("");
		chat.setLocation(0, 0);
		chat.setSize(370, 230);
		chat.setLineWrap(true);
		chat.setOpaque(false);
		chat.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		// chat.setCaretPosition(0);
		// chat.setMargin(new Insets(50, 50, 300, 150));
		chat.setEditable(false);
		chat.setVisible(true);
		// chat.setHorizontalAlignment(0);
		chatPanel.add(chat);

		/*JScrollPane scroll = new JScrollPane (chat);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		chatPanel.add(scroll);*/
		//scroll.setVisible(true);
		
		// Creation of a label to contain all the JButtons.
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setLocation(10, 310);
		bottomPanel.setSize(370, 70);
		totalGUI.add(bottomPanel);

		// We create a button and manipulate it using the syntax we have
		// used before.
		newMessage = new JTextField(SwingConstants.LEFT);
		newMessage.setLocation(0, 0);
		newMessage.setSize(250, 30);
		newMessage.requestFocusInWindow();
		bottomPanel.add(newMessage);

		sendButton = new JButton("Send");
		sendButton.setLocation(270, 0);
		sendButton.setSize(100, 30);
		sendButton.addActionListener(this);
		bottomPanel.add(sendButton);

		totalGUI.setOpaque(true);
		return totalGUI;

	}

	/*private static void createAndShowGUI() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("[=] Client [=]");

		// Create and set up the content pane.
		ClientGUI client = new ClientGUI(toAddr);
		frame.setContentPane(client.createContentPane());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(100, 100);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}*/

	/*public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
*/
	
	
	void send(String input){
		
		System.out.println("EFGH");
		//String input = ui.newMessage.getText();
		System.out.println("Input = " + input);
		MessagePacket m = new MessagePacket();
		m.setMessage(input);
		m.setAddr(toAddr);
		try {
			//System.out.println(socket.toString());
			//ObjectOutputStream messageOut = new ObjectOutputStream(socket.getOutputStream());
			System.out.println(messageOut);
			//messageOut.flush();
			//messageOut.writeUTF(toAddr);
			
			messageOut.writeObject(m);
			//messageOut.reset();
			//messageOut.flush();
			//messageOut.writeUTF(input.toString());
			//messageOut.flush();
		} catch (IOException e) {
			System.out.println("Could not write to stream");
			e.printStackTrace(System.out);
			return;
		}
		//new Messaging(0, socket, ui);
		//new Messaging(1, socket, ui);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == sendButton) {
			String message = newMessage.getText();
			send(message);
			newMessage.setText("");
			chat.setText(chat.getText() + "\nME : " + message);
		}

	}

}