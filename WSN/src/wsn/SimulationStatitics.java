/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;
import java.io.*;
/**
 *
 * @author Bobby
 */
public class SimulationStatitics {

    public static long hmacSign=0;
    public static long hmacVerify=0;
    public static long rsaSign=0;
    public static long rsaVerify=0;
    public static long ecdsaSign=0;
    public static long ecdsaVerify=0;
    
    public static boolean hmacSimulated=false;
    public static boolean rsaSimulated=false;
    public static boolean ecdsaSimulated=false;
    
    public static void setHMACSimulationStatus(boolean newStatus)
    {hmacSimulated=newStatus;}
    
    public static void setRSASimulationStatus(boolean newStatus)
    {rsaSimulated=newStatus;}
    
    public static void setECDSASimulationStatus(boolean newStatus)
    {ecdsaSimulated=newStatus;}
    
    public static boolean isHMACSimulated()
    {return hmacSimulated;}
    
    public static boolean isRSASimulated()
    {return rsaSimulated;}
    
    public static boolean isECDSASimulated()
    {return ecdsaSimulated;}
    
    public static long getHMACSignMilliSeconds()
    {return hmacSign;}
    
    public static long getHMACVerifyMilliSeconds()
    {return hmacVerify;}
    
    public static long getRSASignMilliSeconds()
    {return rsaSign;}
    
    public static long getRSAVerifyMilliSeconds()
    {return rsaVerify;}
    
    public static long getECDSASignMilliSeconds()
    {return ecdsaSign;}
    
    public static long getECDSAVerifyMilliSeconds()
    {return ecdsaVerify;}
    
    public static void setHMACSignMilliSeconds(long newMilliSeconds)
    {
        if(newMilliSeconds>hmacSign)
        {hmacSign=newMilliSeconds;}
    }
    
    public static void setHMACVerifyMilliSeconds(long newMilliSeconds)
    {
        if(newMilliSeconds>hmacVerify)
        {hmacVerify=newMilliSeconds;}
    }
    
    
    public static void setRSASignMilliSeconds(long newMilliSeconds)
    {
        if(newMilliSeconds>rsaSign)
        {rsaSign=newMilliSeconds;}
    }
    
    public static void setRSAVerifyMilliSeconds(long newMilliSeconds)
    {
        if(newMilliSeconds>rsaVerify)
        {rsaVerify=newMilliSeconds;}
    }
    
    public static void setECDSASignMilliSeconds(long newMilliSeconds)
    {
        if(newMilliSeconds>ecdsaSign)
        {ecdsaSign=newMilliSeconds;}
    }
    
    public static void setECDSAVerifyMilliSeconds(long newMilliSeconds)
    {
        if(newMilliSeconds>ecdsaVerify)
        {ecdsaVerify=newMilliSeconds;}
    }
    public static long getEnhancedSignMilliSeconds()
    { long milli=0;
    try
    {
        BufferedReader fin=new BufferedReader(new FileReader("signin"));
        String str=fin.readLine();
        if(str!=null)
        {
            milli=new Long(str).longValue();
        }
        
    }catch(Exception ex)
    {
        System.out.println(ex.getMessage());
    
    }
    return milli;
    }
    
    public static long getEnhancedVerifyMilliSeconds()
    { long milli=0;
    try
    {
        BufferedReader fin=new BufferedReader(new FileReader("verify"));
        String str=fin.readLine();
        if(str!=null)
        {
            milli=new Long(str).longValue();
        }
        
    }catch(Exception ex)
    {
        System.out.println(ex.getMessage());
    
    }
    return milli;
    }
}
