import javax.swing.*;
import java.awt.*;

public class Animation {

    int x = 70;
    int y = 70;

    public static void main(String[] args) {
        Animation gui = new Animation();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        MyDrawPanel panel = new MyDrawPanel();

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);

        for (int i = 0; i < 130; i++) {
            x++;
            y++;

            panel.repaint();

            try {
                Thread.sleep(10);
            } catch(Exception ex) {

            }
        }
    }

    class MyDrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Color.green);
            g.fillOval(x, y, 40, 40);
        }
    }

}