import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private Socket socket = null;
	private String serverAddress = "172.30.102.178";
	private int serverPort = 8080;
	ClientGUI ui = null;
	String toAddr = null;
	Client(ClientGUI ui, String toAddr) {
		this.ui= ui;
		this.toAddr = toAddr;
		while (!connect())
			;
		//new Messaging(0, socket, ui);
		new Messaging(socket, ui);
	}

	void send(String input){
		
		System.out.println("EFGH");
		//String input = ui.newMessage.getText();
		System.out.println("Input = " + input);
		try {
			DataOutputStream messageOut = new DataOutputStream(socket.getOutputStream());
			messageOut.writeUTF(toAddr);
			messageOut.flush();
			messageOut.writeUTF(input);
			messageOut.flush();
		} catch (IOException e) {
			System.out.println("Could not write to stream");
			e.printStackTrace(System.out);
			return;
		}
		//new Messaging(0, socket, ui);
		//new Messaging(1, socket, ui);
		
	}
	
	private boolean connect() {

		System.out.println("Establishing connection. Please wait ...");

		try {

			socket = new Socket(serverAddress, serverPort);
			System.out.println("Connected: " + socket);

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

		//new Client(null);
	}

}
