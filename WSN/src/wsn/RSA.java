/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bobby
 */
public class RSA {
  private static KeyPairGenerator kpg ;
  private static KeyPair keyPair;
  private static byte[] signatureBytes;
  private static Signature sig;
    public static void init()
    {
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);  
            keyPair = kpg.genKeyPair();
            sig = Signature.getInstance("MD5WithRSA");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void signIn(String strData)
    {
        try {
            byte[] data = strData.getBytes("UTF8");

            
            sig.initSign(keyPair.getPrivate());
            sig.update(data);
            signatureBytes = sig.sign();
            
        } catch (SignatureException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public static boolean verify(String strData)
    {   boolean flag=false;
        try {
            byte[] data = strData.getBytes("UTF8");
            sig.initVerify(keyPair.getPublic());
            sig.update(data);
            //System.out.println(new String(data));
            flag=sig.verify(signatureBytes);
            //System.out.println(sig.verify(signatureBytes));
        } catch (SignatureException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
}
