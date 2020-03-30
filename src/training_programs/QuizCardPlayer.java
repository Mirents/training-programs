import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.util.*;
import java.io.FileReader;
import training_programs.QuizAnswer;

public class QuizCardPlayer extends JFrame {
  JPanel panel;
  JButton button;
  JTextArea text;
  ArrayList<QuizAnswer> listQA = new ArrayList<QuizAnswer>();
  int thisQA = 0;
  boolean isAnswer = false;

  public static void main(String[] args) {
    new QuizCardPlayer();
  }

  QuizCardPlayer() {
    super("Quiz card game");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBounds(50, 50, 300, 300);
    BorderLayout layout = new BorderLayout();
    panel = new JPanel(layout);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

    text = new JTextArea(3, 20);
    text.setLineWrap(true);
    JScrollPane scrollText = new JScrollPane(text);
    scrollText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    button = new JButton("Next Answer");
    button.addActionListener(new NextActionListener());

    Box Box1 = new Box(BoxLayout.Y_AXIS);
    Box1.add(scrollText);
    Box Box2 = new Box(BoxLayout.Y_AXIS);
    Box2.add(button);
    panel.add(BorderLayout.CENTER, Box1);
    panel.add(BorderLayout.SOUTH, Box2);

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem openMenuItem = new JMenuItem("Open card file");
    openMenuItem.addActionListener(new OpenMenuListener());
    JMenuItem exitMenuItem = new JMenuItem("Exit");
    exitMenuItem.addActionListener(new ExitMenuListener());
    fileMenu.add(openMenuItem);
    fileMenu.add(exitMenuItem);
    menuBar.add(fileMenu);
    this.setJMenuBar(menuBar);

    text.setText("");

    this.getContentPane().add(panel);
    this.pack();
    this.setVisible(true);
  }

  private class NextActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if(listQA.size() > 0) {
        if(isAnswer) {
          thisQA++;
          if(thisQA >= listQA.size())
            thisQA = 0;
          text.setText(listQA.get(thisQA).getQuestion());
          button.setText("Answer");
          isAnswer = false;
        } else {
          text.setText(listQA.get(thisQA).getAnswer());
          isAnswer = true;
          button.setText("Next Question");
        }
      } else {
        text.setText("Card not load");
      }
    }
  }

  private class OpenMenuListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Quiz card file *.qcb", "qcb");
      JFileChooser fileOpen = new JFileChooser();
      fileOpen.setCurrentDirectory(new File("."));
      fileOpen.setFileFilter(filter);
      int ret = fileOpen.showOpenDialog(null);
      if(ret == JFileChooser.APPROVE_OPTION)
        openList(fileOpen.getSelectedFile());
        if(listQA.size() > 0) {
          text.setText(listQA.get(0).getQuestion());
          button.setText("Answer");
        }
    }
  }

  private void openList(File file) {
    listQA.clear();
    thisQA = 0;
      try {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        while(true) {
          QuizAnswer t = null;
          t = (QuizAnswer) in.readObject();
          if(t != null) {
            listQA.add((QuizAnswer) t);
          }
        }
      } catch(EOFException e) { }
      catch(Exception e) { e.printStackTrace(); }
  }

  private class ExitMenuListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      dispose();
    }
  }
}
