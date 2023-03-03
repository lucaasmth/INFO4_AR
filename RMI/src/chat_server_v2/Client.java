package chat_server_v2;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws RemoteException, NotBoundException {
		Scanner scanner = new Scanner(System.in);
		Registry registry = LocateRegistry.getRegistry(args[1]);
		IChatRoomManager chatRoomManager = (IChatRoomManager) registry.lookup("chatRoomManager");
		IParticipant me = new Participant(args[0]);
		IChatRoom chatRoom = chatRoomManager.connect(args[2]);
		chatRoom.connect(me);
		while (true) {
			String message = scanner.nextLine();
			if (message.equals("exit")) {
				chatRoom.leave(me);
				scanner.close();
				System.exit(0);
			}
			chatRoom.send(me, message);
		}
	}
}
