/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
/**
 *
 * @author Bobby
 */
public class SensorNode  extends JInternalFrame implements Runnable{
 
    private Container con;
    private JTextArea txtMessage;
    MulticastSocket socket = null;
    DatagramPacket inPacket = null;
    byte[] inBuf = new byte[2000];
public SensorNode(String title)
{
    super(title,false,false,false,false);
    setTitle(title);
    con=getContentPane();
    con.setLayout(new BorderLayout());
    txtMessage=new JTextArea();
    txtMessage.setEditable(false);
    JScrollPane pane=new JScrollPane(txtMessage);
    con.add(pane,BorderLayout.CENTER);
    setSize(200, 150);
    setVisible(true);
}

    @Override
    public void run() {
        try {
        socket = new MulticastSocket(8888);
        InetAddress address = InetAddress.getByName("224.2.2.3");
        socket.joinGroup(address);
       while (true) {
            inPacket = new DatagramPacket(inBuf, inBuf.length);
            socket.receive(inPacket);
            
            //System.out.println("Cipher : "+inBuf);
            //inBuf=ECC.decrypt(inBuf, ECC.getPrivateKey());
            String msg = new String(inBuf, 0, inPacket.getLength());
            //System.out.println("From " + inPacket.getAddress() + " Msg : " + msg);
            if(msg.equals("CLEAR"))
            {txtMessage.setText("");
            }
            else
            {
            if(msg.startsWith("RSA::"))
            {   StopWatch timer = new StopWatch();
                timer.start();
                boolean flag=RSA.verify(msg);
                timer.stop();
                SimulationStatitics.setRSAVerifyMilliSeconds(timer.getElapsedTime());
                System.out.println("RSA(verify): "+timer.getElapsedTime());
                if(flag)
                {   txtMessage.append("RSA: AUTHENTICATED\n");
                    txtMessage.append(msg.substring(5));
                    txtMessage.append("\n");}
                else
                {   
                    txtMessage.append("RSA: NOT AUTHENTICATED\n");
                    txtMessage.append(msg.substring(5));
                    txtMessage.append("\n");
                }
            }
            else if(msg.startsWith("HMAC::"))
            {
                StopWatch timer = new StopWatch();
                timer.start();
                String hmac=HMAC.calculateHMAC(msg);
                boolean flag=HMAC.verify(hmac, HMAC.getHMAC());
                timer.stop();
                SimulationStatitics.setHMACVerifyMilliSeconds(timer.getElapsedTime());
                System.out.println("HMAC(verify): "+timer.getElapsedTime());
                if(flag)
                {
                    txtMessage.append("HMAC: AUTHENTICATED\n");
                    txtMessage.append(msg.substring(6));
                    txtMessage.append("\n");
                
                }
                else
                {
                    txtMessage.append("HMAC: NOT AUTHENTICATED\n");
                    txtMessage.append(msg.substring(6));
                    txtMessage.append("\n");
                
                }
            
            }
            else
            {
            if(msg.startsWith("SIG::"))
            {
            String outputMsg=msg.substring(5);
            //System.out.println(outputMsg);
                StopWatch timer = new StopWatch();
                timer.start();
                boolean flag=ECDSA.verify(outputMsg);
                timer.stop();
                SimulationStatitics.setECDSAVerifyMilliSeconds(timer.getElapsedTime());
                System.out.println("ECC(verify): "+timer.getElapsedTime());
                if(flag)
                {
                   txtMessage.setText(""); 
                   txtMessage.append("ECDSA: AUTHENTICATED\n");
                }
                else
                {
                   txtMessage.setText(""); 
                   txtMessage.append("ECDSA: NOT AUTHENTICATED\n");
                }
            }
            else
            {
            txtMessage.append(msg);
            txtMessage.append("\n");
            }
            } 
            }
      }
        } catch (Exception ex) {
      System.out.println("In Sensoe Node : "+ex);
    }
    }
}
