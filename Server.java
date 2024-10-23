import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); // Start the RMI registry on port 1099
            MaekawaManager_Interface manager = new MaekawaManager();
            Naming.rebind("rmi://localhost/MaekawaManager", manager);
            System.out.println("MaekawaManager bound in registry");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

