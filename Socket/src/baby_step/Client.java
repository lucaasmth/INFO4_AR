package baby_step;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		String host = "localhost";
		int port = 9999;
		Socket soc = null;

		try {
			soc = new Socket(host, port);

			String name = args[0];
			if (name == null) {
				Scanner scanner = new Scanner(System.in);
				System.out.print("Entrez votre nom: ");
				name = scanner.nextLine();
			}

			OutputStream os = soc.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(name);

			InputStream is = soc.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			String message = dis.readUTF();

			System.out.println(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				soc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
