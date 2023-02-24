package chat_server_v1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatRoom extends UnicastRemoteObject implements IChatRoom {

	private String name;
	private ArrayList<IParticipant> participants;

	protected ChatRoom(String name) throws RemoteException {
		super();
		this.name = name;
		this.participants = new ArrayList<IParticipant>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String name() throws RemoteException {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public void connect(IParticipant p) throws RemoteException {
		// TODO Auto-generated method stub
		this.participants.add(p);
		send(p, "just entered in the chatroom " + this.name());

	}

	@Override
	public void leave(IParticipant p) throws RemoteException {
		// TODO Auto-generated method stub
		this.participants.remove(p);
		send(p, "just left the chatroom " + this.name());

	}

	@Override
	public String[] who() throws RemoteException {
		// TODO Auto-generated method stub
		String[] list = new String[participants.size()];
		Iterator<IParticipant> iterator = participants.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			IParticipant participant = iterator.next();
			list[index++] = participant.name();
		}
		return list;
	}

	@Override
	public void send(IParticipant p, String msg) throws RemoteException {
		// TODO Auto-generated method stub
		Iterator<IParticipant> iterator = participants.iterator();
		String p_name = p.name();
		while (iterator.hasNext()) {
			IParticipant participant = iterator.next();
			participant.receive(p_name, msg);
		}

	}

}
