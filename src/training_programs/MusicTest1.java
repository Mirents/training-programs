import java.sound.midi.*;

public class MusicTest1 {

  public void play() {
    Sequencer seq = MidiSystem.getSequencer();
    System.out.println("We get a sintez!");
  }

  public static void main(String[] args) {
    MusicTest1 mt1 = new MusicTest1();
    mt1.play();
  }
}
