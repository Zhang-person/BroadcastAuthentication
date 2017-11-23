/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;

/**
 *
 * @author Bobby
 */
public class ExtendedBlock {
    
    
    public static String getEB0(String messages[])
    {
            GeneralHashFunctionLibrary hash=new GeneralHashFunctionLibrary();
            MulticastMessageQueue mmq=new MulticastMessageQueue(messages);
            String []msg=new String[mmq.getSize()];
            int j=0;
            //for(int i=mmq.getSize()-1;i>=0;i--)
            for(int i=0;i<mmq.getSize();i++)
            {
               msg[j]=mmq.getMessage(i);
               j++;
            }

            int maxIndex=msg.length/3;
            if((msg.length%3) > 0)
            {maxIndex+=1;}
            
            String outputMsg="";
            String [] msgBlock=new String[maxIndex];
            
            for(int a=0; a<maxIndex; a++)
            {
                int startIndex=a * 3;
                
                int endIndex = 0;
                
                if(msg.length > (startIndex+3))
                {endIndex = startIndex+3;}
                else
                {endIndex = msg.length;}
                
                String rslt="";
                
                for(int b=startIndex; b<endIndex; b++)
                {
                    if(rslt.isEmpty())
                    {rslt=msg[b].toString();}
                    else
                    {
                        rslt=rslt.concat(", ");
                        rslt=rslt.concat(msg[b].toString());
                    }                    
                }                
                msgBlock[a]=rslt;
            }
            
            for(int a=(maxIndex-1);a>=0;a--)
            {
                String blockMsg=msgBlock[a].toString().trim();
                if(a==(maxIndex - 1))
                {
                    outputMsg=String.valueOf(hash.JSHash(blockMsg+RandomString.getRandomString()));
                }
                else
                {
                    outputMsg=String.valueOf(hash.JSHash(blockMsg+outputMsg));
                }
            }
            
    return outputMsg;
    }
    
}
