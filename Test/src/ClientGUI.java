import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	//Receiving r = null;
	//DataOutputStream messageOut = null;
	ClientGUI(final String toAddr,ObjectOutputStream messageOut) {
		
		this.toAddr = toAddr;
		this.messageOut = messageOut;
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("[=] Client [=]");

		frame.setContentPane(this.createContentPane());

		frame.setLocation(100, 100);
		frame.setSize(400, 400);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent windowEvent) {
		        MainScreen.openedWindows.remove(toAddr);
		    }
		});
	}

	private JPanel createContentPane() {

		// We create a bottom JPanel to place everything on.
		JPanel totalGUI = new JPanel();
		//totalGUI.setLayout(new GridLayout(0,1));
		totalGUI.setLayout(new BorderLayout());
		//totalGUI.setLayout(null);
		// Creation of a Panel to contain the title labels
		JPanel titlePanel = new JPanel();
		//titlePanel.setLayout(null);
		titlePanel.setLocation(150, 10);
		titlePanel.setSize(400, 30);
		totalGUI.add(titlePanel, BorderLayout.NORTH);

		JLabel titleLabel = new JLabel("Welcome to Chat");
		titleLabel.setLocation(0, 0);
		titleLabel.setSize(370, 30);
		//titleLabel.setHorizontalAlignment(0);
		titleLabel.setForeground(Color.blue);
		titlePanel.add(titleLabel);

		// Creation of a Panel to contain the score labels.
		JPanel chatPanel = new JPanel(new GridLayout(1,1));
		//chatPanel.setLayout(null);
		chatPanel.setLocation(10, 50);
		chatPanel.setSize(370, 250);
		totalGUI.add(chatPanel, BorderLayout.CENTER);

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
		chatPanel.add(new JScrollPane(chat));
		//chatPanel.add(chat);

		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.setLocation(10, 320);
		bottomPanel.setSize(400, 70);
		totalGUI.add(bottomPanel, BorderLayout.SOUTH);

		newMessage = new JTextField("", SwingConstants.WEST);
		//newMessage.setLocation(0, 0);
		//newMessage.setAlignmentX(0);
		newMessage.setSize(800, 70);
		bottomPanel.add(newMessage, BorderLayout.WEST);

		sendButton = new JButton("Send");
		sendButton.setLocation(270, 0);
		sendButton.setSize(100, 30);
		sendButton.addActionListener(this);
		bottomPanel.add(sendButton);

		totalGUI.setOpaque(true);
		return totalGUI;

	}

	void send(String input){
		
		System.out.println("EFGH");
		//String input = ui.newMessage.getText();
		System.out.println("Input = " + input);
		MessagePacket m = new MessagePacket();
		m.setMessage(input);
		m.setToAddr(toAddr);
		try {
			
			messageOut.writeObject(m);
			
		} catch (IOException e) {
			System.out.println("Could not write to stream");
			e.printStackTrace(System.out);
			return;
		}
		
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