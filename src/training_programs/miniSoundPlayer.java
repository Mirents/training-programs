import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class miniSoundPlayer {
  static JFrame f = new JFrame("My First Music clip");
  static myDrawPanel mdp;

  public static void main(String [] args) {
    miniSoundPlayer mini = new miniSoundPlayer();
    mini.go();
  }

  public void setUpGUI() {
    mdp = new myDrawPanel();
    f.setContentPane(mdp);
    f.setBounds(30, 30, 300, 300);
    f.setVisible(true);
  }

  public void go() {
    setUpGUI();

    try {
      Sequencer sequencer = MidiSystem.getSequencer();
      sequencer.open();
      int [] eventsIWant = { 127 };
      sequencer.addControllerEventListener(mdp, eventsIWant);
      Sequence seq = new Sequence(Sequence.PPQ, 4);
      Track track = seq.createTrack();

      int r = 0;
      for(int i=0; i<60; i+=4) {
        r = (int) ((Math.random() * 50) + 1);

        track.add(makeEvent(144, 1, r, 100, i));
        track.add(makeEvent(176, 1, 127, 0, i));
        track.add(makeEvent(128, 1, r, 100, i+2));
      }

      sequencer.setSequence(seq);
      sequencer.setTempoInBPM(120);
      sequencer.start();
    } catch (Exception ex) { ex.printStackTrace(); }
  }

  public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
    MidiEvent event = null;

    try {
      ShortMessage a = new ShortMessage();
      a.setMessage(comd, chan, one, two);
      event = new MidiEvent(a, tick);
    } catch (Exception e) {}
    return event;
  }

  class myDrawPanel extends JPanel implements ControllerEventListener {
    boolean msg = false;

    public void controlChange(ShortMessage event) {
      msg = true;
      repaint();
    }

    public void paintComponent(Graphics gr) {
      Graphics2D g2D = (Graphics2D) gr;

      int r = (int) (Math.random() * 255);
      int g = (int) (Math.random() * 255);
      int b = (int) (Math.random() * 255);

      g2D.setColor(new Color(r, g, b));

      int ht = (int) ((Math.random() * 120) + 10);
      int wh = (int) ((Math.random() * 120) + 10);

      int x = (int) ((Math.random() * 40) + 10);
      int y = (int) ((Math.random() * 40) + 10);

      g2D.fillRect(x, y, ht, wh);
      msg = false;
    }
  }
}
