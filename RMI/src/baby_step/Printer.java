package baby_step;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Printer extends UnicastRemoteObject implements IPrinter {

	protected Printer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void print(String s) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(s);
	}

}
