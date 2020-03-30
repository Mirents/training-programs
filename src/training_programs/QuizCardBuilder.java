import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.io.FileReader;

public class QuizCardBuilder extends JFrame {
  JPanel panel;
  Label labelQuestion;
  Label labelAnswer;
  JTextArea textQuestion;
  JTextArea textAnswer;
  JButton buttonNext;
  ArrayList<QuizAnswer> listQA = new ArrayList<QuizAnswer>();
  int thisQA = 0;

  public static void main(String[] args) {
    new QuizCardBuilder();
  }

  QuizCardBuilder() {
    super("QuizCardBuilder");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBounds(50, 50, 300, 300);
    BorderLayout layout = new BorderLayout();
    panel = new JPanel(layout);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

    textQuestion = new JTextArea(6, 20);
    textQuestion.setLineWrap(true);
    JScrollPane scrollQuestion = new JScrollPane(textQuestion);
    scrollQuestion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollQuestion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    textAnswer = new JTextArea(2, 20);
    textAnswer.setLineWrap(true);
    JScrollPane scrollAnswer = new JScrollPane(textAnswer);
    scrollAnswer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollAnswer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    labelQuestion = new Label("Question");
    labelAnswer = new Label("Answer");

    buttonNext = new JButton("Next card");
    buttonNext.addActionListener(new nextActionListener());

    Box Box1 = new Box(BoxLayout.Y_AXIS);
    Box1.add(labelQuestion);
    Box1.add(scrollQuestion);
    Box1.add(labelAnswer);
    Box1.add(scrollAnswer);
    Box Box2 = new Box(BoxLayout.X_AXIS);
    Box2.add(buttonNext);
    panel.add(BorderLayout.CENTER, Box1);
    panel.add(BorderLayout.SOUTH, Box2);

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem newMenuItem = new JMenuItem("New");
    newMenuItem.addActionListener(new NewMenuListener());
    JMenuItem openMenuItem = new JMenuItem("Open");
    openMenuItem.addActionListener(new OpenMenuListener());
    JMenuItem saveMenuItem = new JMenuItem("Save");
    saveMenuItem.addActionListener(new SaveMenuListener());
    fileMenu.add(newMenuItem);
    fileMenu.add(openMenuItem);
    fileMenu.add(saveMenuItem);
    menuBar.add(fileMenu);
    this.setJMenuBar(menuBar);

    listQA.add(new QuizAnswer("Привет, как дела?", "Вася"));
    listQA.add(new QuizAnswer("Где был?", "Петя"));
    listQA.add(new QuizAnswer("Как сам?", "Кузя"));
    textQuestion.setText(listQA.get(0).getQuestion());
    textAnswer.setText(listQA.get(0).getAnswer());

    this.getContentPane().add(panel);
    this.pack();
    this.setVisible(true);
  }

  private class nextActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if(listQA.size() > 0) {
        thisQA++;
        if(thisQA >= listQA.size())
          thisQA = 0;
        textQuestion.setText(listQA.get(thisQA).getQuestion());
        textAnswer.setText(listQA.get(thisQA).getAnswer());
      } /* else if(!textQuestion.getText().equals("") && !textAnswer.getText().equals(""))
        listQA.add(new QuizAnswer(textQuestion.getText(), textAnswer.getText()));*/

        saveList("12345.trt");
    }
  }

  private class NewMenuListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if(listQA.size() > 0)
        listQA.clear();

      thisQA = 0;
      textQuestion.setText("");
      textAnswer.setText("");
    }
  }

  private class OpenMenuListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      JFileChooser fileOpen = new JFileChooser();
      int ret = fileOpen.showOpenDialog(null);
      if(ret == JFileChooser.APPROVE_OPTION)
        openList(fileOpen.getSelectedFile());
    }
  }

  private class SaveMenuListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String s = createSaveFile();
      saveList(s);
    }
  }

  private String createSaveFile() {
    try {
    JFileChooser fileSave = new JFileChooser();
    int ret = fileSave.showSaveDialog(null);
    if(ret == JFileChooser.APPROVE_OPTION) {
      File file = fileSave.getSelectedFile();
        return file.getName();
    }
  } catch(Exception ex) {
    System.out.println("___________________2");
    ex.printStackTrace();
  }
  return null;
  }

  private void saveList(String file) {
    if(listQA.size() > 0 && !file.getName().equals(""))
    {
      try {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file + ".qcb"));
        for(QuizAnswer q : listQA) {
          System.out.println("Save " + q.getQuestion());
          os.writeObject(q);
        }
        os.close();
      } catch(Exception e) {
        System.out.println("___________________1");
        e.printStackTrace();
      }
    }
  }

  private void openList(File file) {
    listQA.clear();
      try {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));

        while(true) {
          QuizAnswer t = null;
          t = (QuizAnswer) in.readObject();
          if(t != null)
          listQA.add((QuizAnswer) t);
        }
      } catch(EOFException e) { System.out.println("Open work!"); }
      catch(Exception e) { e.printStackTrace(); }
  }

  private class QuizAnswer implements Serializable {
    private transient static final long serialVersionUID = 11111L;
    private String question;
    private String answer;

    QuizAnswer(String question, String answer) {
      this.question = question;
      this.answer = answer;
    }

    public String getQuestion() {
      return question;
    }

    public String getAnswer() {
      return answer;
    }

    public void setAnswer(String answer) {
      this.answer = answer;
    }

    public void setQuestion(String question) {
      this.question = question;
    }
  }


  public class GameCharacter implements Serializable {
    int power;
    String type;
    String weapons;

    public GameCharacter(int p, String t, String w) {
      power = p;
      type = t;
      weapons = w;
    }

    public String getType() {
      return type;
    }

    public int getPower() {
      return power;
    }

    public String getWeapons() {
        return weapons;
    }
  }



}
