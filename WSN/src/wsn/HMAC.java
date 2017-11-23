/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author Bobby
 */
public class HMAC {
    
private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
private static String hmac; 
private static final String key="key";
private static String toHexString(byte[] bytes) {
Formatter formatter = new Formatter();
for (byte b : bytes) {
formatter.format("%02x", b);
}
 
return formatter.toString();
} 

public static void init(String data)
{
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            hmac=toHexString(mac.doFinal(data.getBytes()));
        } catch (InvalidKeyException ex) {
            Logger.getLogger(HMAC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HMAC.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public static String getHMAC()
{return hmac;}

public static String calculateHMAC(String data)
throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
{
SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
mac.init(signingKey);
return toHexString(mac.doFinal(data.getBytes()));
}

public static boolean verify(String s1,String s2)
{
    if(s1.trim().equals(s2.trim()))
        return true;
    else
        return false;
}
}
