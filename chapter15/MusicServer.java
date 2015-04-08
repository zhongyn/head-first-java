import java.io.*;
import java.net.*;
import java.util.*;

public class MusicServer {
    
    ArrayList clientOutputStreams;

    public static void main(String[] args) {
        MusicServer server = new MusicServer();
        server.go();
    }

    public void go() {
        clientOutputStreams = new ArrayList();
        try {
            ServerSocket serverSock = new ServerSocket(5000);

            while (true) {
                Socket clientSocket = serverSock.accept();
                ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }            
    }

    public class ClientHandler implements Runnable {
        ObjectInputStream reader;

        public ClientHandler(Socket clientSocket) {
            try {
                reader = new ObjectInputStream(clientSocket.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            Object message = null;
            Object sequence = null;
            try {
                while ((message = reader.readObject()) != null) {
                    sequence = reader.readObject();
                    System.out.println("read two objects");
                    tellEveryone(message, sequence);
                }                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void tellEveryone(Object message, Object sequence) {
        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext()) {
            try {
                ObjectOutputStream writer = (ObjectOutputStream)it.next();
                writer.writeObject(message);
                writer.writeObject(sequence);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}