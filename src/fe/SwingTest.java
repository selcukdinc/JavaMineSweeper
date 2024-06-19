package fe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SwingTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500, 500); // Frame boyutunu belirleyin
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kapatma işlemini belirleyin
        frame.setLocation(250, 250);
        frame.setVisible(true);
        frame.setTitle("Mine Sweeper");
        frame.setLayout(null);


        JButton btn = new JButton();
        btn.setSize(50,30);
        btn.setLocation(50,50);
        btn.setText("Buton");
        
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Buton Clicked");                
            }
        });

        

        frame.add(btn);
    }

}
