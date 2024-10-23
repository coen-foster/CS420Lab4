import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

public class Process extends UnicastRemoteObject implements Process_Interface {
    private static final long serialVersionUID = 1L;
	private VectorClock_Interface clock;
    private Set<Integer> quorum;
    private MaekawaManager_Interface maekawaManager;
    private int processId;
    private int votesReceived;

    public Process(int processId, Set<Integer> quorum, MaekawaManager_Interface maekawaManager) throws RemoteException {
        this.processId = processId;
        this.quorum = quorum;
        this.clock = new VectorClock(processId);
        this.maekawaManager = maekawaManager;
        this.votesReceived = 0;
    }

    @Override
    public void requestCriticalSection() throws RemoteException {
        clock.increment();
        System.out.println("Process " + processId + " requests critical section with clock: " + clock);
        
        for (Integer quorumMember : quorum) {
            maekawaManager.sendRequest(processId, quorumMember, clock.getClock());
        }
    }

    @Override
    public void releaseCriticalSection() throws RemoteException {
        System.out.println("Process " + processId + " releasing critical section");

        for (Integer quorumMember : quorum) {
            maekawaManager.sendRelease(processId, quorumMember);
        }
    }

    @Override
    public void receiveRequest(int senderId, int[] senderClock) throws RemoteException {
        clock.update(senderClock);
        System.out.println("Process " + processId + " received REQUEST from Process " + senderId + " with clock: " + clock);
        
        maekawaManager.sendGrant(senderId, processId);
    }

    @Override
    public void receiveRelease(int senderId) throws RemoteException {
        System.out.println("Process " + processId + " received RELEASE from Process " + senderId);
    }

    @Override
    public void receiveGrant() throws RemoteException {
        votesReceived++;
        if (votesReceived == quorum.size()) {
            System.out.println("Process " + processId + " enters critical section.");
        }
    }
}
