// Example 3 (Advanced) - Export Trade History from MT4 to CSV
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
 *  $ javac -cp .:json-simple-1.1.1.jar getHistory.java (Compile)
 *  $ java -cp .:json-simple-1.1.1.jar getHistory (Run)
 * */
class getHistory
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
                jo.put("MSG","TRADE_HISTORY");
                jo.put("FROM_DATE","2022/04/13 07:00:00");
                jo.put("TO_DATE","2022/04/15 00:00:00");

                writer.write(jo.toString() + "\r\n");
                writer.flush();

                jsonString = reader.readLine();
                System.out.println(jsonString);

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(jsonString);
                JSONArray jarr= (JSONArray)json.get("TRADES");

                FileWriter myFile = new FileWriter("tradeHistory.csv");

                myFile.write("OPEN_TIME,CLOSE_TIME,TICKET,SYMBOL,MAGIC,OPEN_PRICE,CLOSE_PRICE,TYPE,LOTS,STOP_LOSS,TAKE_PROFIT,SWAP,COMMISSION,COMMENT,PROFIT\r\n");

                for(int i = 0; i < jarr.size(); i++) {
                        JSONObject item = (JSONObject) jarr.get(i);

                        myFile.write(item.get("OPEN_TIME").toString() + "," +
                                item.get("CLOSE_TIME").toString() + "," +
                                item.get("TICKET").toString() + "," +
                                item.get("SYMBOL").toString() + "," +
                                item.get("MAGIC").toString() + "," +
                                item.get("OPEN_PRICE").toString() + "," +
                                item.get("CLOSE_PRICE").toString() + "," +
                                item.get("TYPE").toString() + "," +
                                item.get("LOTS").toString() + "," +
                                item.get("STOP_LOSS").toString() + "," +
                                item.get("TAKE_PROFIT").toString() + "," +
                                item.get("SWAP").toString() + "," +
                                item.get("COMMENT") + "," +
                                item.get("PROFIT").toString() +
                                "\r\n");
                }

                myFile.close();

                System.out.println("tradeHistory.csv created!");

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
