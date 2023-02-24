package chat_server_v1;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws RemoteException, NotBoundException {
		Scanner scanner = new Scanner(System.in);
		Registry registry = LocateRegistry.getRegistry("localhost");
		IChatRoom chatRoom = (IChatRoom) registry.lookup("chatroom");
		IParticipant me = new Participant(args[0]);
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
