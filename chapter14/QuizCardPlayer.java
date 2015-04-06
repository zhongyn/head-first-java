import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class QuizCardPlayer {
    
    private JFrame frame;
    private JPanel panel;
    private JTextArea text;
    private JButton nextButton;
    private ArrayList<QuizCard> cardList;
    private boolean isShowAnswer;

    public static void main(String[] args) {
        QuizCardPlayer player = new QuizCardPlayer();
        player.go();
    }

    public void go() {
        // build and display gui
        frame = new JFrame();
        panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        Box background = new Box(BoxLayout.Y_AXIS);
        panel.add(background);

        text = new JTextArea(6, 20);
        JScrollPane textScroller = new JScrollPane(text);
        text.setLineWrap(true);
        textScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        background.add(textScroller);

        nextButton = new JButton("Show Question");
        nextButton.addActionListener(new NextCardListener());
        background.add(nextButton);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadItem = new JMenuItem("Load card set");
        loadItem.addActionListener(new LoadMenuListener());

        fileMenu.add(loadItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.setBounds(300, 200, 400, 300);
        frame.setVisible(true);

        cardList = new ArrayList<QuizCard>();

    }

    public class NextCardListener implements ActionListener {
        // if this is a question, show the answer, otherwise show next question
        // set a flag for whether we are viewing a question or answer
        public void actionPerformed(ActionEvent ev) {
            
        }
    }

    public class LoadMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            // bring up a file dialog box, let the user navigate to and choose a card set to open
        }
    }

    private void loadFile(File file) {
        // build an arraylist of cards, by reading them from a text file
        // and tell the makeCard() method to make a new card out of the line
    }

    private void makeCard(String line) {
        //called by the loadFile() method, create a new quizCard and adds it to the cardlist
    }




























}