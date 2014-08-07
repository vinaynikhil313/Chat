import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {

	private Socket socket = null;
	private DataInputStream  messageIn = null;
	private DataOutputStream messageOut = null;
	private String serverAddress = "127.0.0.1";
	private int serverPort = 8081;
	private String toAddr = "172.30.102.112";

	Client()
	{
		while(!connect());
		while(true)
		{
			Scanner in = new Scanner(System.in);
			String data = in.nextLine();
			try {
				messageOut.writeUTF(toAddr);
				messageOut.flush();
				messageOut.writeUTF(data);
				messageOut.flush();
				String out = messageIn.readUTF();
				System.out.println("Message = " + out);
			} catch (IOException e) {
				System.out.println("Failed to send message");
				e.printStackTrace();
			}
		}
	}

	private boolean connect()
	{
		System.out.println("Establishing connection. Please wait ...");
		try
		{  
			socket = new Socket(serverAddress, serverPort);
			System.out.println("Connected: " + socket);
			try{
				messageOut = new DataOutputStream(socket.getOutputStream());
				messageIn = new DataInputStream(socket.getInputStream());
			}catch(IOException e){
				System.out.println("Could not load data stream....Retry");
				return false;
			}
		}
		catch(UnknownHostException uhe)
		{
			System.out.println("Host unknown: " + uhe.getMessage());
			return false;
		}
		catch(IOException ioe)
		{
			System.out.println("Unexpected exception: " + ioe.getMessage());
			return false;
		}
		return true;
	}

	public static void main(String[] args) {

		new Client();
	}

}
