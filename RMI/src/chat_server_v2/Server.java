package chat_server_v2;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		IChatRoomManager chatRoomManager = new ChatRoomManager();

		Registry registry = LocateRegistry.createRegistry(1099);
		registry.bind("chatRoomManager", chatRoomManager);
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
