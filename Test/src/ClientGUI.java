import java.awt.*;

import javax.swing.*;

public class ClientGUI{
	
	public JTextField chat = null;
	public JTextField newMessage = null;
	
	ClientGUI(){
		//new Client(this);
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

				chat = new JTextField(SwingConstants.LEFT);
				chat.setLocation(0, 0);
				chat.setSize(370, 230);
				chat.setCaretPosition(0);
				//chat.setMargin(new Insets(50, 50, 300, 150));
				chat.setEditable(false);
				chat.setHorizontalAlignment(0);
				chatPanel.add(chat);

				
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

				JButton sendButton = new JButton("Send");
				sendButton.setLocation(270, 0);
				sendButton.setSize(100, 30);
				bottomPanel.add(sendButton);

				totalGUI.setOpaque(true);
				return totalGUI;
		

	}

	private static void createAndShowGUI() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("[=] Client [=]");

		// Create and set up the content pane.
		ClientGUI client = new ClientGUI();
		frame.setContentPane(client.createContentPane());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(100, 100);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

}