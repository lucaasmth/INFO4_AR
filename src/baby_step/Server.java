package baby_step;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {

		int serverPort = 9999;
		int backlog = 3;
		ServerSocket listenSoc = null;

		try {
			listenSoc = new ServerSocket(serverPort, backlog);
			System.out.println("serveur demarre sur le port " + serverPort);
			while (true) {
				Socket soc = listenSoc.accept();
				InputStream is = soc.getInputStream();
				DataInputStream dis = new DataInputStream(is);
				String name = dis.readUTF();
				OutputStream os = soc.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);
				dos.writeUTF("Hello " + name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				listenSoc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
