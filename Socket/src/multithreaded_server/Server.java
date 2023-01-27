package multithreaded_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
				FileSenderThread thread = new FileSenderThread(soc);
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

class FileSenderThread extends Thread {
	protected Socket soc;

	public FileSenderThread(Socket soc) {
		this.soc = soc;
		start();
	}

	public void run() {
		try {
			InputStream is = soc.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			String fileName = dis.readUTF();
			OutputStream os = soc.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(fileName);
				byte[] buffer = new byte[(int) fis.getChannel().size()];
				dos.writeInt((int) fis.getChannel().size());
				buffer = fis.readAllBytes();
				dos.write(buffer);
				// FileOutputStream fos = new FileOutputStream(is);
			} catch (FileNotFoundException e) {
				dos.writeInt(-1);
				// dos.writeUTF("File not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
