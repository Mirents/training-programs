//package training_programs;

import javax.sound.midi.*;

public class MusicTest1 {

  public void play() {
    try {
      Sequencer seq = MidiSystem.getSequencer();
      System.out.println("We get a sintez!");
    } catch(MidiUnavailableException ex) {
      System.out.println("Error inicialize!");
    }
  }

  public static void main(String[] args) {
    MusicTest1 mt1 = new MusicTest1();
    mt1.play();
  }
}
