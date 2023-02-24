package chat_server_v1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Participant extends UnicastRemoteObject implements IParticipant {

	private String name;

	protected Participant(String name) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public void receive(String name, String msg) {
		// TODO Auto-generated method stub
		System.out.println(name + " : " + msg);

	}

}
