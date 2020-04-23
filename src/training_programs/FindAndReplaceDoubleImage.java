import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class FindAndReplaceDoubleImage {
  JFrame frame;
  List<File> listFile = new ArrayList<File>();
  String doubleFile = "/doubleFile";

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
      System.out.println("- " + f.getAbsolutePath());*/

    // Список названий файлов
    /*String [] s = dir.list();
    for(String d : dir.list())
      System.out.println(d.toString());*/

    listFile = Arrays.asList(dir.listFiles());

    System.out.println(dir.getAbsolutePath());
    File dirToDelete = new File(dir.getAbsolutePath() + doubleFile);

    if(dirToDelete.mkdir()) // TODO добавить проверку на присутствие каталога
      workToLostFile(dir.getAbsolutePath() + doubleFile);
    else
      System.out.println("Don`t create source directori!");
  }

  public void workToLostFile(String source) {
    int i = 0;

    while(true) {
      for(int j = 0; j < listFile.size(); j++) {
        if(i != j && !listFile.get(i).getName().contains("_")) { // TODO добвить сравнение атрибутов и размеров файлов
          String s1 = listFile.get(i).getName().substring(0, listFile.get(i).getName().length()-4);
          String s2 = listFile.get(j).getName().substring(0, listFile.get(j).getName().length()-4);

          if(s1.contains(s2) || s2.contains(s1))
            if(openAndDeleteFile(listFile.get(i), listFile.get(j), source))
              i = 0;
          }
      }
      if(i++ == listFile.size()-1)
        break;
      }
  }

  public boolean openAndDeleteFile(File s1, File s2, String source) {
    BufferedImage img1 = null;
    BufferedImage img2 = null;
    File f1 = null;
    File f2 = null;

    try {
      img1 = ImageIO.read(s1);
      img2 = ImageIO.read(s2);

    int wImg1 = img1.getWidth();
    int hImg1 = img1.getHeight();

    int wImg2 = img2.getWidth();
    int hImg2 = img2.getHeight();
    int p1 = img1.getRGB(100, 100);
    int p2 = img2.getRGB(100, 100);
    if(p1 == p2) {
        //System.out.println(source + "/" + s2.getName());
        if(s2.renameTo((new File(source + "/" + s2.getName())))) {
          System.out.println(source + s2.getName());
          return true;
        }
        else {
          System.out.println(s2.getPath() + source + s2.getName());
          return false;
        }
    } else {
      System.out.println("33");
      return false;
    }
    } catch(IOException e) { e.printStackTrace(); }
    finally { return false; }
  }
}
