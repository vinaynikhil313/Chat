import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Messaging implements Runnable {
	
	Socket s = null;
	private ObjectOutputStream messageOut = null;
	private ObjectInputStream messageIn = null;
	Thread t = null;
	private Scanner in;
	public ClientGUI cl = null;

	Messaging(Socket s, ClientGUI cl) {

		this.s = s;
		this.cl = cl;
		System.out.println("IL");
		t = new Thread(this);
		System.out.println(s.getInetAddress() + " and " + s.isConnected() + " and " + s.isClosed());
		t.start();
		
	}

	@Override
	public void run() {

		try {
			
			System.out.println("IL1");
			messageIn = new ObjectInputStream(s.getInputStream());
			System.out.println("IL2");
		} catch (IOException e) {
			System.out.println("Could not create streams");
			e.printStackTrace(System.out);
		}
		
		while (true) {
			try {
				if (messageIn != null) {
					System.out.println("IJKL");
					//String message = messageIn.readUTF();
					MessagePacket m = (MessagePacket) messageIn.readObject();
					cl.chat.setText(cl.chat.getText() + "\n" + m.getAddr() + " : " + m.getMessage());
					System.out.println("From Server " + m.getAddr() + " : " + m.getMessage());
				}
			} catch (IOException e) {
				System.out.println("Could not read from stream");
				e.printStackTrace(System.out);
				return;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
