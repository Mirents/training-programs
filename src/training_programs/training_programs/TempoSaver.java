import java.io.*;
import java.util.*;

public class TempoSaver implements Serializable {
  //private transient static final long serialVersionUID = 10001L;
  //private ArrayList<Boolean> ListTempo = new ArrayList<Boolean>();
  int i;

  //TempoSaver(ArrayList<JCheckBox> list) {
  TempoSaver(int s) {
    i = s;
    /*for(JCheckBox cb : list) {
      if(cb.isSelected())
        ListTempo.add(true);
      else
        ListTempo.add(false);
    }*/
  }

  public ArrayList<Boolean> RestoreTempo() {
    ArrayList<Boolean> ListTempo = new ArrayList<Boolean>();
    return ListTempo;
  }
}
