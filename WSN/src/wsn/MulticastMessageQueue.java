/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;
import java.util.*;
/**
 *
 * @author Bobby
 */
public class MulticastMessageQueue {

    private ArrayList messageQueue=new ArrayList();

    public MulticastMessageQueue(String[] messages)
    {
    for(int i=0;i<messages.length;i++)
    {
    messageQueue.add(messages[i]);
    }
    }
    
    public void addMessage(String message)
    {
    messageQueue.add(message);
    }
    
    public int getSize()
    {
        return messageQueue.size();
    }
    public ArrayList getAllMessages()
    {
        return messageQueue;
    }
    public String getMessage(int index)
    {
    return messageQueue.get(index).toString();
    }
}
