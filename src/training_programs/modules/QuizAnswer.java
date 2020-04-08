package modules;

import java.io.*;

public class QuizAnswer implements Serializable {
  private transient static final long serialVersionUID = 10000L;
  private String question;
  private String answer;

  public QuizAnswer(String question, String answer) {
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
