package basic_file_server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Test {
	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream("test.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
