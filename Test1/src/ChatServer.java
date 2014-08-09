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

	public DataInputStream openIStream() throws IOException {
		DataInputStream streamIn;
		streamIn = new DataInputStream(new BufferedInputStream(
				socket.getInputStream()));
		return streamIn;
	}

	public DataOutputStream openOStream() throws IOException {
		DataOutputStream streamOut;
		streamOut = new DataOutputStream(new BufferedOutputStream(
				socket.getOutputStream()));
		return streamOut;
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
	}

	@Override
	public void run() {
		DataInputStream streamIn = null;
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
				String line = streamIn.readUTF();
				System.out.println("To addr = " + line);
				Socket sock = connected.get(line);
				DataOutputStream OP = new DataOutputStream(
						sock.getOutputStream());
				line = streamIn.readUTF();
				done = line.equals(".bye");
				System.out.println(line);
				OP.writeUTF(temp.getInetAddress().toString().substring(1) + " : " + line);
			} catch (IOException ioe) {
				System.out.println("Error");
				done = true;
			}
		}

	}

	public static void main(String args[]) {

		new ChatServer();
		
	}
}