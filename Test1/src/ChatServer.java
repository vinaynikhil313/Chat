import java.net.*;
import java.util.HashMap;
import java.io.*;

public class ChatServer implements Runnable {

	private int port = 8080;
	private Socket socket = null;
	private ServerSocket server = null;
	private HashMap<String, ObjectOutputStream> connected = null;

	public ChatServer() {

		connected = new HashMap<String, ObjectOutputStream>();

		try {

			server = new ServerSocket(port);

		} catch (IOException e) {
			System.out.println("Could not create ServerSocket");
			e.printStackTrace(System.out);
		}
		
		System.out.println("Binding to port " + port + ", please wait  ...");
		System.out.println("Server started: " + server);
		
		while (true) {
			try {
				
				System.out.println("Waiting for a client ...");
				
				socket = server.accept();
				
				System.out.println("Client accepted: "
						+ socket.getInetAddress().toString().substring(1));
				
				connected.put(socket.getInetAddress().toString().substring(1),
						new ObjectOutputStream(socket.getOutputStream()));
				
				Thread t = new Thread(this);
				t.start();

			} catch (IOException ioe) {
				System.out.println("Could not accept a client");
				System.out.println(ioe);
			}
		}

	}

	public ObjectInputStream openIStream() throws IOException {
		ObjectInputStream streamIn;
		streamIn = new ObjectInputStream(socket.getInputStream());
		return streamIn;
	}

	@Override
	public void run() {
		ObjectInputStream streamIn = null;
		Socket temp = socket;
		try {
			streamIn = openIStream();
		} catch (IOException e) {
			System.out.println("Could not create Input Stream");
			e.printStackTrace();
		}

		boolean done = false;
		while (!done) {
			try {
				MessagePacket m = (MessagePacket) streamIn.readObject();
				System.out.println("To addr = " + m.getToAddr());
				ObjectOutputStream OP = connected.get(m.getToAddr());
				done = m.getMessage().equals(".bye");
				System.out.println("Message : " + m.getMessage());
				m.setFromAddr(temp.getInetAddress().toString().substring(1));
				OP.writeObject(m);
			} catch (IOException ioe) {
				System.out.println("Error - Client Disconnected");
				done = true;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		//System.out.println(connected.get(temp.getInetAddress().toString().substring(1)));
		connected.remove(temp.getInetAddress().toString().substring(1));
		
		try {
			temp.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {

		new ChatServer();
		
	}
}