import java.io.Serializable;


public class MessagePacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String addr, message;

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
