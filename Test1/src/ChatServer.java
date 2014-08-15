<<<<<<< HEAD
import java.net.*;
import java.util.HashMap;
import java.io.*;

public class ChatServer implements Runnable {

	private int port = 8080;
	private Socket socket = null;
	private ServerSocket server = null;
	private HashMap<String, Socket> connected = null;

	public ChatServer() {

		connected = new HashMap<String, Socket>();

		try {

			server = new ServerSocket(port);

		} catch (IOException e) {
			System.out.println("Could not create ServerSocket");
			e.printStackTrace(System.out);
		}
		while (true) {
			try {
				System.out.println("Binding to port " + port
						+ ", please wait  ...");
				System.out.println("Server started: " + server);
				System.out.println("Waiting for a client ...");
				
				socket = server.accept();
				
				System.out.println("Client accepted: "
						+ socket.getInetAddress().toString().substring(1));
				connected.put(socket.getInetAddress().toString().substring(1),
						socket);
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

	public ObjectOutputStream openOStream() throws IOException {
		ObjectOutputStream streamOut;
		streamOut = new ObjectOutputStream(socket.getOutputStream());
		return streamOut;
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
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
				streamIn.reset();
				//String line = streamIn.readUTF();
				//System.out.println(socket.getInetAddress() + " and " + socket.isConnected() + " and " + socket.isClosed());
				System.out.println("To addr = " + m.getAddr());
				Socket sock = connected.get(m.getAddr());
				ObjectOutputStream OP = new ObjectOutputStream(sock.getOutputStream());
				System.out.println(sock.getInetAddress() + " and " + sock.isConnected() + " and " + sock.isClosed());
				//line = streamIn.readUTF();
				done = m.getMessage().equals(".bye");
				System.out.println("Message : " + m.getMessage());
				m.setAddr(temp.getInetAddress().toString().substring(1));
				OP.writeObject(m);
				OP.reset();
				//OP.writeUTF(temp.getInetAddress().toString().substring(1) + " : " + line);
			} catch (IOException ioe) {
				System.out.println("Error");
				done = true;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String args[]) {

		new ChatServer();
		
	}
=======
import java.net.*;
import java.util.HashMap;
import java.io.*;

public class ChatServer implements Runnable {

	private int port = 8080;
	private Socket socket = null;
	private ServerSocket server = null;
	private HashMap<String, Socket> connected = null;

	public ChatServer() {

		connected = new HashMap<String, Socket>();

		try {

			server = new ServerSocket(port);

		} catch (IOException e) {
			System.out.println("Could not create ServerSocket");
			e.printStackTrace(System.out);
		}
		while (true) {
			try {
				System.out.println("Binding to port " + port
						+ ", please wait  ...");
				System.out.println("Server started: " + server);
				System.out.println("Waiting for a client ...");
				
				socket = server.accept();
				
				System.out.println("Client accepted: "
						+ socket.getInetAddress().toString().substring(1));
				connected.put(socket.getInetAddress().toString().substring(1),
						socket);
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

	public ObjectOutputStream openOStream() throws IOException {
		ObjectOutputStream streamOut;
		streamOut = new ObjectOutputStream(socket.getOutputStream());
		return streamOut;
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
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
				//String line = streamIn.readUTF();
				//System.out.println(socket.getInetAddress() + " and " + socket.isConnected() + " and " + socket.isClosed());
				System.out.println("To addr = " + m.getAddr());
				Socket sock = connected.get(m.getAddr());
				ObjectOutputStream OP = new ObjectOutputStream(sock.getOutputStream());
				System.out.println(sock.getInetAddress() + " and " + sock.isConnected() + " and " + sock.isClosed());
				//line = streamIn.readUTF();
				done = m.getMessage().equals(".bye");
				System.out.println("Message : " + m.getMessage());
				m.setAddr(temp.getInetAddress().toString().substring(1));
				OP.writeObject(m);
				OP.reset();
				//OP.writeUTF(temp.getInetAddress().toString().substring(1) + " : " + line);
			} catch (IOException ioe) {
				System.out.println("Error");
				done = true;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String args[]) {

		new ChatServer();
		
	}
>>>>>>> origin/master
}