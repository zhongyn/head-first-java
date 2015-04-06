import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class MusicPlayer {
    
    static JFrame frame = new JFrame("My first music player");
    static MyDrawPanel panel;

    public static void main(String[] args) {
        MusicPlayer mp = new MusicPlayer();
        mp.go();
    }

    public void setUpGui() {
        panel = new MyDrawPanel();
        frame.setContentPane(panel);
        frame.setBounds(30, 30, 300, 300);
        frame.setVisible(true);
    }

    public void go() {
        setUpGui();

        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addControllerEventListener(panel, new int[] {127});
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            int r = 0;
            for (int i = 0; i < 60; i++) {
                r = (int)((Math.random() * 50) + 1);
                track.add(makeEvent(144, 1, r, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, r, 100, i+2));
            }

            sequencer.setSequence(seq);
            sequencer.start();
            sequencer.setTempoInBPM(60);
        } catch(Exception ex) {ex.printStackTrace();}
    }

    public MidiEvent makeEvent(int cmd, int chanel, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage msg = new ShortMessage();
            msg.setMessage(cmd, chanel, one, two);
            event = new MidiEvent(msg, tick);
        } catch(Exception ex) {}
        return event;
    }

    class MyDrawPanel extends JPanel implements ControllerEventListener {
        boolean msg = false;

        public void controlChange(ShortMessage event) {
            msg = true;
            repaint();
        }

        public void paintComponent(Graphics g) {
            if (msg) {
                int r = (int)(Math.random() * 250);
                int gr = (int)(Math.random() * 250);
                int b = (int)(Math.random() * 250);

                g.setColor(new Color(r, gr, b));

                int ht = (int)(Math.random() * 120 +10);
                int wd = (int)(Math.random() * 120 +10);

                int x = (int)(Math.random() * 40 + 10);
                int y = (int)(Math.random() * 40 + 10);

                g.fillRect(x, y, ht, wd);
                msg = false;
            }
        }
    }

}
