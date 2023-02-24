package chat_server_v1;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		IChatRoom chatRoom = new ChatRoom("ChatRoom1");
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.bind("chatroom", chatRoom);
		Object obj = new Object();
		synchronized (obj) {
			try {
				obj.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
