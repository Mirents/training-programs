import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class FindAndReplaceDoubleImage {
  JFrame frame;
  List<String> listFile = new ArrayList<String>();

  public static void main(String [] args) {
    new FindAndReplaceDoubleImage().go();
  }

  public void go() {
		frame = new JFrame("Find and Replace double Image");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(new Rectangle(100, 100, 400, 300));
    JPanel background = new JPanel(new BorderLayout());
    background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JButton buttonOpenCatalog = new JButton("Open Catalog");
    buttonOpenCatalog.addActionListener(new MyOpenCatalogListener());

    background.add(BorderLayout.NORTH, buttonOpenCatalog);
    frame.getContentPane().add(background);
		frame.setVisible(true);
	}

  public class MyOpenCatalogListener implements ActionListener {
    public void actionPerformed(ActionEvent a) {
      try {
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileOpen.setAcceptAllFileFilterUsed(false);
        fileOpen.setCurrentDirectory(new File("."));
        int ret = fileOpen.showOpenDialog(null);
        if(ret == JFileChooser.APPROVE_OPTION) {
          getListFile(fileOpen.getSelectedFile());
        }
      } catch(Exception e) { e.printStackTrace(); }
    }
  }

  public void getListFile(File dir) {
    // Список объектов File
    /*File[] arrFiles = dir.listFiles();
    for(File f : arrFiles)
      System.out.println("- " + f.getName());*/

    // Список названий файлов
    /*String [] s = dir.list();
    for(String d : dir.list())
      System.out.println(d.toString());*/

    listFile = Arrays.asList(dir.list());
    for(String g : listFile)
      System.out.println("/// " + g.toString());

    workToLostFile();
  }

  public void workToLostFile() {
    for(int i = 0; i < listFile.size(); i++)
      for(int j = 0; j < listFile.size(); j++) {
        if(i != j) {
          String s1 = listFile.get(i);
          String s2 = listFile.get(j);
          System.out.println(s1 + "  /  " + s2);
          if(s1.contains(s2) || s2.contains(s1))
            System.out.println(listFile.get(i) + "  ///  " + listFile.get(j));
          }
      }
  }
}
