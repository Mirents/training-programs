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
import java.util.*;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;

public class FindAndReplaceDoubleImage {
  JFrame frame;
  List<File> listFile;
  String doubleFile = "/doubleFile";
  JTextField sourceDirName;
  JList<String> doubleFileName;
  Vector<String> listDoubleFileName = new Vector<String>();
  JLabel labelSource;

  public static void main(String [] args) {
    new FindAndReplaceDoubleImage().go();
  }

  public void go() {
		frame = new JFrame("Find and Replace double Image");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(new Rectangle(100, 100, 400, 300));
    JPanel background = new JPanel(new GridLayout(3, 2));
    //background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    Box Box = new Box(BoxLayout.Y_AXIS);
    Box.setBorder(new EmptyBorder(5, 5, 5, 5));
    Box Box1 = new Box(BoxLayout.X_AXIS);

    JButton buttonOpenCatalog = new JButton("Open Catalog");
    buttonOpenCatalog.addActionListener(new MyOpenCatalogListener());
    Box.add(buttonOpenCatalog);
    Box.add(Box.createVerticalStrut(5));

    labelSource = new JLabel("Source dir: ");
    sourceDirName = new JTextField();
    Box1.add(labelSource);
    Box1.add(sourceDirName);
    Box.add(Box1);
    Box.add(Box.createVerticalStrut(5));

    doubleFileName = new JList<String>();
    doubleFileName.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane theList = new JScrollPane(doubleFileName);
    doubleFileName.setListData(listDoubleFileName);
    Box.add(theList);

    frame.getContentPane().add(Box);
    frame.pack();
		frame.setVisible(true);
	}

  public class MyOpenCatalogListener implements ActionListener {
    public void actionPerformed(ActionEvent a) {
      try {
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileOpen.setAcceptAllFileFilterUsed(false);
        fileOpen.setCurrentDirectory(new File(".")); // TODO Решить вопрос с точкой для задания текущего каталога
        int ret = fileOpen.showOpenDialog(null);
        if(ret == JFileChooser.APPROVE_OPTION) {
          String source = sourceDirName.getText();
          if(!source.equals(""))
            getListFile(fileOpen.getSelectedFile(), ("/" + source));
          else
            JOptionPane.showMessageDialog(null, "Error! Folder name is empty!");
        }
      } catch(Exception e) { e.printStackTrace(); }
    }
  }

  public void getListFile(File dir, String source) {

    listFile = new ArrayList<>(Arrays.asList(dir.listFiles()));
    listFile.removeIf(f -> (f.isDirectory()));

    File dirToDelete = new File(dir.getAbsolutePath() + source);

    if(dirToDelete.exists())
      JOptionPane.showMessageDialog(null, "Error, folder is name \"" + source + "\" is exists, set new name folder");
    else if(dirToDelete.mkdir())
      workToListFile(dir.getAbsolutePath() + source);
    else
      JOptionPane.showMessageDialog(null, "Error create folder name " + source);
  }

  public void workToListFile(String source) {
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
    int [][] rgbOne = new int[100][100];
    int [][] rgbTwo = new int[100][100];
    int k1, k2;
    boolean isRemove = false;

    try {
      img1 = ImageIO.read(s1);
      img2 = ImageIO.read(s2);

      int wImg1 = img1.getWidth();
      int hImg1 = img1.getHeight();

      int wImg2 = img2.getWidth();
      int hImg2 = img2.getHeight();

      if(wImg1 != wImg2 || hImg1 != hImg2)
        return false;

      k1 = (int) (wImg1-1)/100; // TODO устранить уязвимость в размере картинки, если она меньше 100 на 100
      k2 = (int) (hImg1-1)/100;

      for(int i = 0; i < 100; i++)
        for(int j = 0; j < 100; j++) {
          int p1 = img1.getRGB(i*k1, j*k2);
          int p2 = img2.getRGB(i*k1, j*k2);
          if(p1 != p2)
            isRemove = true;
        }

      if(!isRemove) {
        if(removeFile(s2, source))
          return true;
        else
          return false;
        } else
          return false;
    } catch(IOException e) { e.printStackTrace(); }
    finally { return false; }
  }

  public boolean removeFile(File f, String source) {
    try {
      if(f.renameTo((new File(source + "/" + f.getName())))) {
        listDoubleFileName.add(f.getName());
        doubleFileName.setListData(listDoubleFileName);
        return true;
      }
      else
        return false;
    } catch(Exception e) { e.printStackTrace(); }
    finally { return false; }
  }
}
