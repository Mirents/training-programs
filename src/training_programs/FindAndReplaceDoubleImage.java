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

	DefaultListModel<String> model = new DefaultListModel<String>();
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
		
		doubleFileName = new JList<String>(model);
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
					System.out.println(listFile.toString());
			}
			isWork = false;
		}

		public void deleteNoImage() {
			/*for(File f : listFile)
				System.out.println(f.getName().substring(f.getName().length()-3, f.getName().length()));*/
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
						// TODO Исправить Каталог для битых файлов успешно создан, можно перемещать их
						removeBad();
						return true;
					} else
						return false;
				} else {
					removeBad();
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
		
		public void removeBad() {
			int i = 0;
			while(i < listFile.size()) {
				File f = listFile.get(i);
					if(!openImage(f)) {
						listDoubleFileName.add("Remove file " + f.getName());
						doubleFileName.setListData(listDoubleFileName);
						listFile.remove(f);
					}
					else
						i++;
			}
		}
	
		public boolean openImage(File f) {
			try {
				ImageIO.read(f);
			} catch(IOException e) { return false; }
			return true;
		}
		
		public void workToListFile(String source) {
			int i = 0;
			int j = 0;
			boolean endList = false;
			
			while(true) {
				endList = false;
				j = 0;
				while(!endList) {
					if(j == listFile.size()-1) {
						endList = true;
					}
					else {
						//String s1 = listFile.get(i).getName().substring(0, listFile.get(i).getName().length()-4);
						//String s2 = listFile.get(j).getName().substring(0, listFile.get(j).getName().length()-4);
			
						//System.out.println("i= " + i + " j= " + j + " Сравниваю файлы: " + s1 + " " + s2);
						
						if(i != j) {
							float f = getContentFile(listFile.get(i), listFile.get(j));
							if(f >= 99.0f) {
								if(removeFile(listFile.get(j), source)) {
									//i = -1;
									endList = true;
								} else {
									/*listDoubleFileName.add("Dont remove file " + listFile.get(j).getName());
									doubleFileName.setListData(listDoubleFileName);*/
									model.addElement("Dont remove file " + listFile.get(j).getName());
									System.out.println("Dont remove file " + listFile.get(j).getName());
								}
							} else if(f >= 20.0f) {
								/*listDoubleFileName.add("Совпадение файлов: " + listFile.get(i).getName() + " " + listFile.get(j).getName() +
										" - " + f + " %");
								doubleFileName.setListData(listDoubleFileName);*/
								model.addElement("Совпадение файлов: " + listFile.get(i).getName() + " " + listFile.get(j).getName() +
										" - " + f + " %");
								System.out.println("Совпадение файлов: " + listFile.get(i).getName() + " " + listFile.get(j).getName() +
										" - " + f + " %");
							} else if(f == -1f) {
								/*listDoubleFileName.add("Один из файлов поврежден: " + listFile.get(i).getName() + " " + listFile.get(j).getName() +
										" - " + f + " %");
								doubleFileName.setListData(listDoubleFileName);*/
								model.addElement("Один из файлов поврежден: " + listFile.get(i).getName() + " " + listFile.get(j).getName() +
										" - " + f + " %");
								System.out.println("Один из файлов поврежден: " + listFile.get(i).getName() + " " + listFile.get(j).getName() +
										" - " + f + " %");
							} else if(f == -2f) {
								System.out.println("NullPointerException: " + listFile.get(i).getName() + " " + listFile.get(j).getName() +
								" - " + f + " %");
							}
						}
						j++;
					}
				}
				
				if(i == listFile.size()-1)
					break;
				else
					i++;
			}
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
				listFile.remove(f);
				/*listDoubleFileName.add(f.getName());
				doubleFileName.setListData(listDoubleFileName);*/
				model.addElement("Remove file " + f.getName());
				removeFiles++;
				System.out.println("remove " + f.getName());
				return true;
			}
			else
				return false;
		}
	}
}
