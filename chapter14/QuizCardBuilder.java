import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class QuizCardBuilder {
    
    private JFrame frame;
    private JPanel panel;
    private JTextArea question;
    private JTextArea answer;
    private JButton nextCard;
    private ArrayList<QuizCard> cardList;

    public static void main(String[] args) {
        QuizCardBuilder builder = new QuizCardBuilder();
        builder.go();
    }

    public void go() {
        // build and display gui
        frame = new JFrame("Quiz Card Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.getContentPane().add(panel);

        Box background = new Box(BoxLayout.Y_AXIS); 
        panel.add(background);

        background.add(new JLabel("Question:"));

        question = new JTextArea(5, 20);
        JScrollPane questionScroller = new JScrollPane(question);
        question.setLineWrap(true);
        questionScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        questionScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        background.add(questionScroller);

        background.add(new JLabel("Answer::"));

        answer = new JTextArea(5, 20);
        JScrollPane answerScroller = new JScrollPane(answer);
        answer.setLineWrap(true);
        answerScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        answerScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        background.add(answerScroller);

        nextCard = new JButton("Next Card");
        background.add(nextCard);
        nextCard.addActionListener(new NextCardListener());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        newMenuItem.addActionListener(new NewMenuListener());
        saveMenuItem.addActionListener(new SaveMenuListener());
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        frame.setBounds(300, 200, 400, 300);
        frame.setVisible(true);

        cardList = new ArrayList<QuizCard>();
    }

    public class NextCardListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            // add the current card to the list and clear the text area
            addCard();
            clearCard();
        }
    }

    public void addCard() {
        QuizCard card = new QuizCard(question.getText(), answer.getText());
        cardList.add(card);        
    }

    public void clearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }

    public class SaveMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            // bring up a file dialog box, let the user name and save the set
            addCard();
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }
    }

    public class NewMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            // clear out the card list and clear the text area
            clearCard();
            cardList.clear();
        }
    }

    private void saveFile(File file) {
        // iterate through the card list and write each one out to a text file
        // in a parseable way
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (QuizCard card:cardList) {
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
            writer.close();
        } catch(IOException ex) {
            System.out.println("couldn't write the cardlist out");
            ex.printStackTrace();
        }
    }
}

