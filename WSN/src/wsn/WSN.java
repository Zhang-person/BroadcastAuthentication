/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Bobby
 */
public class WSN   extends JFrame implements WindowListener{

    public WSN(){}
    public WSN(String title)
    {
    super(title);
    setTitle(title);
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    int w=(int)width;
    int h=(int)height;
    setSize(w,h);
    this.setResizable(false);
    
    JMenu mnuManet = new JMenu("WSN");
    mnuManet.setMnemonic('W');
    JMenuItem mnuSimulations = new JMenuItem("Simulations");
    mnuSimulations.setMnemonic('S');
    mnuManet.add(mnuSimulations);
    
    JMenuItem mnuEnhanced= new JMenuItem("Enhancement");
    mnuEnhanced.setMnemonic('E');
    mnuManet.add(mnuEnhanced);
    
    JMenuItem mnuResults = new JMenuItem("Results");
    mnuResults.setMnemonic('R');
    mnuManet.add(mnuResults);	
    
    JMenuItem mnuExit = new JMenuItem("Exit");
    mnuExit.setMnemonic('x');
    mnuManet.add(mnuExit);
    
    mnuEnhanced.addActionListener(
            new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
                    try {
                        //EnhancedBaseStation ebs=new EnhancedBaseStation("Enhanced Base Station");
                        Runtime.getRuntime().exec("cmd /c start server.bat");
                        Runtime.getRuntime().exec("cmd /c start client1.bat");
                        Runtime.getRuntime().exec("cmd /c start client2.bat");
                        Runtime.getRuntime().exec("cmd /c start client3.bat");
                        Runtime.getRuntime().exec("cmd /c start client4.bat");
                    } catch (IOException ex) {
                        Logger.getLogger(WSN.class.getName()).log(Level.SEVERE, null, ex);
                    }
                                        
				}
			}
            );
    
    mnuSimulations.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					
                                    
                                    WSNTestBed wtb=new WSNTestBed();
                                        
				}
			}
		);
    
    
    mnuResults.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
				
                                if(SimulationStatitics.isECDSASimulated())
                                {
                                    if(SimulationStatitics.isHMACSimulated())
                                    {
                                        if(SimulationStatitics.isRSASimulated())
                                        {
                                  final SimulationResult demo = new SimulationResult("Simulation Result");
                                  demo.pack();
                                  RefineryUtilities.centerFrameOnScreen(demo);
                                  demo.setVisible(true);  
                                        }
                                    }
                                }
				}
			}
		);
    
    
    mnuExit.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					System.exit(0);
				}
			}
		);	
    
    JMenuBar bar = new JMenuBar();
    setJMenuBar(bar);
    bar.add(mnuManet);
    
    Container con=getContentPane();
    con.setBackground(Color.WHITE);
    ImageIcon imgThisImg = new ImageIcon("images/wsn.gif");
    JLabel l=new JLabel(imgThisImg);
    
    con.setLayout(new BorderLayout());
    con.add(l,BorderLayout.CENTER);
    setVisible(true);
    addWindowListener(this);
    setResizable(false);
    
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        WSN wsn=new WSN("WSN");
    }
    public void windowClosing(WindowEvent e)
    {
    System.exit(0);
    }
    public void windowClosed(WindowEvent e)
    {}
    public void windowOpened(WindowEvent e)
    {}
    public void windowActivated(WindowEvent e)
    {}
    public void windowDeactivated(WindowEvent e)
    {}
    public void windowIconified(WindowEvent e)
    {}
    public void windowDeiconified(WindowEvent e)
    {}
}
