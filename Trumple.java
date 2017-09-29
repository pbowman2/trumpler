package trumpler;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Trumple extends JFrame{
    public Trumple(){
        add(new Board());
        setResizable(false);
        pack();
        setTitle("Trumple");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
       EventQueue.invokeLater(new Runnable(){
           @Override
           public void run() {
               Trumple instance = new Trumple();
               instance.setVisible(true);
           }
       }); 
    }

}