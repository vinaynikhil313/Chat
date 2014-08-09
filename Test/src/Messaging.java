import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Messaging implements Runnable {
	
	Socket s = null;
	private DataOutputStream messageOut = null;
	private DataInputStream messageIn = null;
	Thread t = null;
	private Scanner in;
	public ClientGUI cl = null;

	Messaging(Socket s, ClientGUI cl) {

		this.s = s;
		this.cl = cl;
		t = new Thread(this);
		t.start();
		try {
			messageOut = new DataOutputStream(s.getOutputStream());
			messageIn = new DataInputStream(s.getInputStream());
		} catch (IOException e) {
			System.out.println("Could not create streams");
			e.printStackTrace(System.out);
		}
	}

	@Override
	public void run() {

		while (true) {
			try {
				if (messageIn != null) {
					System.out.println("IJKL");
					String message = messageIn.readUTF();
					cl.chat.setText(cl.chat.getText() + "\n" + message);
					System.out.println("From Server " + message);
				}
			} catch (IOException e) {
				System.out.println("Could not read from stream");
				e.printStackTrace(System.out);
				return;
			}
		}
	}

}
