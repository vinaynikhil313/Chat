import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private Socket socket = null;
	private String serverAddress = "172.30.102.178";
	private int serverPort = 8080;
	ClientGUI ui = null;
	String toAddr = null;
	Client(ClientGUI ui, String toAddr, Socket socket) {
		this.ui= ui;
		this.toAddr = toAddr;
		
		//new Messaging(0, socket, ui);
		this.socket = socket;
		new Messaging(socket, ui);
	}
	
	
	public static void main(String[] args) {

		//new Client(null);
	}

}
