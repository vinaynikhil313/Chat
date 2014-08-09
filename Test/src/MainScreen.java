import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

public class MainScreen implements MouseListener {

	public JTextArea chat = null;
	public JTextField newMessage = null;
	public static JButton b[] = null;
	public JButton sendButton2 = null;
	Color C = new Color(59, 89, 182);

	private JPanel createContentPane() {

		// JPanel to place everything on.
		JPanel totalGUI = new JPanel();
		totalGUI.setLayout(null);
		totalGUI.setBackground(C);
		// Creation of a Panel to contain the title labels
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(null);
		titlePanel.setLocation(150, 10);
		titlePanel.setSize(100, 30);
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
		friends.setSize(370, 350);
		totalGUI.add(friends);

		JLabel titleLabel2 = new JLabel("Friends List");
		titleLabel2.setLocation(0, 0);
		titleLabel2.setSize(100, 30);
		titleLabel2.setHorizontalAlignment(0);
		titleLabel2.setForeground(Color.blue);
		friends.add(titleLabel2);

		b = new JButton[5];
		for (int i = 0; i < 5; i++) {
			b[i] = new JButton("172.30.102.178");
			b[i].addMouseListener(this);
			b[i].setBackground(C);
			b[i].setForeground(Color.BLACK);
			b[i].setSize(150, 30);
			b[i].setLocation(10, 40+30*i);
			b[i].setFocusPainted(false);
			b[i].setFont(new Font("Tahoma", Font.BOLD, 12));

			friends.add(b[i]);
		}
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setLocation(10, 470);
		bottomPanel.setSize(370, 70);
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

		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("[=] Client [=]");

		// Create and set up the content pane.
		MainScreen mainScreen = new MainScreen();
		frame.setContentPane(mainScreen.createContentPane());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(100, 100);
		frame.setSize(400, 600);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JButton temp = (JButton) e.getSource();
		temp.setForeground(Color.BLACK);
		String toAddr = temp.getText();
		new ClientGUI(toAddr);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		JButton temp = (JButton) e.getSource();
		temp.setBackground(Color.WHITE);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		JButton temp = (JButton) e.getSource();
		temp.setBackground(C);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}