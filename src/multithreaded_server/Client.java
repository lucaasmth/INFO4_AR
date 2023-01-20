package multithreaded_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
				System.out.print("Entrez le fichier souhaité: ");
				name = scanner.nextLine();
			}

			OutputStream os = soc.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(name);

			InputStream is = soc.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			int fileSize = dis.readInt();
			System.out.println(fileSize);

			if (fileSize == -1) {
				System.out.println("Error");
				return;
			}

			byte[] file = dis.readNBytes(fileSize);
			File myFile = new File("downloads/" + name);
			myFile.createNewFile();
			FileOutputStream thomas = new FileOutputStream(myFile);
			thomas.write(file);

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
