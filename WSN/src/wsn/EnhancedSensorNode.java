/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.net.ssl.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Bobby
 */
public class EnhancedSensorNode extends JFrame implements WindowListener ,Runnable{
    private SSLSocketFactory sslsocketfactory =null;
    private SSLSocket sslsocket =null;
    
    private Container con;
    private JTextArea txtMessage;
    MulticastSocket socket = null;
    DatagramPacket inPacket = null;
    byte[] inBuf = new byte[2000];
    
    public EnhancedSensorNode(String title, int nodeId)
    {
        super(title);
        
    con=getContentPane();
    con.setLayout(new BorderLayout());
    txtMessage=new JTextArea();
    txtMessage.setEditable(false);
    JScrollPane pane=new JScrollPane(txtMessage);
    con.add(pane,BorderLayout.CENTER);
    
        this.setSize(400,150);
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
                final Dimension screenSize = toolkit.getScreenSize();
                final int x = (screenSize.width - this.getWidth()) / 2;
                final int y = (screenSize.height - this.getHeight()) / 2;
                if(nodeId==1)
                {
                this.setLocation(280, 300);
                }
                else if(nodeId==2)
                {
                this.setLocation(700, 300);
                }
                else if(nodeId==3)
                {
                this.setLocation(280, 480);
                }
                else if(nodeId==4)
                {
                this.setLocation(700, 480);
                }
                this.setVisible(true);
                this.setResizable(false);
                
                sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try {
            sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", 9999);
        } catch (IOException ex) {
            Logger.getLogger(EnhancedSensorNode.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        EnhancedSensorNodeService esns=new EnhancedSensorNodeService(sslsocket);
        Thread serviceThread=new Thread(esns);
        serviceThread.start();
        Thread thisThread=new Thread(this);
        thisThread.start();
    
    }
    @Override
    public void run() {
        try {
        socket = new MulticastSocket(8888);
        InetAddress address = InetAddress.getByName("224.2.2.3");
        socket.joinGroup(address);
       while (true) {
           inBuf = new byte[2000];
            inPacket = new DatagramPacket(inBuf, inBuf.length);
            
            socket.receive(inPacket);
            
                StopWatch timer = new StopWatch();
                timer.start();
            DoubleEncription sensorNodeSecret=new DoubleEncription(false);
            timer.stop();
            long totTime=timer.getElapsedTimeSecs();
            //String msg = new String(inBuf, 0, inPacket.getLength());
            String msg=sensorNodeSecret.decript(inBuf).trim();
            System.out.println("From " + inPacket.getAddress() + " Msg : " + msg);
            if(msg.equals("CLEAR"))
            {txtMessage.setText("CLEAR");
            }
            else
            {
            
            if(msg.startsWith("SIG::"))
            {
            System.out.println("Signature Received : "+msg);
            String outputMsg=msg.substring(5);
            System.out.println("Output Message : "+outputMsg);
            
                
                
                timer.start();
                EnhancedECDSA sensorNode=new EnhancedECDSA(false);
                boolean flag=sensorNode.verify(outputMsg);
                             
                timer.stop();
                totTime+=timer.getElapsedTime();
                PrintWriter fout=new PrintWriter(new FileWriter(new File("verify")));
                fout.print(totTime);
                fout.close();
                //SimulationStatitics.setECDSAVerifyMilliSeconds(timer.getElapsedTime());
                System.out.println("ECC(verify): "+timer.getElapsedTime());
                if(flag)
                {
                   txtMessage.setText(""); 
                   txtMessage.append("EnhancedECDSA: AUTHENTICATED\n");
                }
                else
                {
                   txtMessage.setText(""); 
                   txtMessage.append("EnhancedECDSA: NOT AUTHENTICATED\n");
                }
            }
            else
            {
            txtMessage.append(msg);
            txtMessage.append("\n");
            }
            } 
           // }
      }
        } catch (Exception ex) {
      System.out.println("In Enhanced Sensoe Node : "+ex);
    }
    }
    public static void main(String[] args)
    {
        
       EnhancedSensorNode esn=new EnhancedSensorNode("Enhanced Sensor Node "+args[0],Integer.parseInt(args[0]));
         //EnhancedSensorNode esn=new EnhancedSensorNode("Enhanced Sensor Node 1",1);
    
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
    
}
