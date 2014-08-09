import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Messaging implements Runnable {

	private int rdwr = -1;
	Socket s = null;
	private DataOutputStream messageOut = null;
	private DataInputStream messageIn = null;
	Thread t = null;
	private Scanner in;
	public ClientGUI cl = null;

	Messaging(int rdwr, Socket s, ClientGUI cl) {
		this.rdwr = rdwr;
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

			if (rdwr == 0) {
				in = new Scanner(System.in);
				// String input = in.nextLine();
				String input = cl.newMessage.getText();
				System.out.println("Input = " + input);
				try {
					messageOut.writeUTF("172.30.103.79");
					messageOut.flush();
					messageOut.writeUTF(input);
					messageOut.flush();
				} catch (IOException e) {
					System.out.println("Could not write to stream");
					e.printStackTrace(System.out);
					return;
				}
			} else if (rdwr == 1) {
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

}
