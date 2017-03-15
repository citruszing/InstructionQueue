/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Peter Burrows
 */

import java.util.UUID;

public class InstructionMessage 
{
    
    private int instructionType;
    private int productCode;
    private int quantity;
    private int uom;
    private int timeStamp;
    
    public InstructionMessage()
    {
    
    }
    
    public InstructionMessage(int insType, int prodCode, int quant, int um , int time)
            throws InvalidMessageException
    {
        this.instructionType = insType;
        this.productCode     = prodCode;
        this.quantity        = quant;
        this.uom             = um;
        this.timeStamp       = time;
    }
    
    public void setInstructionType(int num) throws InvalidMessageException
    {
        if(num > 0 && num < 100)
        {
            this.instructionType = num;
        }
        else
        {
            System.out.println("The value of InstructionType is not between 0 and 100 non inclusive: "+num);
            throw new InvalidMessageException("Invalid InstructionType value "+ num);
        }
    }
 
    public int getInstructionType()
    {
        return this.instructionType;
    }
    
    public String getPriority()
    {
        int tmp = this.getInstructionType();
        String priority = "low";
        if(tmp < 11)
        {
            priority = "high";
            return priority;
        }
        else if(tmp < 91)
        {
            priority = "medium";
            return priority;
        }
        return priority;
    }
    
    
    public void setProductCode(int num) throws InvalidMessageException
    {
        if(num > 0 )
        {
            this.productCode = num;
        }
        else
        {
            System.out.println("The value of ProductCode is not greater than 0: "+num);
            throw new InvalidMessageException("Invalid ProductCode value "+ num);
        }
    }
    
    public int getProductCode()
    {
        return this.productCode;
    }
    
    
    public void setQuantity(int num) throws InvalidMessageException
    {
        if(num > 0 )
        {
            this.quantity = num;
        }
        else
        {
            System.out.println("The value of Quantity is not greater than 0: "+num);
            throw new InvalidMessageException("Invalid Quantity value "+ num);
        }
    }
    
    public int getQuantity()
    {
        return this.quantity;
    }
    
    
    public void setUOM(int num) throws InvalidMessageException
    {
        if(num >= 0 && num < 256 )
        {
            this.uom = num;
        }
        else
        {
            System.out.println("The value of UOM is not between 0 (inclusive) and 256 (non inclusive): "+num);
            throw new InvalidMessageException("Invalid UOM value "+ num);
        }
    }
    
    public int getUOM()
    {
        return this.uom;
    }
    
    
    public void setTimeStamp(int num) throws InvalidMessageException
    {
        if(num > 0 )
        {
            this.timeStamp = num;
        }
        else
        {
            System.out.println("The value of TimeStamp is not greater than 100: "+num);
            throw new InvalidMessageException("Invalid TimeStamp value "+ num);
        }
    }
    
    public int getTimeStamp()
    {
        return this.timeStamp;
    }
    
}//End of Class
