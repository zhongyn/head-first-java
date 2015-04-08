import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;

public class ChatClient {
    
    private Socket sock;
    private JTextArea incoming;
    private JTextField outgoing;
    private PrintWriter writer;
    private BufferedReader reader;

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.go();
    }

    public void go() {
        // build and display gui
        JFrame frame = new JFrame("Chat Client");
        JPanel panel = new JPanel();
        frame.getContentPane().add(BorderLayout.CENTER, panel);

        incoming = new JTextArea(10, 20);
        JScrollPane incomingScroller = new JScrollPane(incoming);
        incoming.setLineWrap(true);
        incoming.setEditable(false);
        incomingScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        incomingScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(incomingScroller);

        outgoing = new JTextField(20);
        panel.add(outgoing);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        panel.add(sendButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500, 300, 300, 300);
        frame.setVisible(true);

        setUpNetworking();

        Thread getMessageThread = new Thread(new IncomingReader());
        getMessageThread.start();
    }

    public class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // send message to the server
            sendMessage();
        }
    }

    public void setUpNetworking() {
        try {
            sock = new Socket("127.0.0.1", 5000);
            writer = new PrintWriter(sock.getOutputStream());
            InputStreamReader stream = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(stream);
            System.out.println("networking established");
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendMessage() {
        try {
            writer.println(outgoing.getText());
            writer.flush();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        outgoing.setText("");
        outgoing.requestFocus();
    }

    public class IncomingReader implements Runnable {
        // read messages from server
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("read" + message);
                    incoming.append(message + "\n");
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}



