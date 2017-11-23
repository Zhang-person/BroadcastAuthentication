/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Bobby
 */
public class BaseStation extends JInternalFrame  implements ActionListener{
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
    private Container con;
    private JRadioButton hmac;
    private JRadioButton rsa;
    private JRadioButton ecc;
    private ButtonGroup group;
    private JButton muticast;
    
    private JPanel msgPanel=new JPanel();
    private JPanel optionPanel=new JPanel();
    
    private JPanel typingPanel=new JPanel();
    
    private JTextField msgText=new JTextField(35);
    private JButton msgAdd=new JButton("Add Message");
    
    
    DefaultListModel model=new DefaultListModel();
    private JList msgList=new JList(model);
    
    
    private JPanel clearPanel=new JPanel();
    private JButton clearBtn=new JButton("Clear List");
    
    DatagramSocket socket = null;
    DatagramPacket outPacket = null;
    byte[] outBuf;
    final int PORT = 8888;
    
 
    

    
    public BaseStation(String title)
    {
         super(title,false,false,false,false);
         setTitle(title);
         
         msgPanel.setLayout(new BorderLayout());
         optionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
         typingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
         con=getContentPane();
         con.setLayout(new GridLayout(1,2));
         
         muticast=new JButton("Multicast");
         muticast.addActionListener(this);
         hmac=new JRadioButton("HMAC",true);
         rsa=new JRadioButton("RSA");
         ecc=new JRadioButton("ECDSA");
         group=new ButtonGroup();
         group.add(hmac);
         group.add(rsa);
         group.add(ecc);
         optionPanel.add(hmac);
         optionPanel.add(rsa);
         optionPanel.add(ecc);
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
         setSize(825, 175);
         setVisible(true); 
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if(ae.getSource()==muticast)
        {
            if(hmac.isSelected())
            {
                HMACMulticast();
                SimulationStatitics.setHMACSimulationStatus(true);
            }
            else if(rsa.isSelected())
            {
                RSAMulticast();
                SimulationStatitics.setRSASimulationStatus(true);
            }
            else if(ecc.isSelected())
            {
                ECCMulticast();
                SimulationStatitics.setECDSASimulationStatus(true);
            }
        }
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
        }
    
    public void HMACMulticast()
    {clearSensorNodes();
        if(model.getSize()>0)
        {
            try {
                String[] messageQueue=new String[model.getSize()];
                for(int i=0;i<model.getSize();i++)
                {
                messageQueue[i]=model.getElementAt(i).toString();
                }
                socket = new DatagramSocket();
                for(int i=0;i<messageQueue.length;i++)
                {
                    
                    String msg=new String("HMAC::"+messageQueue[i]);
                    StopWatch timer = new StopWatch();
                    timer.start();
                    HMAC.init(msg);
                    timer.stop();
                    SimulationStatitics.setHMACSignMilliSeconds(timer.getElapsedTime());
                    System.out.println("HMAC(init): "+timer.getElapsedTime());
                    outBuf = msg.getBytes();
        
                    InetAddress address = InetAddress.getByName("224.2.2.3");
        
                    outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
                    socket.send(outPacket);
        Thread.sleep(50);
                }
            } catch (Exception ex) {
                Logger.getLogger(BaseStation.class.getName()).log(Level.SEVERE, null, ex);
            } 
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
    public void RSAMulticast()
    {clearSensorNodes();
        if(model.getSize()>0)
        {
        String[] messageQueue=new String[model.getSize()];
        for(int i=0;i<model.getSize();i++)
        {
        messageQueue[i]=model.getElementAt(i).toString();
        }
        
        try
        { 
        socket = new DatagramSocket();
        //String signature="SIG::"+outputMsg.trim();
        //outBuf=signature.getBytes();
        //InetAddress address = InetAddress.getByName("224.2.2.3");
        //outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
        //socket.send(outPacket);
        
        for(int i=0;i<messageQueue.length;i++)
        {
            StopWatch timer = new StopWatch();
            timer.start();
            RSA.init();
            //timer.stop();
            //System.out.println("RSA(init): "+timer.getElapsedTime());
        String msg=new String("RSA::"+messageQueue[i]);
        //timer.start();
        RSA.signIn(msg);
        timer.stop();
        SimulationStatitics.setRSASignMilliSeconds(timer.getElapsedTime());
        System.out.println("RSA(init): "+timer.getElapsedTime());
        outBuf = msg.getBytes();
        
        InetAddress address = InetAddress.getByName("224.2.2.3");
        
        outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
        socket.send(outPacket);
        
        }
       
    }
    catch(Exception ex)
    {System.err.println("Error : "+ex.getMessage());}
        
    }
    
    }
    
    
    
    
    
    public void ECCMulticast()
    {clearSensorNodes();
    if(model.getSize()>0)
    {
        String[] messageQueue=new String[model.getSize()];
        for(int i=0;i<model.getSize();i++)
        {
        messageQueue[i]=model.getElementAt(i).toString();
        }
        
                String outputMsg=ExtendedBlock.getEB0(messageQueue);
                StopWatch timer = new StopWatch();
                timer.start();
                ECDSA.init();
                //timer.stop();
                //System.out.println("ECC(init): "+timer.getElapsedTime());
                //timer.start();
                ECDSA.sign(outputMsg);
                timer.stop();
                SimulationStatitics.setECDSASignMilliSeconds(timer.getElapsedTime());
                System.out.println("ECC(init): "+timer.getElapsedTime());
                //System.out.println(outputMsg);
                //System.out.println(ECDSA.verify(outputMsg));
    try
    { 
        socket = new DatagramSocket();
        String signature="SIG::"+outputMsg.trim();
        outBuf=signature.getBytes();
        InetAddress address = InetAddress.getByName("224.2.2.3");
        outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
        socket.send(outPacket);
        
        for(int i=0;i<messageQueue.length;i++)
        {
        String msg=new String(messageQueue[i]);
        
        outBuf = msg.getBytes();
        
        address = InetAddress.getByName("224.2.2.3");
        
        outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
        socket.send(outPacket);
        }
       
    }
    catch(Exception ex)
    {System.err.println("Error : "+ex.getMessage());}
    }
    }
    }

