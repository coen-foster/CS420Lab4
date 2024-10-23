import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MaekawaManager_Interface extends Remote {
    void sendRequest(int senderId, int receiverId, int[] clock) throws RemoteException;
    void sendRelease(int senderId, int receiverId) throws RemoteException;
    void sendGrant(int senderId, int receiverId) throws RemoteException;
	void registerProcess(int processId, Process_Interface process) throws RemoteException;
}
