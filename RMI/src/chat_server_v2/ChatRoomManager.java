package chat_server_v2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatRoomManager extends UnicastRemoteObject implements IChatRoomManager {

	ArrayList<IChatRoom> chatrooms;

	public ChatRoomManager() throws RemoteException {
		this.chatrooms = new ArrayList<IChatRoom>();
	}

	public IChatRoom connect(String chatroom) throws RemoteException {
		Iterator<IChatRoom> iterator = this.chatrooms.iterator();
		while (iterator.hasNext()) {
			IChatRoom temp = iterator.next();
			if (temp.name().equals(chatroom)) {
				return temp;
			}
		}
		IChatRoom new_chatroom = new ChatRoom(chatroom);
		this.chatrooms.add(new_chatroom);
		return new_chatroom;
	}
}
