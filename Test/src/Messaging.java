import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Messaging implements Runnable {
	
	//Socket s = null;
	private ObjectOutputStream messageOut = null;
	private ObjectInputStream messageIn = null;
	Thread t = null;
	public ClientGUI cl = null;

	Messaging(ClientGUI cl, InputStream in) {

		//this.s = s;
		this.cl = cl;
		System.out.println("IL");
		//createStream();
		t = new Thread(this);
		//System.out.println(s.getInetAddress() + " and " + s.isConnected() + " and " + s.isClosed());
		t.start();
		
	}
	
	

	private void createStream() {
		// TODO Auto-generated method stub
		try {
			this.messageIn = new ObjectInputStream(cl.socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public void run() {

		/*try {
			
			System.out.println("IL1");
			System.out.println(s.getInetAddress() + " and " + s.isConnected() + " and " + s.isClosed());
			messageIn = new ObjectInputStream(s.getInputStream());
			System.out.println("IL2");
		} catch (IOException e) {
			System.out.println("Could not create streams");
			e.printStackTrace(System.out);
		}*/
		if(messageIn == null)
		{
			System.out.println("NULL");
			createStream();
		}
		while (true) {
			try {
				//if (messageIn != null) {
					System.out.println("IJKL");
					//String message = messageIn.readUTF();
					MessagePacket m = (MessagePacket) messageIn.readObject();
					messageIn.reset();
					cl.chat.setText(cl.chat.getText() + "\n" + m.getAddr() + " : " + m.getMessage());
					System.out.println("From Server " + m.getAddr() + " : " + m.getMessage());
				//}
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
