/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Peter Burrows
 */

import java.util.ArrayList;

public class InstructionQueue 
{   
    
    ArrayList<InstructionMessage> highPriority;
    ArrayList<InstructionMessage> medPriority;
    ArrayList<InstructionMessage> lowPriority;
    
    public InstructionQueue()
    {
        highPriority = new ArrayList<InstructionMessage>();
        medPriority  = new ArrayList<InstructionMessage>();
        lowPriority  = new ArrayList<InstructionMessage>();
    }
    
    public void receiveMessage(InstructionMessage msg) throws Exception
    {
        try
        {
            String priority = msg.getPriority();
            if(priority.equals("high"))
            {
                enqueueMessage(this.highPriority,msg);
            }
            else if(priority.equals("medium"))
            {
                 enqueueMessage(this.medPriority,msg);
            }
            else
            {
                 enqueueMessage(this.lowPriority,msg);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    

    public InstructionMessage processNextMessage()
    {
        InstructionMessage message = getNextMessage();
        if(message != null)
        {
            removeMessage(message);         //remove message from ArrayList queue from memory
            return message;
        }
        else
        {
            return null;
        }
    }
    
    public int getMessageCount()
    {
        int count = highPriority.size() + medPriority.size() + lowPriority.size();
        return count;
    }
    
    private void enqueueMessage(ArrayList<InstructionMessage> queue, InstructionMessage msg)
    {
        queue.add(msg);
    }
    
    private InstructionMessage getNextMessage()
    {
        InstructionMessage message;
        
        if(this.highPriority.size() > 0)
        {
            message = this.highPriority.get(0);
            return message;
        }
        else if(this.medPriority.size() > 0)
        {
            message = this.medPriority.get(0);
            return message;
        }
        else if(this.lowPriority.size() > 0)
        {
            message = this.lowPriority.get(0);
            return message;
        }
        else
        {
            System.out.println("All queues are empty");
        }
        return null;
    }
    
    private void removeMessage(InstructionMessage msg)
    {
  
        if(this.highPriority.contains(msg))
        {
            this.highPriority.remove(msg);
        }
        else if(this.medPriority.contains(msg))
        {
            this.highPriority.remove(msg);
        }
        else if(this.lowPriority.contains(msg))
        {
            this.lowPriority.remove(msg);
        }
        else
        {
            System.out.println("InstructionMessage cannot be located from memory to remove :$ ");
        }
    }

}
