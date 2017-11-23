/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Bobby
 */
public class WSNSplash   extends JFrame{
 
    public WSNSplash()
    {
    ImageIcon imgThisImg = new ImageIcon("images/sensor.jpg");
		JLabel label = new JLabel(imgThisImg);
		add(label);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(719,468);
                
                final Toolkit toolkit = Toolkit.getDefaultToolkit();
                final Dimension screenSize = toolkit.getScreenSize();
                final int x = (screenSize.width - this.getWidth()) / 2;
                final int y = (screenSize.height - this.getHeight()) / 2;
                this.setLocation(x, y);
		setVisible(true);
                
                try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(WSNSplash.class.getName()).log(Level.SEVERE, null, ex);
        }
        
           
         WSN wsn=new  WSN("WSN");
         dispose();       
    
    }
    
    public static void main(String[] args)
    {
    WSNSplash wsnSplash=new WSNSplash();
    }
}
