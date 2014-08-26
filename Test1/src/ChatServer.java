import java.net.*;
import java.util.HashMap;
import java.io.*;

public class ChatServer implements Runnable {

	private int port = 1237;
	private Socket socket = null;
	private ServerSocket server = null;
	private HashMap<String, ObjectOutputStream> connected = null;
	private DatabaseClass db = null;

	public ChatServer() {

		db = new DatabaseClass();
		while (!db.connect())
			;
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

				// System.out.println("Client accepted: "
				// + socket.getInetAddress().toString().substring(1));

				String temp = db.getNick(socket.getInetAddress().toString()
						.substring(1));
				System.out.println("Client accepted: "
						+ socket.getInetAddress().toString().substring(1)
						+ " nick " + temp);
				if (temp != null)
					connected.put(temp.toLowerCase(), new ObjectOutputStream(
							socket.getOutputStream()));
				else
					connected.put(
							socket.getInetAddress().toString().substring(1),
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
			// try {
			MessagePacket m = null;
			// System.out.println(temp.isConnected() + " one " +
			// temp.isClosed());
			try {
				m = (MessagePacket) streamIn.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				done = true;
				// System.out.println(temp.isConnected() + " two " +
				// temp.isClosed());
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				done = true;
				e.printStackTrace();
			}

			if (m != null && m.getType() == 0) {
				System.out.println("To addr = " + m.getToAddr());
				ObjectOutputStream OP = connected.get(m.getToAddr()
						.toLowerCase());
				// done = m.getMessage().equals(".bye");
				if (m.getFileBytes() == null)
					System.out.println("Message : " + m.getMessage());
				else {
					System.out.println("Message file name : " + m.getMessage());
					// System.out.println("Message : " + m.getMessage());
				}
				try {
					if (OP != null)
						OP.writeObject(m);
				} catch (IOException e) {
					e.printStackTrace(System.out);
				}
			} else if (m != null && m.getType() == 1) {

				boolean isPresent = db.checkUser(1, m.getToAddr());
				if (isPresent) {
					if (connected.get(m.getToAddr().toLowerCase()) == null) {
						m.setMessage("offline");
					} else {
						m.setMessage("online");
					}
				} else {
					m.setMessage("does not exist");
				}

				ObjectOutputStream OP = connected.get(m.getFromAddr()
						.toLowerCase());
				try {
					OP.writeObject(m);
					System.out.println("sent");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (m != null && m.getType() == 2) {
				if (m.getMessage().equals("check")) {
					// boolean isPresent = db.checkUser(0, temp.getInetAddress()
					// .toString().substring(1));
					String tempNick = db.getNick(temp.getInetAddress()
							.toString().substring(1));
					System.out.println(tempNick);
					ObjectOutputStream OP = null;
					if (tempNick != null) {
						m.setMessage(tempNick);
						OP = connected.get(tempNick.toLowerCase());
					} else {
						m.setMessage("not registered");
						OP = connected.get(temp.getInetAddress().toString()
								.substring(1));
					}

					try {
						OP.writeObject(m);
						// System.out.println("registration success");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					if (!db.checkUser(1, m.getMessage())) {
						db.addUser(m.getMessage(), temp.getInetAddress()
								.toString().substring(1));

						// m.setMessage("new user registered");
						System.out.println("registration success");

						ObjectOutputStream OP = connected.get(temp
								.getInetAddress().toString().substring(1));
						try {
							OP.writeObject(m);
							// System.out.println("registration success");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						connected.remove(temp.getInetAddress().toString()
								.substring(1));
						connected.put(m.getMessage().toLowerCase(), OP);

					} else {
						m.setMessage("nick already exists");
						ObjectOutputStream OP = connected.get(temp
								.getInetAddress().toString().substring(1));
						try {
							OP.writeObject(m);
							// System.out.println("registration success");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			} else if (m != null && m.getType() == 3) {
				connected.remove(m.getFromAddr().toLowerCase());
				System.out.println("client disconnected");
				for (String key : connected.keySet()) {
					ObjectOutputStream OP = connected.get(key);
					MessagePacket m2 = new MessagePacket();
					m2.setToAddr(m.getFromAddr());
					m2.setType(3);
					m2.setMessage("offline");
					try {
						OP.writeObject(m2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				done = true;
			}

		}
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