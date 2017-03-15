/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Peter Burrows
 */
public class ResponseStatus 
{
    private boolean success;
    private String description;
    private String payload;

    public boolean getSuccess()
    {
        return this.success;
    }
    
    public void setSuccess(boolean bool)
    {
        this.success = bool;
    } 
    
    public String getDesc()
    {
        return this.description;
    }

    public void setDesc(String str)
    {
        this.description = str;
    }
    
    public String getPayload()
    {
        return this.payload;
    }

    public void setPayload(String str)
    {
        this.payload = str;
    }
}
