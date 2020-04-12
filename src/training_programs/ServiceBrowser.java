import java.arw.*;
import javax.swing.*;
import java.rmi.*;
import java.awt.event.*;

public class ServiceBrowser {
  JPanel panel;
  JComboBox serviceList;
  ServiceServer server;

  public void buildGUI() {
    JFrame frame = new JFrame("RMI browser");
    panel = new JPanel();
    frame.getContentPane().add(BorderLayout.CENTER, panel);

    Object[] services = getServiceList();
    serviceList = new JComboBox(services);

    frame.getContentPane().add(BorderLayout.NORTH, serviceList);

    serviceList.addActionListener(new MyListListener);

    frame.setSize(500, 500);
    frame.setVisible(true);
  }

  public loadService(Object serviceSelection) {
    try {
      Service svr = server.getService(serviceSelection);

      panel.removeAll();
      panel.add(svc.getGuiPanel());
      panel.validate();
      panel.repaint();
    } catch(Exception e) { e.printStackTrace(); }
  }

  public Object[] getServiceList() {
    Object obj = null;
    Object[] services = null;

    try {
      obj = Naming.Lookup("rmi://127.0.0.1/ServiceServer");
    } catch(Exception e) { e.printStackTrace(); }

    server = (ServiceServer) obj;

    try {
      services = server.getServiceList();
    } catch(Exception e) { e.printStackTrace(); }

    return services;
  }

  public class MyListListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      Object selection = serviceList.getSelectionItem();
      loadService(selection);
    }
  }

  public static void main(String[] args) {
    new ServiceBrowser().buildGUI();
  }
}
