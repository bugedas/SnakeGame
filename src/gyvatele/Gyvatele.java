/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gyvatele;

/**
 *
 * @author buged
 */
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import javafx.scene.text.Text;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.*;

public class Gyvatele extends JFrame {


    public Gyvatele() {
        
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
        
        setResizable(false);
        pack();
        
        setTitle("Snake");
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    
    public static void main(String[] args) {
        
        
         
        
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new Gyvatele();
            ex.setVisible(true);
             
        });
    }
}
