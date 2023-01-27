package poolbased_multithreaded;

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
		ProdConsBuffer clientBuffer = new ProdConsBuffer(5);
		FileSenderThread[] threadBuffer = new FileSenderThread[5];
		for (int i = 0; i < 5; i++) {
			threadBuffer[i] = new FileSenderThread(clientBuffer);
		}

		try {
			listenSoc = new ServerSocket(serverPort, backlog);
			System.out.println("serveur demarre sur le port " + serverPort);
			while (true) {
				Socket soc = listenSoc.accept();
				clientBuffer.put(soc);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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

	protected ProdConsBuffer serverBuffer;

	public FileSenderThread(ProdConsBuffer serverBuffer) {
		this.serverBuffer = serverBuffer;

		start();
	}

	public void run() {
		try {
			Socket soc = serverBuffer.get();
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
