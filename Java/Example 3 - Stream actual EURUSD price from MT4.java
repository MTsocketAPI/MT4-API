// Example 3 - Stream actual EURUSD price from MT4
//
// PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
//
// WARNING: All these source codes are only examples for testing purposes. 
// WARNING: We don’t provide any guarantee or responsibility about it. 
// WARNING: Use these examples at your own risk.

import java.net.*;
import java.io.*;

/**
 *  $ javac streamPrices.java (Compile)
 *  $ java streamPrices (Run)
  **/

class streamPrices
{
    public static void main(String[] args)
    {
           try
           {
                Socket socket_cmd = new Socket("localhost", 77);
                Socket socket_data = new Socket("localhost", 78);

                OutputStream output_cmd = socket_cmd.getOutputStream();
                InputStream input_cmd = socket_cmd.getInputStream();

                InputStream input_data = socket_data.getInputStream();

                BufferedReader reader_cmd = new BufferedReader(new InputStreamReader(input_cmd));
                BufferedReader reader_data = new BufferedReader(new InputStreamReader(input_data));

                BufferedWriter writer_cmd = new BufferedWriter(new OutputStreamWriter(output_cmd));

                String jsonString;

                writer_cmd.write("{\"MSG\":\"TRACK_PRICES\",\"SYMBOLS\":[\"EURUSD\"]}\r\n");
                writer_cmd.flush();

                jsonString = reader_cmd.readLine();
                System.out.println(jsonString);

                while ((jsonString = reader_data.readLine()) != null) {
                        System.out.println(jsonString);
                }
           }
           catch (UnknownHostException ex) {
                System.out.println("Server not found: " + ex.getMessage());
           }
           catch (IOException ex) {
                System.out.println("I/O error: " + ex.getMessage());
           }
        }
}
