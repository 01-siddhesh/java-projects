import java.net.*;
import java.io.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Client extends JFrame
{
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    //declaring component
    private JLabel heading = new JLabel("Client Area");
    private JTextArea messageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    private Font font = new Font("Roboto",Font.PLAIN,20);

    public Client()
    {
        try{
            System.out.println("sending request to server");
            socket = new Socket("192.168.0.168",7778);
            System.out.println("Connection done");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            createGUI();
            handleEvents();

            startReading();
            // startWriting();

        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    private void createGUI()
    {   //gui code
        this.setTitle("Client Messenger[END]");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //code for component
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);

        //heading.setIcon(new ImageIcon("download.png"));
        //heading.setHorizontalTextPosition(SwingConstants.CENTER);
        //heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        messageArea.setEditable(false);
        messageInput.setHorizontalAlignment(SwingConstants.CENTER);

        //setting frame layout
        this.setLayout(new BorderLayout());

        //adding components to frame
        this.add(heading,BorderLayout.NORTH);
        JScrollPane jScrollPane = new JScrollPane(messageArea);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);

        this.setVisible(true);    
    }

    private void handleEvents()
    {
        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                //throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                //System.out.println("key released "+e.getKeyCode());
                if(e.getKeyCode()==10)
                {
                    String contentToSend = messageInput.getText();
                    messageArea.append("Me: "+contentToSend+"\n");
                    out.println(contentToSend);
                    out.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();
                }

                //throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
            }
            
        });
    }

    public void startReading()
    {
        //thread
        Runnable r1=()->{
            System.out.println("reading started");
            try{
                while(true)
                {
                    String msg = br.readLine();
                    if(msg.equals("exit"))
                    {
                        System.out.println("server terminated the programe...");
                        JOptionPane.showMessageDialog(this, "Server terminated chat");
                        messageInput.setEnabled(false);
                        socket.close();
                        break;
                    }

                    //System.out.println("Server: "+msg);
                    messageArea.append("Server: "+msg+"\n");
                }
            }catch(Exception e){
                //e.printStackTrace();
                System.out.println("connection closed");
                
            }
            
        };

        new Thread(r1).start();
    }

    public void startWriting()
    {
        System.out.println("Writer started...");
        //thread
        Runnable r2=()->{
            try{
                while(!socket.isClosed())
                {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("exit"))
                    {
                        socket.close();
                        break;
                    }
                }
            }catch(Exception e){
                //e.printStackTrace();
                System.out.println("connection is closed");
            }
        };

        new Thread(r2).start();
    }


    
    public static void main(String args[])
    {
        System.out.println("this is client...");
        new Client();
    }
}
