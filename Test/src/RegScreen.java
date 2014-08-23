import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class RegScreen {

	public RegScreen(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("[=] Client [=]");

		frame.setContentPane(this.createContentPane());

		frame.setLocation(100, 100);
		frame.setSize(400, 400);
		frame.setVisible(true);
		System.out.println("ABDC");
	}

	private JPanel createContentPane() {

		JPanel totalGUI = new JPanel();
		totalGUI.setLayout(new BorderLayout());
		totalGUI.setOpaque(true);
		totalGUI.add(new JLabel("Label"));
		
		return totalGUI;
	}

	public static void main(String []args){
		
		new RegScreen();
		
	}
	
}
