// Example 2 - Send Order To MT4
//
// PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
//
// WARNING: All these source codes are only examples for testing purposes. 
// WARNING: We don’t provide any guarantee or responsibility about it. 
// WARNING: Use these examples at your own risk.


import java.net.*;
import java.io.*;

/**
 *  $ javac sendOrder.java (Compile)
 *  $ java sendOrder (Run)  
 **/

class sendOrder
{
    public static void main(String[] args)
    {
           try
           {
                Socket socket = new Socket("localhost", 77);

                OutputStream output = socket.getOutputStream();
                InputStream input = socket.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

                String jsonString;

                writer.write("{\"MSG\":\"ORDER_SEND\",\"SYMBOL\":\"EURUSD\",\"VOLUME\":0.02,\"TYPE\":\"ORDER_TYPE_BUY\"}\r\n");
                writer.flush();

                jsonString = reader.readLine();
                System.out.println(jsonString);

           }
           catch (UnknownHostException ex) {
                System.out.println("Server not found: " + ex.getMessage());
           }
           catch (IOException ex) {
                System.out.println("I/O error: " + ex.getMessage());
           }
        }
}

