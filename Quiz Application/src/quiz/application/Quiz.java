package quiz.application;

import javax.swing.*;
import java.awt.*;

public class Quiz extends JFrame{
    
    Quiz(){
        setBounds(0,0,1400,800);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/login.jpeg"));
        JLabel image = new JLabel(i1);
        image.setBounds(0,0,1400,350);
        add(image);
        
        JLabel qno = new JLabel("1");
        qno.setBounds(50,400,50,30);
        qno.setFont(new Font("Tahoma", Font.PLAIN,24)); 
        add(qno);
        
        JLabel question = new JLabel("This is a question");
        question.setBounds(150,400,900,30);
        question.setFont(new Font("Tahoma", Font.PLAIN,24)); 
        add(question);
        
        setVisible(true);
    }
    
    public static void main(String args[]){
        new Quiz();
    }
}
