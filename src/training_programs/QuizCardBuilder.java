import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class QuizCardBuilder extends JFrame {
  JPanel panel;
  Label labelQuestion;
  Label labelAnswer;
  JTextArea textQuestion;
  JTextArea textAnswer;
  JButton buttonSave;
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

    buttonSave = new JButton("Save");
    buttonSave.addActionListener(new saveActionListener());
    buttonNext = new JButton("Next card");
    buttonNext.addActionListener(new nextActionListener());

    Box Box1 = new Box(BoxLayout.Y_AXIS);
    Box1.add(labelQuestion);
    Box1.add(scrollQuestion);
    Box1.add(labelAnswer);
    Box1.add(scrollAnswer);
    Box Box2 = new Box(BoxLayout.X_AXIS);
    Box2.add(buttonSave);
    Box2.add(buttonNext);
    panel.add(BorderLayout.CENTER, Box1);
    panel.add(BorderLayout.SOUTH, Box2);

    createList();

    this.getContentPane().add(panel);
    this.pack();
    this.setVisible(true);
  }

  private class nextActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      thisQA++;
      if(thisQA > listQA.size()-1)
        thisQA = 0;
      textQuestion.setText(listQA.get(thisQA).getQuestion());
      textAnswer.setText(listQA.get(thisQA).getAnswer());
    }
  }

  private class saveActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      saveList();
    }
  }

  public void createList() {
    //listQA.add(new QuizAnswer("1+1", "2"));
    //listQA.add(new QuizAnswer("1+3", "4"));
    //listQA.add(new QuizAnswer("2+3", "5"));
    openList();
    textQuestion.setText(listQA.get(thisQA).getQuestion());
    textAnswer.setText(listQA.get(thisQA).getAnswer());
  }

  private void saveList() {
    try {
      ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("quiz.sp"));
      for(QuizAnswer q : listQA)
        os.writeObject(q);

      os.close();
    } catch(Exception e) { e.printStackTrace(); }
  }

  private void openList() {
    listQA.clear();
      try {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("quiz.sp"));

        while(true) {
          QuizAnswer t = null;
          t = (QuizAnswer) in.readObject();
          if(t != null)
          listQA.add((QuizAnswer) t);
        }
      } catch(EOFException e) { System.out.println("Es!"); } 
      catch(Exception e) { e.printStackTrace(); }
  }

  private class QuizAnswer implements Serializable {
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
}
