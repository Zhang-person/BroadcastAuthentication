/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bobby
 */
public class ECDSA {
    static KeyPairGenerator keyGen;
    static KeyPair pair ;
    static PrivateKey priv;
    static PublicKey pub;
    public static Signature dsa ;
    static byte[] realSig ;
    public static void init()
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
    public static void sign(String digest)
    {
        try {
            byte[] strByte = digest.getBytes("UTF-8");
                    dsa.update(strByte);
            
                    realSig = dsa.sign();
                    //String signature=new BigInteger(1, realSig).toString(16);
        } catch (SignatureException ex) {
            Logger.getLogger(ECDSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ECDSA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean verify(String digest)
    {   boolean flag=false;
        try {
            
            byte[] strByte = digest.getBytes("UTF-8");
            if(dsa==null)
            {System.out.println("DSA NULL");}
            if(pub==null)
            {System.out.println("PUB NULL");}
            dsa.initVerify(pub);
            System.out.println("Testing !");
                    dsa.update(strByte);

                    //System.out.println(dsa.verify(realSig));
                    flag=dsa.verify(realSig);
        } catch (SignatureException ex) {
            Logger.getLogger(ECDSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ECDSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ECDSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
}
