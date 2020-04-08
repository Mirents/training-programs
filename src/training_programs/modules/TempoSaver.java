package modules;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class TempoSaver implements Serializable {
  private transient static final long serialVersionUID = 10001L;
  private ArrayList<Boolean> ListTempo = new ArrayList<Boolean>();

  public TempoSaver(ArrayList<JCheckBox> list) {
    for(JCheckBox cb : list) {
      if(cb.isSelected())
        ListTempo.add(true);
      else
        ListTempo.add(false);
    }
  }

  public ArrayList<Boolean> RestoreTempo() {
    return ListTempo;
  }
}
