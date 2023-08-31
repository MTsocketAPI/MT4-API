// Example 1 (Advanced) - Get actual EURUSD price (using JSON library) from MT4
//
// PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
//
// WARNING: All these source codes are only examples for testing purposes. 
// WARNING: We don’t provide any guarantee or responsibility about it. 
// WARNING: Use these examples at your own risk.

import java.net.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 *  $ javac -cp .:json-simple-1.1.1.jar getQuoteJSON.java (Compile)
 *  $ java -cp .:json-simple-1.1.1.jar getQuoteJSON (Run)
 * */
class getQuoteJSON
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
                JSONObject jo = new JSONObject();
                
                jo.put("MSG","QUOTE");
                jo.put("SYMBOL","EURUSD");

                writer.write(jo.toString() + "\r\n");
                writer.flush();

                jsonString = reader.readLine();

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(jsonString);

                System.out.println("Time: " + json.get("TIME") + " Symbol: " + json.get("SYMBOL") + " Ask: " + json.get("ASK"));
           }
           catch(ParseException pe) {
               System.out.println("position: " + pe.getPosition());
               System.out.println(pe);
           }
           catch (UnknownHostException ex) {
                System.out.println("Server not found: " + ex.getMessage());
           }
           catch (IOException ex) {
                System.out.println("I/O error: " + ex.getMessage());
           }
        }
}


$Response = $Reader.ReadLine()
Write-Host $Response

$Stream.Close()
$Socket.Close()

