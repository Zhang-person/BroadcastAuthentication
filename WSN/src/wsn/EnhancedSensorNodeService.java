/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;
/**
 *
 * @author Bobby
 */
public class EnhancedSensorNodeService implements Runnable{
    private SSLSocket sslsocket =null;
    public EnhancedSensorNodeService(SSLSocket sslsocket)
    {
    this.sslsocket=sslsocket;
    }
    public void run()
    {
        
        try
        {
            while(true)
            {
            InputStream inputstream = sslsocket.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader); 
            String inputLine=bufferedreader.readLine();
            System.out.println("MESSAGE RECEIVED FROM BASESTATION : "+inputLine);
            System.out.println("SENSOR NODE AUTHENTICATED SUCCESFULLY");
               /* ObjectInputStream in=new ObjectInputStream(sslsocket.getInputStream());
                EnhancedECDSA keyObject=(EnhancedECDSA)in.readObject();
                System.out.println("Key Received From Server");
                if(keyObject==null)
                {System.out.println("Key Object NULL");}
                else
                {System.out.println("Key Object NOT NULL");}*/
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    
    }
    
}
