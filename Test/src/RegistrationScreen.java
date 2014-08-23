import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;

class RegistrationScreen {

	static ObjectOutputStream outStream = null;

	public RegistrationScreen(ObjectOutputStream outStream) {
		RegistrationScreen.outStream = outStream;
		display();
	}

	private static void display() {
		JTextField user = new JTextField("");
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel("Enter username:"));
		panel.add(user);
		int result = JOptionPane.showConfirmDialog(null, panel, "Registration",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			if (user.getText().equals("")) {
				JOptionPane.showMessageDialog(null,
						"please enter a valid username", "ERROR", 1);
				display();
			} else {
				System.out.println(user.getText());
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
		} else {
			System.out.println("Cancelled");
		}
	}

}