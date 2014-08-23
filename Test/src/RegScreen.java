import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class RegScreen implements ActionListener{

	JButton register = null;
	JTextField user = null;
	ObjectOutputStream outStream = null;
	JFrame frame = null;
	public RegScreen(ObjectOutputStream outStream){
		this.outStream = outStream;
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("[=] Registration [=]");

		frame.setContentPane(this.createContentPane());

		frame.setLocation(400, 200);
		frame.setSize(300, 200);
		frame.setVisible(true);
		System.out.println("ABDC");
	}

	private JPanel createContentPane() {

		JPanel totalGUI = new JPanel();
		totalGUI.setBackground(Color.WHITE);
		totalGUI.setLayout(null);
		totalGUI.setOpaque(true);
		user = new JTextField("");
		user.setLocation(40, 50);
		user.setSize(125, 35);
		user.setBorder(BorderFactory.createTitledBorder("Enter Nickname"));
		register = new JButton("Register");
		register.setLocation(175, 58);
		register.setSize(85, 25);
		
		register.addActionListener(this);
		totalGUI.add(user);
		totalGUI.add(register);
		
		return totalGUI;
	}
	
	public void close(){
		frame.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent windowEvent) {
		        
		    }
		});
	}

	public static void main(String []args){
		
		//new RegScreen(null);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==register)
		{
			MessagePacket m = new MessagePacket();
			m.setType(2);
			m.setMessage(user.getText());
			try {
				outStream.writeObject(m);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		MainScreen.flag=true;
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
}
