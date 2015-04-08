import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    
    ArrayList clientOutputStreams;

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.go();
    }

    public void go() {
        clientOutputStreams = new ArrayList();
        try {
            ServerSocket serverSock = new ServerSocket(5000);

            while (true) {
                Socket clientSocket = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }            
    }

    public class ClientHandler implements Runnable {
        BufferedReader reader;

        public ClientHandler(Socket clientSocket) {
            try {
                InputStreamReader stream = new InputStreamReader(clientSocket.getInputStream());
                reader = new BufferedReader(stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("read" + message);
                    tellEveryone(message);
                }                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter)it.next();
                writer.println(message);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}