// Example 2 (Advanced) - Stream OHLC data (using JSON library) from MT4
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
 *  $ javac -cp .:json-simple-1.1.1.jar getStreamJSON.java (Compile)
 *  $ java -cp .:json-simple-1.1.1.jar getStreamJSON (Run)
 * */
class getStreamJSON
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

                JSONArray ja = new JSONArray();

                ja.add("EURUSD");

                JSONObject jo = new JSONObject();

                jo.put("MSG","TRACK_OHLC");
                jo.put("SYMBOLS",ja);
                jo.put("TIMEFRAME","PERIOD_M1");

                //System.out.println(jo.toString());
                writer_cmd.write(jo.toString() + "\r\n");
                writer_cmd.flush();

                String jsonString;

                while ((jsonString = reader_data.readLine()) != null) {
                        //System.out.println(jsonString);
                        JSONParser parser = new JSONParser();
                        JSONObject json = (JSONObject) parser.parse(jsonString);
                        JSONArray jarr= (JSONArray)json.get("OHLC");
                        JSONObject jelem= (JSONObject)jarr.get(0);
                        System.out.println("Time: " + jelem.get("TIME") + " Symbol: " + json.get("SYMBOL") + " Open: " + jelem.get("OPEN") + " Close: " + jelem.get("CLOSE") + " High: " + jelem.get("HIGH") + " Low: " + jelem.get("LOW"));
                }
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


$Stream_cmd.Close()
$Socket_cmd.Close()

$Stream_data.Close()
$Socket_data.Close()


