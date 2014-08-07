

import java.net.*;
import java.util.HashMap;
import java.io.*;

public class ChatServer implements Runnable {
	private Socket socket = null;
	private ServerSocket server = null;
	private HashMap<String, Socket> connected = null;
	
	public ChatServer(int port) {
		connected = new HashMap<String, Socket>();
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			try {
				System.out.println("Binding to port " + port
						+ ", please wait  ...");
				System.out.println("Server started: " + server);
				System.out.println("Waiting for a client ...");
				socket = server.accept();
				System.out.println("Client accepted: " + socket.getInetAddress().toString().substring(1));
				connected.put(socket.getInetAddress().toString().substring(1), socket);
				Thread t = new Thread(this);
				t.start();

			} catch (IOException ioe) {
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
		// if (streamIn != null) streamIn.close();
	}

	public static void main(String args[]) {
		ChatServer server = null;
		// if (args.length != 1)
		// System.out.println("Usage: java ChatServer port");
		// else
		server = new ChatServer(8081);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DataInputStream streamIn = null;
		//DataOutputStream streamOut = null;
		try {
			streamIn = openIStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean done = false;
		while (!done) {
			try {
				String line = streamIn.readUTF();
				System.out.println(line);
				Socket sock = connected.get(line);
				DataOutputStream OP = new DataOutputStream(sock.getOutputStream());
				line = streamIn.readUTF();
				System.out.println(line);
				OP.writeUTF("Server : " + line);
				done = line.equals(".bye");
			} catch (IOException ioe) {
				System.out.println("Error");
				done = true;
			}
		}

	}
}