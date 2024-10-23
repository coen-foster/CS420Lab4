import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Process_Interface extends Remote {
    void requestCriticalSection() throws RemoteException;
    void releaseCriticalSection() throws RemoteException;
    void receiveRequest(int senderId, int[] clock) throws RemoteException;
    void receiveRelease(int senderId) throws RemoteException;
	void receiveGrant() throws RemoteException;
}
