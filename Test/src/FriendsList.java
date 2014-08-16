import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class FriendsList {

	File f = null;

	
	int count = 0;

	FriendsList() {
		f = new File("FriendsList.txt");
		if (!f.exists()) {
			try {
				FileOutputStream fout = new FileOutputStream(f);
				f.createNewFile();
				fout.write(count);
				fout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	int getCount() {
		Scanner fin = null;
		try {
			fin = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.count = fin.nextInt();
		fin.close();
		return this.count;
	}

	String[] getFriendsList() {
		Scanner fin = null;
		String[] list = null;
		try {
			fin = new Scanner(f);
			fin.nextLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count > 0) {
			list = new String[count];
			for(int i=0;i<count;i++)
				list[i] = fin.nextLine();
			fin.close();
		}
		
		return list;
	}

	boolean addFriend(String addr){
		try {
			FileOutputStream fout = new FileOutputStream(f, true);
		    //PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
		    fout.write(addr.getBytes());
		    fout.close();
		} catch (IOException e) {
		    //exception handling left as an exercise for the reader
			return false;
		}
		
		return true;
	}
	
}
