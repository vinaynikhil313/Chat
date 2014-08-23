import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

public class MainScreen {

	public JTextField newFriend = null;
	// public static JButton b[] = null;
	private static HashMap<String, JLabel> status = null;
	// public static JLabel[] status = null;
	public JButton addButton = null;
	public JButton sendButton2 = null;
	private static Socket socket = null;
	private static String serverAddress = "172.30.102.178";
	private static int serverPort = 1234;
	private static JFrame frame = null;
	Color C = new Color(59, 89, 182);
	private JPanel friends = null;
	private int i = 0;
	static ObjectOutputStream outStream;
	static ObjectInputStream inStream;
	static HashMap<String, ClientGUI> openedWindows = null;
	FriendsList f = null;

	MainScreen() {
		openedWindows = new HashMap<String, ClientGUI>();
		status = new HashMap<String, JLabel>();
		f = new FriendsList();
		new Receiving();
		MessagePacket m = new MessagePacket();
		m.setType(2);
		try {
			outStream.writeObject(m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private JPanel createContentPane() {

		// JPanel to place everything on.
		JPanel totalGUI = new JPanel();
		totalGUI.setLayout(null);
		totalGUI.setBackground(C);

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(null);
		titlePanel.setLocation(10, 10);
		titlePanel.setSize(215, 30);
		// titlePanel.setBackground(Color.GRAY);
		totalGUI.add(titlePanel);

		JLabel titleLabel = new JLabel("Main Screen");
		titleLabel.setLocation(0, 0);
		titleLabel.setSize(100, 30);
		titleLabel.setHorizontalAlignment(0);
		titleLabel.setForeground(Color.blue);
		titlePanel.add(titleLabel);

		friends = new JPanel();
		friends.setLayout(null);
		friends.setLocation(10, 60);
		friends.setSize(265, 350);
		friends.setBackground(Color.WHITE);
		totalGUI.add(friends);

		JLabel titleLabel2 = new JLabel("Friends List");
		titleLabel2.setLocation(0, 0);
		titleLabel2.setSize(100, 30);
		titleLabel2.setHorizontalAlignment(0);
		titleLabel2.setForeground(Color.blue);
		friends.add(titleLabel2);

		int friendsCount = f.getCount();
		// b = new JButton[friendsCount];
		// status = new JLabel[friendsCount];
		String[] list = f.getFriendsList();
		for (i = 0; i < friendsCount; i++) {

			JPanel temp = new JPanel();
			temp.setLayout(null);
			temp.setLocation(10, 40 + 30 * i);
			temp.setSize(215, 30);
			temp.setBackground(Color.WHITE);
			temp.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));

			JButton b;
			b = new JButton(list[i]);
			b.addMouseListener(new Buttons(i));
			b.setBackground(C);
			b.setForeground(Color.BLACK);
			b.setSize(150, 30);
			b.setLocation(0, 0);
			b.setFocusPainted(false);
			b.setFont(new Font("Tahoma", Font.BOLD, 12));
			
			JLabel tempLabel = new JLabel("Offline");
			tempLabel = new JLabel("Offline");
			tempLabel.setLocation(160, 0);
			tempLabel.setSize(50, 30);
			tempLabel.setBackground(Color.WHITE);
			status.put(list[i], tempLabel);
			
			temp.add(b);
			temp.add(tempLabel);

			MessagePacket m = new MessagePacket();
			m.setType(1);
			m.setFromAddr(null);
			m.setToAddr(list[i]);
			try {
				outStream.writeObject(m);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			friends.add(temp);
		}

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setLocation(10, 470);
		bottomPanel.setSize(215, 70);
		totalGUI.add(bottomPanel);

		JLabel titleLabel3 = new JLabel("Add a Friend");
		titleLabel3.setLocation(0, 0);
		titleLabel3.setSize(100, 30);
		titleLabel3.setHorizontalAlignment(0);
		titleLabel3.setForeground(Color.blue);
		bottomPanel.add(titleLabel3, BorderLayout.NORTH);

		newFriend = new JTextField("", SwingConstants.WEST);
		newFriend.setLocation(10, 30);
		newFriend.setSize(120, 25);
		bottomPanel.add(newFriend, BorderLayout.WEST);

		addButton = new JButton("Add");
		addButton.setLocation(140, 30);
		addButton.setSize(50, 25);
		addButton.setMargin(new Insets(0, 0, 0, 0));
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if(f.getCount() > 0)
					f.addFriend("\r\n" + newFriend.getText());
				else if(f.getCount() == 0)
					f.addFriend(newFriend.getText());
				JPanel temp = new JPanel();
				temp.setLayout(null);
				temp.setLocation(10, 40 + 30 * i);
				temp.setSize(215, 30);
				temp.setBackground(Color.WHITE);
				temp.setBorder(BorderFactory
						.createEtchedBorder(EtchedBorder.LOWERED));

				JButton b = new JButton(newFriend.getText());
				b.addMouseListener(new Buttons(i));
				b.setBackground(C);
				b.setForeground(Color.BLACK);
				b.setSize(150, 30);
				b.setLocation(0, 0);
				b.setFocusPainted(false);
				b.setFont(new Font("Tahoma", Font.BOLD, 12));

				JLabel tempLabel = new JLabel("Offline");
				tempLabel.setLocation(160, 0);
				tempLabel.setSize(50, 30);
				tempLabel.setBackground(Color.WHITE);
				status.put(newFriend.getText(), tempLabel);
				
				MessagePacket m = new MessagePacket();
				m.setType(1);
				m.setFromAddr(null);
				m.setToAddr(newFriend.getText());
				try {
					outStream.writeObject(m);
				} catch (IOException e) {
					e.printStackTrace();
				}
				temp.add(b);
				temp.add(tempLabel);

				friends.add(temp);
				frame.repaint();
				i++;
				newFriend.setText("");
			}
		});
		bottomPanel.add(addButton, BorderLayout.EAST);

		totalGUI.setOpaque(true);
		return totalGUI;

	}

	private static void createAndShowGUI() {

		frame = new JFrame("[=] Client [=]");
		JFrame.setDefaultLookAndFeelDecorated(true);
		// Create and set up the content pane.
		MainScreen mainScreen = new MainScreen();
		frame.setContentPane(mainScreen.createContentPane());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(100, 100);
		frame.setSize(300, 600);
		frame.setBackground(Color.WHITE);
		frame.setVisible(true);
	}

	private static boolean connect() {

		System.out.println("Establishing connection. Please wait ...");

		try {

			socket = new Socket(serverAddress, serverPort);
			System.out.println("Connected: " + socket);
			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
			return false;
		} catch (IOException ioe) {
			System.out.println("Could not load data stream....Retry");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {

		while (!connect())
			;

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}

	class Buttons extends MouseAdapter {
		// private final int index;

		public Buttons(int index) {
			// this.index = index;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			JButton tempButton = (JButton) e.getComponent();
			tempButton.setForeground(Color.BLACK);
			String toAddr = tempButton.getText();
			if (openedWindows.get(toAddr) == null) {
				ClientGUI temp = new ClientGUI(toAddr, outStream);
				openedWindows.put(toAddr, temp);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			JButton tempButton = (JButton) e.getComponent();
			tempButton.setBackground(Color.WHITE);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JButton tempButton = (JButton) e.getComponent();
			tempButton.setBackground(C);
		}

	}

	public class Receiving implements Runnable {

		Thread t = null;

		Receiving() {

			t = new Thread(this);
			t.start();

		}

		@Override
		public void run() {

			if (inStream == null) {
				System.out.println("NULL");
			}
			while (true) {

				MessagePacket m = null;
				try {
					m = (MessagePacket) inStream.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (m.getType() == 0) {
					ClientGUI temp = openedWindows.get(m.getFromAddr());
					if (temp != null) {
						temp.chat.setText(temp.chat.getText() + "\n"
								+ m.getFromAddr() + " : " + m.getMessage());
						temp.chat.setCaretPosition(temp.chat.getDocument()
								.getLength());
						System.out.println("From Server " + m.getFromAddr()
								+ " : " + m.getMessage());
					} else {
						ClientGUI create = new ClientGUI(m.getFromAddr(),
								outStream);
						openedWindows.put(m.getFromAddr(), create);
						create.chat.setText(create.chat.getText() + "\n"
								+ m.getFromAddr() + " : " + m.getMessage());
						create.chat.setCaretPosition(create.chat.getDocument()
								.getLength());
					}
				}
				else if(m.getType() == 1)
				{
					JLabel temp = status.get(m.getToAddr());
					temp.setText(m.getMessage());
					frame.repaint();
				}
			}
		}

	}

}