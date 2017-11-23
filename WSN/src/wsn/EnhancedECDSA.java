/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;
import java.io.*;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Bobby
 */
public class EnhancedECDSA implements Serializable{
    private KeyPairGenerator keyGen;
    private KeyPair pair ;
    private PrivateKey priv;
    private PublicKey pub;
    private Signature dsa ;
    private byte[] realSig ;  
    
    public EnhancedECDSA(boolean isBaseStation)
    {
        if(isBaseStation)
        {
        try {
                        keyGen = KeyPairGenerator.getInstance("EC");
                        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

                        keyGen.initialize(256, random);

                        pair = keyGen.generateKeyPair();
                        priv = pair.getPrivate();
                        pub = pair.getPublic();
                        
                        dsa = Signature.getInstance("SHA1withECDSA");

                        dsa.initSign(priv);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ECDSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ECDSA.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    }
    public  void sign(String digest)
    {
        try {
            byte[] strByte = digest.getBytes("UTF-8");
                    dsa.update(strByte);
            
                    realSig = dsa.sign();
                    
                    /* Save the signature in a file */
            FileOutputStream sigfos = new FileOutputStream("sig");
            sigfos.write(realSig);
            sigfos.close();
            
            /* Save the public key in a file */
            byte[] key = pub.getEncoded();
            FileOutputStream keyfos = new FileOutputStream("suepk");
            keyfos.write(key);
            keyfos.close();
            
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } 
    }
    
    public  boolean verify(String digest)
    {   boolean flag=false;
        try {
            
            /* import encoded public key */

            FileInputStream keyfis = new FileInputStream("suepk");
            byte[] encKey = new byte[keyfis.available()];  
            keyfis.read(encKey);

            keyfis.close();

            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            
            
            /* input the signature bytes */
            FileInputStream sigfis = new FileInputStream("sig");
            byte[] sigToVerify = new byte[sigfis.available()]; 
            sigfis.read(sigToVerify );
            sigfis.close();
            
            /* create a Signature object and initialize it with the public key */
            Signature sig = Signature.getInstance("SHA1withECDSA");
            
            byte[] strByte = digest.getBytes("UTF-8");
            
            sig.initVerify(pubKey);
            
                    sig.update(strByte);

                    
                    flag=sig.verify(sigToVerify);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } 
        return flag;
    }
    
    public static void main(String[] args)
    {
        EnhancedECDSA baseStation=new EnhancedECDSA(true);
        baseStation.sign("Bobby Thomas");
        
        EnhancedECDSA sensorNode=new EnhancedECDSA(false);
        
        System.err.println("Varification : "+sensorNode.verify("Bobby Thomas"));
    
    }
}
