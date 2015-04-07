import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.sound.midi.*;
import java.io.*;

public class BeatBox {
    
    JPanel mainPanel;
    ArrayList<JCheckBox> checkBoxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame frame;
    boolean[] checkBoxState = new boolean[256];


    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap",
        "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"};
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public static void main(String[] args) {
        new BeatBox().buildGUI();
    }

    public void buildGUI() {
        frame = new JFrame("Cyber BeatBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        
        JButton upTempo = new JButton("upTempo");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("downTempo");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        JButton savePattern = new JButton("savePattern");
        savePattern.addActionListener(new MySavePatternListener());
        buttonBox.add(savePattern);

        JButton loadPattern = new JButton("loadPattern");
        loadPattern.addActionListener(new MyLoadPatternListener());
        buttonBox.add(loadPattern);

        JButton clearPattern = new JButton("clearPattern");
        clearPattern.addActionListener(new MyClearPatternListener());
        buttonBox.add(clearPattern);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        frame.getContentPane().add(background);

        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        for (int i = 0; i < 256; i++) {
            JCheckBox a = new JCheckBox();
            a.setSelected(false);
            mainPanel.add(a);
            checkBoxList.add(a);
        }

        setUpMidi();

        frame.setBounds(500, 50, 300, 300);
        frame.pack();
        frame.setVisible(true);
    }

    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void buildTrackAndStart() {
        int[] trackList = null;

        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; i++) {
            trackList = new int[16];
            int key = instruments[i];
            for (int j = 0; j < 16; j++) {
                JCheckBox jc = checkBoxList.get(j + 16*i);
                if (jc.isSelected()) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            }
            makeTrack(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        }

        track.add(makeEvent(192, 9, 1, 0, 15));
        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
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
            sequencer.setTempoFactor((float)(tempoFactor*1.03));
        }
    }

    public class MyDownTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor*0.97));
        }
    }

    public class MyClearPatternListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            for (int i = 0; i < 256; i++) {
                checkBoxList.get(i).setSelected(false);
            }
        }
    }

    public class MySavePatternListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            for (int i = 0; i < 256; i++) {
                JCheckBox checkBox = checkBoxList.get(i);
                if (checkBox.isSelected()) {
                    checkBoxState[i] = true;
                }
            }
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            File file = fileSave.getSelectedFile();

            try {
                FileOutputStream fs = new FileOutputStream(file);
                ObjectOutputStream os = new ObjectOutputStream(fs);
                os.writeObject(checkBoxState);
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class MyLoadPatternListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            File file = fileOpen.getSelectedFile();

            try {
                FileInputStream fs = new FileInputStream(file);
                ObjectInputStream is = new ObjectInputStream(fs);
                checkBoxState = (boolean[])is.readObject();
            } catch(Exception ex) {
                ex.printStackTrace();
            }

            for (int i = 0; i < 256; i++) {
                JCheckBox checkBox = checkBoxList.get(i);
                if (checkBoxState[i]) {
                    checkBox.setSelected(true);
                } else {
                    checkBox.setSelected(false);
                }
            }
        }
    }

    public void makeTrack(int[] list) {
        for (int i = 0; i < 16; i++) {
            int key = list[i];
            if (key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i+1));
            }
        }
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

}
