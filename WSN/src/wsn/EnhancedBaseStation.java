/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.*;
/**
 *
 * @author Bobby
 */
public class EnhancedBaseStation extends JFrame implements WindowListener,Runnable,ActionListener
{
    private String messages[]={"Compiled and Interpreter",
            "Platform Independent",
            "Object-Oriented",
            "Robust and Secure",
            "Distributed",
            "Simple Small and Familiar",
            "Multithreaded and Interactive",
            "Dynamic and Extensible Code",
            "Distributed",
            "Secure",
            "Architectural Neutral",
            "Portable",
            "Interpreted",
            "High performance"
            };
    
    private SSLServerSocketFactory sslserversocketfactory=null;
    private SSLServerSocket sslserversocket =null;
    private SSLSocket sslsocket =null;
    
    private Container con;
    
    private JButton muticast;
    
    private JPanel msgPanel=new JPanel();
    private JPanel optionPanel=new JPanel();
    
    private JPanel typingPanel=new JPanel();
    
    private JTextField msgText=new JTextField(20);
    private JButton msgAdd=new JButton("Add Message");
    
    
    DefaultListModel model=new DefaultListModel();
    private JList msgList=new JList(model);
    
    
    private JPanel clearPanel=new JPanel();
    private JButton clearBtn=new JButton("Clear List");
    
    DatagramSocket socket = null;
    DatagramPacket outPacket = null;
    byte[] outBuf=new byte[2000];
    final int PORT = 8888;
    
    public EnhancedBaseStation(String title)
    {
        super(title);
        
        
        msgPanel.setLayout(new BorderLayout());
         optionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
         typingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
         con=getContentPane();
         con.setLayout(new GridLayout(1,2));
         
         muticast=new JButton("Multicast");
         muticast.addActionListener(this);
        
         optionPanel.add(new JLabel("           "));
         optionPanel.add(muticast);
         msgPanel.setBackground(Color.yellow);
         typingPanel.add(msgText);
         typingPanel.add(msgAdd);
         msgPanel.add(typingPanel,BorderLayout.NORTH);
         msgPanel.add(new JScrollPane(msgList),BorderLayout.CENTER);
         clearPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
         clearPanel.add(clearBtn);
         msgPanel.add(clearPanel,BorderLayout.SOUTH);
         con.add(msgPanel);
         con.add(optionPanel);
         msgAdd.addActionListener(this);
         msgText.addActionListener(this);
         clearBtn.addActionListener(this);
         for(int i=0;i<messages.length;i++)
         {
         model.addElement(messages[i]);
         }
        
        
        
        
        this.setSize(800,200);
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
                final Dimension screenSize = toolkit.getScreenSize();
                final int x = (screenSize.width - this.getWidth()) / 2;
                final int y = (screenSize.height - this.getHeight()) / 2;
                this.setLocation(x, 60);
        this.setVisible(true);
        this.setResizable(false);
        
        sslserversocketfactory =(SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        try {
            sslserversocket =(SSLServerSocket) sslserversocketfactory.createServerSocket(9999);
        } catch (IOException ex) {
            Logger.getLogger(EnhancedBaseStation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Thread thisThread=new Thread(this);
        thisThread.start();
    
    }
    public void run()
    {
    try
    {
        while(true)
        {
            sslsocket = (SSLSocket) sslserversocket.accept();
            PrintWriter out=new PrintWriter(new OutputStreamWriter(sslsocket.getOutputStream()),true);
            out.println("Hello");
            
            /*EnhancedECDSA keyObject=new EnhancedECDSA();
            ObjectOutputStream out=new ObjectOutputStream(sslsocket.getOutputStream());
            out.flush();
            out.writeObject(keyObject);
            System.out.println("Key Distributed Successfully !");*/
        }
    
    }
    catch(Exception ex)
    {System.out.println(ex.getMessage());}
    
    }
    public static void main(String[] args)
    {
        EnhancedBaseStation ebs=new EnhancedBaseStation("Enhanced Base Station");
    
    }
    public void windowOpened(WindowEvent e)
    {}
    public void windowClosed(WindowEvent e)
    {}
    public void windowClosing(WindowEvent e)
    {dispose();}
    public void windowActivated(WindowEvent e)
    {}
    public void windowDeactivated(WindowEvent e)
    {}
    public void windowIconified(WindowEvent e)
    {}
    public void windowDeiconified(WindowEvent e)
    {}
    
    @Override
    public void actionPerformed(ActionEvent ae) {
    
    if(ae.getSource()==msgAdd || ae.getSource()==msgText)
        {
        if(!msgText.getText().trim().equals(""))
        {
            model.addElement(msgText.getText().trim());
            msgText.setText("");
        }
        }
        if(ae.getSource()==clearBtn)
        {
            model.removeAllElements();
        }
    
    if(ae.getSource()==muticast)
        {
        
        enhancedMulticast();
        
        
        
        }
    
    }
    
    public void clearSensorNodes()
    {   try {
            socket = new DatagramSocket();
            String msg=new String("CLEAR");
            outBuf = msg.getBytes();
        
        InetAddress address = InetAddress.getByName("224.2.2.3");
        
        outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
        socket.send(outPacket);
        } catch (Exception ex) {
           System.err.println(ex.getMessage()) ;
        }
    }
    public void enhancedMulticast()
    {clearSensorNodes();
    if(model.getSize()>0)
    {
        String[] messageQueue=new String[model.getSize()];
        for(int i=0;i<model.getSize();i++)
        {
        messageQueue[i]=model.getElementAt(i).toString();
        }
                StopWatch timer = new StopWatch();
                String outputMsg=ExtendedBlock.getEB0(messageQueue);
                
                timer.start();
                
                EnhancedECDSA baseStation=new EnhancedECDSA(true);
                
                baseStation.sign(outputMsg);
                
                
    try
    { 
        socket = new DatagramSocket();
        String signature="SIG::"+outputMsg.trim();
        //outBuf=signature.getBytes();
        DoubleEncription baseStationSecret=new DoubleEncription(true);
        outBuf=baseStationSecret.encript(signature.trim());
        timer.stop();
        PrintWriter fout=new PrintWriter(new FileWriter(new File("signin")));
        fout.print(timer.getElapsedTime());
        fout.close();
                System.out.println("TIME : "+timer.getElapsedTime());
        InetAddress address = InetAddress.getByName("224.2.2.3");
        outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
        socket.send(outPacket);
        System.out.println("\nSignature Sent Successfully !");
        //outBuf = baseStationSecret.encript("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz");
        //outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
        
        //socket.send(outPacket);
        for(int i=0;i<messageQueue.length;i++)
        {
        String msg=new String(messageQueue[i]);
        
        //outBuf = msg.getBytes();
        outBuf=baseStationSecret.encript(msg.trim());
        
        address = InetAddress.getByName("224.2.2.3");
        
        outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
        
        socket.send(outPacket);
        System.err.println(msg+" Sent Successfully!");
        }
       
    }
    catch(Exception ex)
    {System.err.println("Error : "+ex.getMessage());}
    }
    }
}
