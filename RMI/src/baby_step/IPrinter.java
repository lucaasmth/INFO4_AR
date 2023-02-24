package baby_step;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrinter extends Remote {
	public void print(String s) throws RemoteException;
}
