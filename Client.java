import java.rmi.Naming;
import java.util.HashSet;
import java.util.Set;

public class Client {
    public static void main(String[] args) {
        try {
        	MaekawaManager_Interface manager = (MaekawaManager_Interface) Naming.lookup("//localhost/MaekawaManager");
            Set<Integer> quorum = new HashSet<Integer>();
            quorum.add(0);
            quorum.add(1);
            quorum.add(2);
            Process_Interface process1 = new Process(0, quorum, manager);
            Process_Interface process2 = new Process(1, quorum, manager);
            Process_Interface process3 = new Process(2, quorum, manager);
            
            manager.registerProcess(0, process1);
            manager.registerProcess(1, process2);
            manager.registerProcess(2, process3);
            
            process1.requestCriticalSection();
            process2.requestCriticalSection();
            Thread.sleep(2000);
            process1.releaseCriticalSection();
            Thread.sleep(2000);
            process2.releaseCriticalSection();
            process3.requestCriticalSection();
            Thread.sleep(2000);
            process3.releaseCriticalSection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}