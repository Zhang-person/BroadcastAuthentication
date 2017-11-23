/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
import javax.crypto.spec.*;
import org.apache.commons.codec.binary.Base64;
/**
 *
 * @author Bobby
 */
public class DoubleEncription {
    
    private int keySize=56;
    private String output="sKey";
    private String algorithm="Blowfish";
    private KeyGenerator kg=null;
    private SecretKey ky=null;
    
    public DoubleEncription(boolean isBaseStation)
    {
        if(isBaseStation)
        {
        try {
            kg = KeyGenerator.getInstance(algorithm);
            kg.init(keySize);
            ky = kg.generateKey();
           
            /*System.out.println();
            System.out.println("KeyGenerator Object Info: ");
            System.out.println("Algorithm = "+kg.getAlgorithm());
            System.out.println("Provider = "+kg.getProvider());
            System.out.println("Key Size = "+keySize);
            System.out.println("toString = "+kg.toString());*/
             writeKey();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        }
    }
    
    public byte[] encript(String inputText)
    {   byte[] encodedBytes=inputText.getBytes();
        try
        {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, ky);
           
            byte[] firstEncrypt = cipher.doFinal(inputText.getBytes());
            //System.out.println("blowfish encryption...firstEncrypted::"+new String(firstEncrypt));
            encodedBytes = Base64.encodeBase64(firstEncrypt);
            //System.out.println("Base64 decoded encryption: encodedBytes::"+new String(encodedBytes));
        }catch(Exception ex)
        {System.out.println(ex.getMessage());}   
     
        
    return encodedBytes;
   
    }
    
    public String decript(byte[] encodedBytes)
    {
        byte[] decryptedFinal=encodedBytes;
        byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
        //System.out.println("Base64 decryptions........decodedBytes:: " +new String(decodedBytes));
        try
        {
        
        Cipher cipher = Cipher.getInstance("Blowfish");
        readKey();
        cipher.init(Cipher.DECRYPT_MODE, ky);   
        decryptedFinal = cipher.doFinal(decodedBytes);
        }catch(Exception ex)
        {
        System.out.println(ex.getMessage());
        }
   
        //System.out.println("Finally...blowfish decryption....decryptedFinal::"+new String(decryptedFinal));
        return new String(decryptedFinal);
        
    }
    
    private void writeKey()
    {
        String fl = output+".key";
      try
      {
      FileOutputStream fos = new FileOutputStream(fl);
      byte[] kb = ky.getEncoded();
      fos.write(kb);
      fos.close();
      }catch(Exception ex)
      {System.out.println(ex.getMessage());}
      /*System.out.println();
      System.out.println("SecretKey Object Info: ");
      System.out.println("Algorithm = "+ky.getAlgorithm());
      System.out.println("Saved File = "+fl);
      System.out.println("Format = "+ky.getFormat());
      System.out.println("toString = "+ky.toString());*/
    }
    
    private void readKey() 
      throws Exception {
      String fl = output+".key";
      FileInputStream fis = new FileInputStream(fl);
      int kl = fis.available();
      byte[] kb = new byte[kl];
      fis.read(kb);
      fis.close();
      KeySpec ks = null;
      SecretKey ky = null;
      SecretKeyFactory kf = null;
      
      ks = new SecretKeySpec(kb,algorithm);
      ky = new SecretKeySpec(kb,algorithm);
      this.ky=ky;

     /* System.out.println();
      System.out.println("KeySpec Object Info: ");
      System.out.println("Saved File = "+fl);
      System.out.println("Length = "+kb.length);
      System.out.println("toString = "+ks.toString());

      System.out.println();
      System.out.println("SecretKey Object Info: ");
      System.out.println("Algorithm = "+ky.getAlgorithm());
      System.out.println("toString = "+ky.toString());*/
   }
    
    public static void main(String[] args)
    {
        try
        {
        DoubleEncription baseStation=new DoubleEncription(true);
        DoubleEncription sensorNode=new DoubleEncription(false);
        byte[] encodedBytes =baseStation.encript("Bobby");
        System.out.println("Original String : "+sensorNode.decript(encodedBytes));
        }catch(Exception ex)
        {System.out.println(ex.getMessage());}
    }
    
}
