import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MainScreen{

	public JTextArea chat = null;
	public JTextField newMessage = null;
	public static JButton b[] = null;
	public JButton sendButton2 = null;
	private static Socket socket = null;
	private static String serverAddress = "172.30.102.178";
	private static int serverPort = 8080;
	Color C = new Color(59, 89, 182);
	static int isOpen[] = null;
	static ObjectOutputStream outStream;
	static ObjectInputStream inStream;
	private JPanel createContentPane() {

		// JPanel to place everything on.
		JPanel totalGUI = new JPanel();
		totalGUI.setLayout(null);
		totalGUI.setBackground(C);
		// Creation of a Panel to contain the title labels
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(null);
		titlePanel.setLocation(10, 10);
		titlePanel.setSize(220, 30);
		totalGUI.add(titlePanel);

		JLabel titleLabel = new JLabel("Main Screen");
		titleLabel.setLocation(0, 0);
		titleLabel.setSize(100, 30);
		titleLabel.setHorizontalAlignment(0);
		titleLabel.setForeground(Color.blue);
		titlePanel.add(titleLabel);

		// Creation of a Panel to contain the score labels.
		JPanel friends = new JPanel();
		friends.setLayout(null);
		friends.setLocation(10, 60);
		friends.setSize(220, 350);
		totalGUI.add(friends);

		JLabel titleLabel2 = new JLabel("Friends List");
		titleLabel2.setLocation(0, 0);
		titleLabel2.setSize(100, 30);
		titleLabel2.setHorizontalAlignment(0);
		titleLabel2.setForeground(Color.blue);
		friends.add(titleLabel2);

		b = new JButton[5];
		isOpen = new int[5];
		for (int i = 0; i < 5; i++) {
			b[i] = new JButton("172.30.102.178");
			b[i].addMouseListener(new Buttons(i));
			b[i].setBackground(C);
			b[i].setForeground(Color.BLACK);
			b[i].setSize(150, 30);
			b[i].setLocation(10, 40+30*i);
			b[i].setFocusPainted(false);
			b[i].setFont(new Font("Tahoma", Font.BOLD, 12));
			
			isOpen[i]=0;
			
			friends.add(b[i]);
		}
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setLocation(10, 470);
		bottomPanel.setSize(220, 70);
		totalGUI.add(bottomPanel);

		JLabel titleLabel3 = new JLabel("Add Friend");
		titleLabel3.setLocation(0, 0);
		titleLabel3.setSize(100, 30);
		titleLabel3.setHorizontalAlignment(0);
		titleLabel3.setForeground(Color.blue);
		bottomPanel.add(titleLabel3);

		totalGUI.setOpaque(true);
		return totalGUI;

	}

	private static void createAndShowGUI() {

		
		JFrame frame = new JFrame("[=] Client [=]");
		JFrame.setDefaultLookAndFeelDecorated(true);
		// Create and set up the content pane.
		MainScreen mainScreen = new MainScreen();
		frame.setContentPane(mainScreen.createContentPane());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(100, 100);
		frame.setSize(250, 600);
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
	    private final int index;

	    public Buttons(int index) {
	        this.index = index;
	    }

	    @Override
		public void mouseClicked(MouseEvent arg0) {
			
	    	b[index].setForeground(Color.BLACK);
			String toAddr = b[index].getText();
			if(isOpen[index]==0)
			{
				new ClientGUI(toAddr, outStream, inStream, index);
				isOpen[index]=1;
			}
	    	
		}

	    @Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			b[index].setBackground(Color.WHITE);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			b[index].setBackground(C);
		}

	}
	
}