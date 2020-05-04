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

public class FindAndReplaceDoubleImage {
	JFrame			frame;
	JTabbedPane		tabbedPane;
	JTextField		sourceDirNameDouble;
	JTextField		sourceDirNameBadFile;
	JCheckBox		checkRemoveBad;
	JCheckBox		checkDeep;
	JList<String>	doubleFileName;
	JLabel			labelSource;
	JLabel			labelOperation;
	JFileChooser 	fileOpen = new JFileChooser();
	JButton 		buttonOpenCatalog;
	JButton 		buttonStart;
	JButton 		buttonStop;
	JLabel			labelGetFileName;
	JTextField		fileNameFrom;
	JCheckBox		checkAddDataCreateFile;
	JLabel			sampleNameFile;
	JLabel			labelWorkDirectory;

	Vector<String>	listDoubleFileName = new Vector<String>();
	List<File>		listFile;
	List<File>		listFileOneName = new ArrayList<File>();
	
	int				countRemoveFiles;
	boolean			isWork = false;
	boolean			isReady = false;
	File			dirToDouble;
	File			dirToBad;
	Thread			FandR;
	long			durSred1, durSred2, durNum;

	public static void main(String [] args) {
		new FindAndReplaceDoubleImage().go();
	}

	public void go() {
		frame = new JFrame("Find and Replace double Image");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(new Rectangle(100, 100, 400, 300));

		BorderLayout layout = new BorderLayout();
	    JPanel background = new JPanel(layout);
	    background.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    
		Box backgroundTable = Box.createVerticalBox();
		backgroundTable.setBorder(new EmptyBorder(5, 5, 5, 5));	

		Box BoxSetupFandR = Box.createVerticalBox();
		Box BoxSetupRename = Box.createVerticalBox();
		Box BoxButton = Box.createHorizontalBox();
		BoxButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		buttonOpenCatalog = new JButton("Open Catalog");
		buttonOpenCatalog.addActionListener(new MyOpenCatalogListener());
		BoxButton.add(buttonOpenCatalog);

		buttonStart = new JButton("Start");
		buttonStart.addActionListener(new MyStartListener());
		BoxButton.add(buttonStart);

		buttonStop = new JButton("Stop");
		buttonStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(FandR.isAlive())
					FandR.interrupt();
			}
		});
		BoxButton.add(buttonStop);

		labelSource = new JLabel("Source working directory: ");
		sourceDirNameDouble = new JTextField("double_image");
		BoxSetupFandR.add(labelSource);
		BoxSetupFandR.add(sourceDirNameDouble);
		BoxSetupFandR.add(javax.swing.Box.createVerticalStrut(5));

		sourceDirNameBadFile = new JTextField("bad_image");
		
		checkRemoveBad = new JCheckBox("Move corrupted files to:");
		checkRemoveBad.setSelected(true);
		checkRemoveBad.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(!isWork)
					sourceDirNameBadFile.setEnabled(checkRemoveBad.isSelected());
			}
		});

		checkDeep = new JCheckBox("Deep");
		checkDeep.setSelected(false);

		Box boxForCheck = Box.createHorizontalBox();
		boxForCheck.add(checkRemoveBad);
		boxForCheck.add(checkDeep);
		BoxSetupFandR.add(boxForCheck);
		BoxSetupFandR.add(sourceDirNameBadFile);
		BoxSetupFandR.add(javax.swing.Box.createVerticalStrut(5));

		labelOperation = new JLabel("This step: Open work directory and set settings");

		doubleFileName = new JList<String>();
		doubleFileName.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane theList = new JScrollPane(doubleFileName);
		doubleFileName.setListData(listDoubleFileName);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add("Find and Replace double", BoxSetupFandR);
		tabbedPane.add("Rename image", BoxSetupRename);
		
		backgroundTable.add(tabbedPane);
		backgroundTable.add(labelOperation);
		backgroundTable.add(theList);
		background.add(backgroundTable, BorderLayout.CENTER);
		background.add(BoxButton, BorderLayout.SOUTH);
		
		frame.getContentPane().add(background);
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
				if(ret == JFileChooser.APPROVE_OPTION) {
					if(!sourceDirNameDouble.getText().equals("") && !sourceDirNameBadFile.getText().equals(""))
						isReady = true;
					else
						JOptionPane.showMessageDialog(null, Resource.ERR0[0]);
				}
			} catch(Exception e) { e.printStackTrace(); }
		}
	}

	public class MyStartListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			if(isReady && !isWork) {
				FandR = new Thread(new FinderAndRemover());
				FandR.start();
				isWork = true;
				isReady = false;
				buttonOpenCatalog.setEnabled(false);
				sourceDirNameDouble.setEnabled(false);
				sourceDirNameBadFile.setEnabled(false);
				checkRemoveBad.setEnabled(false);
			}
		}
	}

	public class FinderAndRemover extends Thread {

		@Override
		public void run() {
			if(getListFile(fileOpen.getSelectedFile())) {
				deleteNoImage();
				if(checkDeep.isSelected())
					removeOneNameFile();
				if(createDirectory(fileOpen.getSelectedFile()))
					workToListFile(dirToDouble.getAbsolutePath());
			}
			isWork = false;
			buttonOpenCatalog.setEnabled(true);
			sourceDirNameDouble.setEnabled(true);
			sourceDirNameBadFile.setEnabled(checkRemoveBad.isSelected());
			checkRemoveBad.setEnabled(true);
		}

		public void deleteNoImage() {
			int i = 0;
			while(i < listFile.size()) {
				File f = listFile.get(i);
				String extension = f.getName().split("\\.")[1];

				if(!extension.toLowerCase().equals("jpg")) {
					setStringLine("File " + f.getName() + " is not image");
					listFile.remove(f);
				} else
					i++;
			}
		}

		public void removeOneNameFile() {
			int i = 0;
			while(i < listFile.size()) {
				File f = listFile.get(i);
				String filename = f.getName().split("\\.")[0];

				if(filename.length() == 1) {
					listFileOneName.add(f);
					listFile.remove(f);
				} else
					i++;
			}
		}

		public void addOneNameFile() {
			if(listFileOneName.size() > 0) {
				for(File f : listFileOneName)
					listFile.add(f);
			}
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
				if(checkRemoveBad.isSelected()) {
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

				labelOperation.setText("Find Bad Image " + i + " / " + listFile.size());
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
				j = i + 1;
				while(j < listFile.size()) {
					String s1 = listFile.get(i).getName().substring(0, listFile.get(i).getName().length()-4);
					String s2 = listFile.get(j).getName().substring(0, listFile.get(j).getName().length()-4);
					labelOperation.setText("Stage 1: " + (i+1) + " / " + listFile.size());

					if(s1.contains(s2) || s2.contains(s1)) {
						float f = getContentFile(getImageFromFile(listFile.get(i)), getImageFromFile(listFile.get(j)), 5);
						decisionToRemove(listFile.get(i), listFile.get(j), f, source);
					}
					j++;
				}
				i++;
			}

			if(checkDeep.isSelected()) {
				addOneNameFile();

				// Второй этап, с проверкой только содержимого
				i = 0;
				while(i < listFile.size()) {
					j = i + 1;
					BufferedImage img1 = getImageFromFile(listFile.get(i));
					while(j < listFile.size()) {
						labelOperation.setText("Stage 2: " + (i+1) + " / " + (j+1) + " / " + listFile.size());
						float f = getContentFile(img1, getImageFromFile(listFile.get(j)), 5);
						decisionToRemove(listFile.get(i), listFile.get(j), f, source);
						j++;
					}
					i++;
				}
			}
		}

		public boolean decisionToRemove(File file1, File file2, float f, String source) {
			if(f >= 99.0f) {
				if(removeFile(file2, source)) {
					setStringLine(file2.getName() + " removing complete");
					//System.out.println("Файл перенесен: " + file2.getName());
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

		public float getContentFile(BufferedImage img1, BufferedImage img2, int koeff) {
			float percent = 0f;
			int wImg1, hImg1, wImg2, hImg2;
			int numPixelSee = 0;
			int koeffW, koeffH;

			if(img1 == null || img2 == null)
				return -1f;

			try {
				wImg1 = img1.getWidth();
				hImg1 = img1.getHeight();

				wImg2 = img2.getWidth();
				hImg2 = img2.getHeight();
			} catch(NullPointerException e) { return -2f; }

			if(wImg1 != wImg2 || hImg1 != hImg2)
				return 0f;



			koeffW = (int)(wImg1 / koeff);
			koeffH = (int)(hImg1 / koeff);

			for(int i = koeffW; i < wImg1; i+=koeffW)
				for(int j = koeffH; j < hImg1; j+=koeffH) {
					numPixelSee++;
					int p = Math.abs(img1.getRGB(i, j) - img2.getRGB(i, j));
					if(p != 0)
						percent++;
				}

			// Измерить время выполнения программы
			/*long st = 0, en = 0, dur = 0;
			st = System.nanoTime();*/

			/*en = System.nanoTime();
			dur = en-st;
			dur = TimeUnit.MILLISECONDS.convert(dur, TimeUnit.NANOSECONDS);
			durSred1 += dur;

			numPixelSee = 0;
			percent = 0;
			st = System.nanoTime();*/
			/*for(int i = 0; i < img1.getRaster().getDataBuffer().getSize(); i++) {
				numPixelSee++;
				int p1 = img1.getRaster().getDataBuffer().getElem(i);
				int p2 = img2.getRaster().getDataBuffer().getElem(i);
				if(p1 != p2)
					percent++;
			}*/
			/*en = System.nanoTime();
			dur = en-st;
			dur = TimeUnit.MILLISECONDS.convert(dur, TimeUnit.NANOSECONDS);
			durSred2 += dur;

			durNum++;
			System.out.println("Work file " + " - " + " sred " + (durSred1/durNum) + " - " + (durSred2/durNum));*/

			float f = 100.0f - (percent*100.0f/(numPixelSee));
			return f;
		}

		public BufferedImage getImageFromFile(File f) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(f);
			} catch(IOException e) { return null; }

			return img;
		}

		public boolean removeFile(File f, String source) {
			if(f.renameTo((new File(source + "/" + f.getName())))) {
				listFile.remove(f);
				countRemoveFiles++;
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

	private static final class Resource {
		static final String [] ERR0 = {"Error! Folder`s name is empty!", "Ошибка, имя папки отсутствует!"};
	}
}
