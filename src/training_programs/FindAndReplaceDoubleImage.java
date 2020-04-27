package training_programs;

import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import javax.swing.border.EmptyBorder;

import javax.swing.JOptionPane;

public class FindAndReplaceDoubleImage {
	JFrame frame;
	List<File> listFile;
	JTextField sourceDirNameDouble;
	JTextField sourceDirNameBadFile;
	JCheckBox CheckRemoveBad;
	JList<String> doubleFileName;
	JLabel labelSource;

	Vector<String> listDoubleFileName = new Vector<String>();
	JFileChooser fileOpen = new JFileChooser();

	int removeFiles;
	//boolean removeBadFile = true;
	//String sourceDouble;
	//String sourceBad;
	boolean isWork = false;
	File dirToDouble;
	File dirToBad;

	public static void main(String [] args) {
		new FindAndReplaceDoubleImage().go();
	}
	
	public void go() {
		frame = new JFrame("Find and Replace double Image");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(new Rectangle(100, 100, 400, 300));
		//background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		Box BoxPane = Box.createVerticalBox();
		BoxPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Box Box1 = Box.createVerticalBox();
		Box Box2 = Box.createVerticalBox();
		
		JButton buttonOpenCatalog = new JButton("Open Catalog");
		buttonOpenCatalog.addActionListener(new MyOpenCatalogListener());
		BoxPane.add(buttonOpenCatalog);
		BoxPane.add(javax.swing.Box.createVerticalStrut(5));

		labelSource = new JLabel("Source working directory: ");
		sourceDirNameDouble = new JTextField("double_image");
		Box1.add(labelSource);
		Box1.add(sourceDirNameDouble);
		BoxPane.add(Box1);
		BoxPane.add(javax.swing.Box.createVerticalStrut(5));

		sourceDirNameBadFile = new JTextField("bad_image");
		CheckRemoveBad = new JCheckBox("Move corrupted files to:");
		CheckRemoveBad.setSelected(true);
		CheckRemoveBad.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(!isWork)
					sourceDirNameBadFile.setEnabled(CheckRemoveBad.isSelected());
			}
		});
		Box2.add(CheckRemoveBad);
		Box2.add(sourceDirNameBadFile);
		BoxPane.add(Box2);
		BoxPane.add(javax.swing.Box.createVerticalStrut(5));
		
		doubleFileName = new JList<String>();
		doubleFileName.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane theList = new JScrollPane(doubleFileName);
		doubleFileName.setListData(listDoubleFileName);
		BoxPane.add(theList);

		frame.getContentPane().add(BoxPane);
		frame.pack();
		frame.setVisible(true);
	}
	
	public class MyOpenCatalogListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			try {
				fileOpen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileOpen.setAcceptAllFileFilterUsed(false);
				//fileOpen.setCurrentDirectory(new File(".")); // TODO Решить вопрос с точкой для задания текущего каталога
				int ret = fileOpen.showOpenDialog(null);
				if(ret == JFileChooser.APPROVE_OPTION & !isWork) {
					if(!sourceDirNameDouble.getText().equals("") && !sourceDirNameBadFile.getText().equals("")) {
							Thread FandR = new Thread(new FinderAndRemover());
							isWork = true;
							FandR.start();
						}
					else
						JOptionPane.showMessageDialog(null, "Error! Folder`s name is empty!");
				}
			} catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public class FinderAndRemover implements Runnable {

		@Override
		public void run() {
			if(getListFile(fileOpen.getSelectedFile())) {
				deleteNoImage(); // TODO Сделать Написать метод удаления не картинок
				if(createDirectory(fileOpen.getSelectedFile()))
					workToListFile(dirToDouble.getAbsolutePath());
				/*for(File f : listFile) {
					listDoubleFileName.add(f.getName());
					doubleFileName.setListData(listDoubleFileName);
				}*/
			}
			isWork = false;
		}

		public void deleteNoImage() {
					
			int i = 0;
			while(i < listFile.size()) {
				File f = listFile.get(i);
										
				if(!f.getName().substring(f.getName().length()-3, f.getName().length()).equals("jpg")) {
					listFile.remove(f);
				} else
					i++;
			}
			//listFile.removeIf(f -> (f.getName().substring(f.getName().length()-3, f.getName().length()) == "jpg"));
		}
		
		public boolean getListFile(File dir) {
			listFile = new ArrayList<>(Arrays.asList(dir.listFiles()));
			listFile.removeIf(f -> (f.isDirectory()));
			if(listFile.size() == 0) {
				JOptionPane.showMessageDialog(null, "Folder is Empty");
				return false;
			} else { // TOTO Тестирование Проверить на пустоту папки
				return true;
			}
		}

		public boolean createDirectory(File dir) {
			dirToDouble = new File(dir.getAbsolutePath() + "/" + sourceDirNameDouble.getText());
			
			if(createOrExistsDir(dirToDouble)) {
				if(CheckRemoveBad.isSelected()) {
					dirToBad = new File(dir.getAbsolutePath() + "/" + sourceDirNameBadFile.getText());
					if(createOrExistsDir(dirToBad)) {
						removeBadFromList(true);
						return true;
					} else
						return false;
				} else {
					removeBadFromList(false);
					return true;
				}
			} else
				return false;
		}
		
		public boolean createOrExistsDir(File dirCreate) {
			if(dirCreate.exists()) { // TODO Тестирование Проверить работу
				JOptionPane.showMessageDialog(null, "Error, folder is name \"" + dirCreate.getName() + "\" is exists, set new name folder");
				return false;
			}
			else if(dirCreate.mkdir())
				return true;
			else {// TODO Тестирование Проверить работу
				JOptionPane.showMessageDialog(null, "Error create folder name " + dirCreate.getName());
				return false;
			}
		}
		
		public void removeBadFromList(boolean inDir) {
			int i = 0;
			while(i < listFile.size()) {
				File f = listFile.get(i);
								
				if(!isOpenImage(f)) {
					setStringLine("Bad file " + f.getName());
					if(inDir) {
						if(removeFile(f, dirToBad.getAbsolutePath()))
							setStringLine(f.getName() + " removing complete");
						else
							setStringLine(f.getName() + " error, don`t remove");
					} else
						listFile.remove(f);
				} else
					i++;
			}
		}
		
		public boolean isOpenImage(File f) {
			try {
				ImageIO.read(f);
			} catch(IOException e) { return false; }
			return true;
		}
		
		public void workToListFile(String source) {
			int i = 0;
			int j = 0;

			// Первый этап отбора сначала по именам, а затем с проверкой содержимого
			while(i < listFile.size()) {
				j = 0;
				while(j < listFile.size()) {
					if(i != j) {
						String s1 = listFile.get(i).getName().substring(0, listFile.get(i).getName().length()-4);
						String s2 = listFile.get(j).getName().substring(0, listFile.get(j).getName().length()-4);
						
						if(s1.contains(s2) || s2.contains(s1)) {
							float f = getContentFile(listFile.get(i), listFile.get(j));
							if(decisionToRemove(listFile.get(i), listFile.get(j), f, source))
								System.out.println("Stage 1 " + i + " " + j);
						}
					}
					j++;
				}
				i++;
			}
			
			// Второй этап, с проверкой только содержимого
			i = 0;
			while(i < listFile.size()) {
				j = 0;
				while(j < listFile.size()) {
					if(i != j) {
						float f = getContentFile(listFile.get(i), listFile.get(j));
						if(decisionToRemove(listFile.get(i), listFile.get(j), f, source))
							System.out.println("Stage 2 " + i + " " + j);
					}
					j++;
				}
				i++;
			}
			
		}

		public boolean decisionToRemove(File file1, File file2, float f, String source) {
			if(f >= 99.0f) {
				if(removeFile(file2, source)) {
					return true;
				} else
					setStringLine("Don`t remove file " + file2.getName());
			} else if(f >= 50.0f) {
				setStringLine("Совпадение файлов: " + file1.getName() + " " + file2.getName() +
						" - " + f + " %");
				System.out.println("Совпадение файлов: " + file1.getName() + " " + file2.getName() +
						" - " + f + " %");
			} else if(f == -1f) {
				setStringLine("Один из файлов поврежден: " + file1 + " " + file2.getName());
				System.out.println("Один из файлов поврежден: " + file1 + " " + file2.getName());
			} else if(f == -2f) {
				setStringLine("Error - NullPointerException: " + file1.getName() + " " + file2.getName());
				System.out.println("Error - NullPointerException: " + file1.getName() + " " + file2.getName());
			}
			return false;
		}
		
		public float getContentFile(File s1, File s2) {
			BufferedImage img1 = null;
			BufferedImage img2 = null;
			float percent = 0f;
			int wImg1, hImg1, wImg2, hImg2;
			
			try {
				img1 = ImageIO.read(s1);
				img2 = ImageIO.read(s2);
			} catch(IOException e) { return -1f; }
			
			try {
				wImg1 = img1.getWidth();
				hImg1 = img1.getHeight();
	
				wImg2 = img2.getWidth();
				hImg2 = img2.getHeight();
			} catch(NullPointerException e) { return -2f; }
			
			if(wImg1 != wImg2 || hImg1 != hImg2)
				return 0f;
	
			for(int i = 0; i < wImg1; i++)
				for(int j = 0; j < hImg1; j++) {
					int p1 = img1.getRGB(i, j);
					int p2 = img2.getRGB(i, j);
					if(p1 != p2)
						percent++;
				}
	
			float f = 100.0f - (percent*100.0f/(wImg1*hImg1));
			return f;
		}
	
		public boolean removeFile(File f, String source) {
			if(f.renameTo((new File(source + "/" + f.getName())))) {
				setStringLine(f.getName() + " removing complete");
				listFile.remove(f);
				removeFiles++;
				return true;
			}
			else {
				listFile.remove(f);
				return false;
			}
		}
		
		public void setStringLine(String s) {
			listDoubleFileName.add(s);
			doubleFileName.setListData(listDoubleFileName);
		}
	}
}
