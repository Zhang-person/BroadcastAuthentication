/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;
import ecc.*;
import ecc.elliptic.ECCryptoSystem;
import ecc.elliptic.ECKey;
import ecc.elliptic.EllipticCurve;
import ecc.elliptic.InsecureCurveException;
import ecc.elliptic.secp256r1;
import ecc.io.*;
import java.util.*;
/**
 *
 * @author Bobby
 */
public class ECC {
    static Key sk;
    static Key pk;
    static EllipticCurve ec ;
    static CryptoSystem cs ;
    public static void init()
    {
            try{
            ec = new EllipticCurve(new secp256r1());

	    cs= new ECCryptoSystem(ec);

	    sk = (ECKey)cs.generateKey();
	    pk = sk.getPublic();
            } catch (InsecureCurveException e) {
	    System.out.println("TestCryptoStreams: "+e);
	}
    }
    
    public static Key getPrivateKey()
    {return sk;}
    public static Key getPublicKey()
    {return pk;}
    
    public static byte[] encrypt(byte[] plain, int numbytes, Key ek)
    {
     byte[] sipherText = cs.encrypt(plain, numbytes, ek);
     return sipherText;
    }
    
    public static byte[] decrypt(byte[] cipher, Key dk)
    {
    byte[] plainText = cs.decrypt(cipher, dk);
    return plainText;
    }
}
