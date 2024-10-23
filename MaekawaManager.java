import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class MaekawaManager extends UnicastRemoteObject implements MaekawaManager_Interface {
    private static final long serialVersionUID = 1L;
	private Map<Integer, Process_Interface> processes;

    public MaekawaManager() throws RemoteException {
        this.processes = new HashMap<>();
    }

    @Override
    public void sendRequest(int senderId, int receiverId, int[] clock) throws RemoteException {
        Process_Interface receiver = processes.get(receiverId);
        if (receiver != null) {
            System.out.println("Sending REQUEST from Process " + senderId + " to Process " + receiverId);
            receiver.receiveRequest(senderId, clock);
        }
    }

    @Override
    public void sendRelease(int senderId, int receiverId) throws RemoteException {
        Process_Interface receiver = processes.get(receiverId);
        if (receiver != null) {
            System.out.println("Sending RELEASE from Process " + senderId + " to Process " + receiverId);
            receiver.receiveRelease(senderId);
        }
    }

    @Override
    public void sendGrant(int senderId, int receiverId) throws RemoteException {
        Process_Interface receiver = processes.get(receiverId);
        if (receiver != null) {
            System.out.println("Sending GRANT from Process " + senderId + " to Process " + receiverId);
            receiver.receiveGrant();
        }
    }

    @Override
    public void registerProcess(int processId, Process_Interface process) throws RemoteException {
        processes.put(processId, process);
    }
}
