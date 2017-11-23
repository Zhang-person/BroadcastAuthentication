/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author Bobby
 */
public class WSNTestBed  extends JFrame implements WindowListener{
    
    
    public WSNTestBed()
    {
    super("WSN TestBed");
    this.setResizable(false);
    WindowUtilities.setNativeLookAndFeel();
    Container content = getContentPane();
    content.setBackground(Color.white);
    JDesktopPane desktop = new JDesktopPane();
    desktop.setBackground(Color.white);
    content.add(desktop, BorderLayout.CENTER);
    setSize(836, 685);
    
    
    
    final Toolkit toolkit = Toolkit.getDefaultToolkit();
    final Dimension screenSize = toolkit.getScreenSize();
    final int x = (screenSize.width - this.getWidth()) / 2;
    final int y = (screenSize.height - this.getHeight()) / 2;
    this.setLocation(x, y);
    
    BaseStation bs=new BaseStation("Base Station");
    bs.setLocation(0, 0);
    desktop.add(bs);
    
    SensorNode sensorNode1=new SensorNode("Sensor Node1");
    sensorNode1.setLocation(0, 180);
    desktop.add(sensorNode1);
    
    SensorNode sensorNode2=new SensorNode("Sensor Node2");
    sensorNode2.setLocation(210, 180);
    desktop.add(sensorNode2);
    
    SensorNode sensorNode3=new SensorNode("Sensor Node3");
    sensorNode3.setLocation(420, 180);
    desktop.add(sensorNode3);
    
    SensorNode sensorNode4=new SensorNode("Sensor Node4");
    sensorNode4.setLocation(630, 180);
    desktop.add(sensorNode4);
    
    SensorNode sensorNode5=new SensorNode("Sensor Node5");
    sensorNode5.setLocation(0, 340);
    desktop.add(sensorNode5);
    
    SensorNode sensorNode6=new SensorNode("Sensor Node6");
    sensorNode6.setLocation(210, 340);
    desktop.add(sensorNode6);
    
    SensorNode sensorNode7=new SensorNode("Sensor Node7");
    sensorNode7.setLocation(420, 340);
    desktop.add(sensorNode7);
    
    SensorNode sensorNode8=new SensorNode("Sensor Node8");
    sensorNode8.setLocation(630, 340);
    desktop.add(sensorNode8);
    
    SensorNode sensorNode9=new SensorNode("Sensor Node9");
    sensorNode9.setLocation(0, 500);
    desktop.add(sensorNode9);
    
    SensorNode sensorNode10=new SensorNode("Sensor Node10");
    sensorNode10.setLocation(210, 500);
    desktop.add(sensorNode10);
    
    SensorNode sensorNode11=new SensorNode("Sensor Node11");
    sensorNode11.setLocation(420, 500);
    desktop.add(sensorNode11);
    
    SensorNode sensorNode12=new SensorNode("Sensor Node12");
    sensorNode12.setLocation(630, 500);
    desktop.add(sensorNode12);
    
    Thread sNode1=new Thread(sensorNode1);
    Thread sNode2=new Thread(sensorNode2);
    Thread sNode3=new Thread(sensorNode3);
    Thread sNode4=new Thread(sensorNode4);
    Thread sNode5=new Thread(sensorNode5);
    Thread sNode6=new Thread(sensorNode6);
    Thread sNode7=new Thread(sensorNode7);
    Thread sNode8=new Thread(sensorNode8);
    Thread sNode9=new Thread(sensorNode9);
    Thread sNode10=new Thread(sensorNode10);
    Thread sNode11=new Thread(sensorNode11);
    Thread sNode12=new Thread(sensorNode12);
    
    sNode1.start();
    sNode2.start();
    sNode3.start();
    sNode4.start();
    sNode5.start();
    sNode6.start();
    sNode7.start();
    sNode8.start();
    sNode9.start();
    sNode10.start();
    sNode11.start();
    sNode12.start();
    
    
    setVisible(true);
    }
    public static void main(String[] args)
    {
    WSNTestBed wtb=new WSNTestBed();
    }
    public void windowClosing(WindowEvent e)
    {
    dispose();
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
