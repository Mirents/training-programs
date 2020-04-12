import java.rmi.*;

public interface ServiceServer extends Remote {
  Object[] getServiceList() trows RemoteException;
  Service getService(Object serviceKey) trows RemoteException;
}
