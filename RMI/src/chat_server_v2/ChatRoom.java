package chat_server_v2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatRoom extends UnicastRemoteObject implements IChatRoom {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6622009205544553308L;
	private String name;
	private ArrayList<IParticipant> participants;
	private ArrayList<String[]> messages;

	protected ChatRoom(String name) throws RemoteException {
		super();
		this.name = name;
		this.participants = new ArrayList<IParticipant>();
		this.messages = new ArrayList<String[]>();
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
		Iterator iter = messages.iterator();
		while (iter.hasNext()) {
			String[] m = (String[]) iter.next();
			p.receive(m[0], m[1]);
		}
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
		String p_name = "un participant";
		try {
			p_name = p.name();
		} catch (Exception e) {

		}
		while (iterator.hasNext()) {
			IParticipant participant = iterator.next();
			try {
				participant.receive(p_name, msg);
			} catch (Exception e) {
				leave(participant);
			}
		}
		String[] m = { p_name, msg };
		messages.add(m);
	}

}
