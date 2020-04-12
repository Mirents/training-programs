import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MiniMusicService implements Service {
  myDrawPanel myPanel;

  public JPanel getGuiPanel() {
    JPanel mainPanel = new JPanel();
    myPanel = new myDrawPanel();
    JButton playButton = new JButton("Play it");
    playButton.addActionListener(new PlayListener());
    mainPanel.add(myPanel);
    mainPanel.add(playButton);
    return mainPanel;
  }

  public class PlayListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequencer.addControllerEventListener(myPanel, new int[] {127});
        Sequence seq = new Sequence(Sequence.PPQ, 4);
        Track track = seq.createTrack();

        for(int i=0; i<100; i+=4) {
          int r = (int) ((Math.random() * 50) + 1);
          if(r < 38) {
            track.add(makeEvent(144, 1, r, 100, i));
            track.add(makeEvent(176, 1, 127, 0, i));
            track.add(makeEvent(128, 1, r, 100, i+2));
          }
        }

        sequencer.setSequence(seq);
        sequencer.setTempoInBPM(220);
        sequencer.start();
      } catch (Exception ex) { ex.printStackTrace(); }
    }
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

      int r = (int) (Math.random() * 250);
      int g = (int) (Math.random() * 250);
      int b = (int) (Math.random() * 250);

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
