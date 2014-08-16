import java.io.IOException;
import java.io.ObjectInputStream;

public class Messaging implements Runnable {	
	
	private ObjectInputStream messageIn = null;
	Thread t = null;
	public ClientGUI cl = null;

	Messaging(ClientGUI cl, ObjectInputStream in) {
		
		this.cl = cl;
		this.messageIn = in;
		t = new Thread(this);
		t.start();
		
	}
	
	@Override
	public void run() {

		if(messageIn == null)
		{
			System.out.println("NULL");
			//createStream();
		}
		while (true) {
			try {
					//String message = messageIn.readUTF();
					MessagePacket m = (MessagePacket) messageIn.readObject();
					//messageIn.reset();
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
