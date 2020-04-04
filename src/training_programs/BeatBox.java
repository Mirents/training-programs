import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.io.*;

public class BeatBox {
  JPanel mainPanel;
  ArrayList<JCheckBox> checkBoxList;
  Sequencer sequencer;
  Sequence sequence;
  Track track;
  JFrame theFrame;
  String [] instrumentNames = {"Bass Drum", "Closet Hi-Hat", "Open Hi-Hat",
  "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo",
  "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom",
  "High Agogo", "Open Hi Conga"};
  int [] instments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

  public static void main(String [] args) {
    new BeatBox().buildGUI();
  }

  public void buildGUI() {
    theFrame = new JFrame("Cyber BeatBox");
    theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    BorderLayout layout = new BorderLayout();
    JPanel background = new JPanel(layout);
    background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    checkBoxList = new ArrayList<JCheckBox>();
    Box buttonBox = new Box(BoxLayout.Y_AXIS);

    JButton start = new JButton("Start");
    start.addActionListener(new MyStartListener());
    buttonBox.add(start);

    JButton stop = new JButton("Stop");
    stop.addActionListener(new MyStopListener());
    buttonBox.add(stop);

    JButton upTempo = new JButton("Tempo Up");
    upTempo.addActionListener(new MyUpTempoListener());
    buttonBox.add(upTempo);

    JButton downTempo = new JButton("Tempo Down");
    downTempo.addActionListener(new MyDownTempoListener());
    buttonBox.add(downTempo);

    JButton saveButton = new JButton("Save Tempo");
    saveButton.addActionListener(new MySaveTempoListener());
    buttonBox.add(saveButton);

    JButton restoreButton = new JButton("Restore Tempo");
    restoreButton.addActionListener(new MyRestoreTempoListener());
    buttonBox.add(restoreButton);

    Box nameBox = new Box(BoxLayout.Y_AXIS);
    for(int i = 0; i < 16; i++) {
      nameBox.add(new Label(instrumentNames[i]));
    }

    background.add(BorderLayout.EAST, buttonBox);
    background.add(BorderLayout.WEST, nameBox);

    theFrame.getContentPane().add(background);

    GridLayout grid = new GridLayout(16, 16);
    grid.setVgap(1);
    grid.setHgap(2);
    mainPanel = new JPanel(grid);
    background.add(BorderLayout.CENTER, mainPanel);

    for(int i = 0; i < 256; i++) {
      JCheckBox c = new JCheckBox();
      c.setSelected(false);
      checkBoxList.add(c);
      mainPanel.add(c);
    }

    setUpMidi();

    theFrame.setBounds(50, 50, 300, 300);
    theFrame.pack();
    theFrame.setVisible(true);
  }

  public void setUpMidi() {
    try {
      sequencer = MidiSystem.getSequencer();
      sequencer.open();
      sequence = new Sequence(Sequence.PPQ, 4);
      track = sequence.createTrack();
      sequencer.setTempoInBPM(120);
    } catch(Exception e) { e.printStackTrace(); }
  }

  public void buildTrackAndStart() {
    int [] trackList = null;

    sequence.deleteTrack(track);
    track = sequence.createTrack();

    for(int i = 0; i < 16; i++) {
      trackList = new int[16];
      int key = instments[i];

      for(int j = 0; j < 16; j++) {
        JCheckBox jc = (JCheckBox) checkBoxList.get(j + (16*i));
        if(jc.isSelected()) {
          trackList[j] = key;
        } else {
          trackList[j] = 0;
        }
      }

      makeTracks(trackList);
      track.add(makeEvent(176, 1, 127, 0, 16));
    }

    track.add(makeEvent(192, 9, 1, 0, 15));
    try {
      sequencer.setSequence(sequence);
      sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
      sequencer.start();
      sequencer.setTempoInBPM(120);
    } catch(Exception e) { e.printStackTrace(); }
  }

  public class MyStartListener implements ActionListener {
    public void actionPerformed(ActionEvent a) {
      buildTrackAndStart();
    }
  }

  public class MyStopListener implements ActionListener {
    public void actionPerformed(ActionEvent a) {
      sequencer.stop();
    }
  }

  public class MyUpTempoListener implements ActionListener {
    public void actionPerformed(ActionEvent a) {
      float tempoFactor = sequencer.getTempoFactor();
      sequencer.setTempoFactor((float) (tempoFactor * 1.03));
    }
  }

  public class MyDownTempoListener implements ActionListener {
    public void actionPerformed(ActionEvent a) {
      float tempoFactor = sequencer.getTempoFactor();
      sequencer.setTempoFactor((float) (tempoFactor * .97));
    }
  }

  public class MySaveTempoListener implements ActionListener {
    public void actionPerformed(ActionEvent a) {
      try {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File("BeatBoxTempo.bbt")));
        TempoSaver ts = new TempoSaver(checkBoxList);
        ArrayList<Boolean> lt = ts.RestoreTempo();
        for(Boolean b : lt)
          System.out.println(b);
        os.writeObject(ts);
        os.close();
      } catch(Exception e) { e.printStackTrace(); }
    }
  }

  public class MyRestoreTempoListener implements ActionListener {
    public void actionPerformed(ActionEvent a) {
      try {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("BeatBoxTempo.bbt"));
          TempoSaver t = (TempoSaver) in.readObject();
          ArrayList<Boolean> lt = t.RestoreTempo();
          int i = 0;
          for(JCheckBox c : checkBoxList) {
            c.setSelected(lt.get(i));
            i++;
          }
      } catch(Exception e) { e.printStackTrace(); }
    }
  }

  public void makeTracks(int [] list) {
    for(int i = 0; i < 16; i++) {
      int key = list[i];

      if(key != 0) {
        track.add(makeEvent(144, 9, key, 100, i));
        track.add(makeEvent(128, 9, key, 100, i+1));
      }
    }
  }

  public MidiEvent makeEvent(int comd, int chan, int on, int tw, int tick) {
    MidiEvent event = null;
    try {
      ShortMessage a = new ShortMessage();
      a.setMessage(comd, chan, on, tw);
      event = new MidiEvent(a, tick);
    } catch(Exception e) { e.printStackTrace(); }
    return event;
  }

  private class TempoSaver implements Serializable {
    //private transient static final long serialVersionUID = 10001L;
    private ArrayList<Boolean> ListTempo = new ArrayList<Boolean>();

    TempoSaver(ArrayList<JCheckBox> list) {
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
}
