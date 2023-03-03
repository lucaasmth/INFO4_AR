package chat_server_v2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatRoomManager extends Remote {

	IChatRoom connect(String chatroom) throws RemoteException;

}
