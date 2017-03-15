/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import java.util.Map.Entry;
import org.eclipse.jetty.plus.servlet.ServletHandler;
import org.eclipse.jetty.server.Server;

/**
 *
 * @author Peter Burrows
 */
@WebServlet(name = "InstructionQueue", urlPatterns = {"/InstructionQueue"})
public class HttpHandler extends HttpServlet 
{
    static InstructionQueue instQueue;
    
    public static void main( String[] args ) throws Exception
    {
        System.out.println("Server initialising.");
        instQueue = new InstructionQueue();
        System.out.println("Queue Initialised successfully.");
        // Create a basic jetty server object that will listen on port 8080.
        // Note that if you set this to port 0 then a randomly available port
        // will be assigned that you can either look in the logs for the port,
        // or programmatically obtain it for use in test cases.
        Server server = new Server(9090);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        
        // Passing in the class for the Servlet allows jetty to instantiate an
        // instance of that Servlet and mount it on a given context path.

        // IMPORTANT:
        // This is a raw Servlet, not a Servlet that has been configured
        // through a web.xml @WebServlet annotation, or anything similar.
        handler.addServletWithMapping(QueueServlet.class, "/*");

        server.start();
        System.out.println("Servlet Started successfully.");        
        // The use of server.join() the will make the current thread join and
        // wait until the server is done executing.
        server.join();

    }

    @SuppressWarnings("serial")
    public static class QueueServlet extends HttpServlet
    {
        /**
         * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
         * methods.
         */
        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException 
        {
            response.setContentType("text/JSON;charset=UTF-8");

            System.out.println("Request Received");

            Gson gson = new Gson();

            try 
            {
                for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) 
                {
                    System.out.println("Parsing parameters");

                    String name  = entry.getKey();
                    String value = entry.getValue()[0];
                    
                    System.out.println("name: "+name+" value: "+value);

                    StringBuilder sb = new StringBuilder();
                    String s;
                    while ((s = request.getReader().readLine()) != null) 
                    {
                        sb.append(s);
                    }

                    System.out.println(sb.toString());
                    if(name.equals("receiveMessagePayload"))    //Inbound InstructionMessage into Queue
                    {
                        InstructionMessage inboundMsg = (InstructionMessage) gson.fromJson(value.toString(), InstructionMessage.class);
                        instQueue.receiveMessage(inboundMsg);

                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setSuccess(true);
                        responseStatus.setDesc("Instruction Message Received and Queued Successfully");
                        responseStatus.setPayload("N/A");
                        response.getOutputStream().print(gson.toJson(responseStatus));
                        response.getOutputStream().flush();

                        System.out.println("Instruction Message Received and Queued Successfully");

                        break;
                    }
                    else if(name.equals("getNextMessagePayload"))   //Outbound InstructionMessage from Queue
                    {
                        response.getOutputStream().print("getNextMessagePayload");
                        InstructionMessage outboundMsg = instQueue.processNextMessage();
                        
                        if(outboundMsg != null)
                        {
                        
                            String json = gson.toJson(outboundMsg);
                            System.out.println("json response: "+json);

                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setSuccess(true);
                            responseStatus.setDesc("Successfully retrieved next queued message");
                            responseStatus.setPayload(json);
                            response.getOutputStream().print(gson.toJson(responseStatus));
                            response.getOutputStream().flush();

                            break;
                        }
                        else
                        {
                            ResponseStatus responseStatus = new ResponseStatus();
                            responseStatus.setSuccess(false);
                            responseStatus.setDesc("No more queued messages to process");
                            response.getOutputStream().print(gson.toJson(responseStatus));
                            response.getOutputStream().flush();
                        }
                    }
                    else if(name.equals("getMessageCount"))
                    {
                        response.getOutputStream().print("getMessageCount");
                        int count = instQueue.getMessageCount();

                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setSuccess(true);
                        responseStatus.setDesc("Successfully retrieved queue size");
                        responseStatus.setPayload(Integer.toString(count));
                        response.getOutputStream().print(gson.toJson(responseStatus));
                        response.getOutputStream().flush();
                        break;
                    }
                    else //unrecongised url param
                    {
                        response.getOutputStream().print("Unrecognised URL");

                        ResponseStatus responseStatus = new ResponseStatus();
                        responseStatus.setSuccess(false);
                        responseStatus.setDesc("Unrecognised parameter supplied.");
                        response.getOutputStream().print(gson.toJson(responseStatus));
                        response.getOutputStream().flush();

                        System.out.println("Unrecognised parameter supplied.");
                        break;
                    }
                }

                System.out.println("Request Completed - success");
            }
            catch (Exception ex) 
            {
                ex.printStackTrace();
                response.getOutputStream().print("Encountered Exception: "+ex.toString());
                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setSuccess(false);
                responseStatus.setDesc(ex.getMessage());

                response.getOutputStream().print(gson.toJson(responseStatus));
                response.getOutputStream().flush();
                System.out.println("Request processed - failed");
            }
        }

        // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
        /**
         * Handles the HTTP <code>GET</code> method.
         */
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Handles the HTTP <code>POST</code> method.
         * @param request servlet request
         * @param response servlet response
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException if an I/O error occurs
         */
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo() 
        {
            return "Short description";
        }// </editor-fold>
    }
}
