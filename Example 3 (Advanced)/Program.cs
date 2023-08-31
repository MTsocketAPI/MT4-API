using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.Net.Sockets;
using System.Xml;

namespace Example_3_Advanced
{
    internal class Program
    {
        static int bufferLen = 8192;
        static void Main(string[] args)
        {
            TcpClient tcpClient = new TcpClient("127.0.0.1", 77);

            JObject jo = new JObject();
            jo["MSG"] = "TRADE_HISTORY";
            jo["FROM_DATE"] = "2023/08/23 00:00:00";
            jo["TO_DATE"] = "2023/08/24 13:00:00";

            Byte[] data = System.Text.Encoding.ASCII.GetBytes(jo.ToString(Newtonsoft.Json.Formatting.None) + "\r\n");

            NetworkStream stream = tcpClient.GetStream();

            stream.Write(data, 0, data.Length);

            data = new Byte[bufferLen];

            String responseData = String.Empty;

            int bytes = stream.Read(data, 0, data.Length);

            responseData = System.Text.Encoding.ASCII.GetString(data, 0, bytes);

            JObject jresult = JsonConvert.DeserializeObject<JObject>(responseData);

            System.IO.File.AppendAllText("tradeHistory.csv", "OPEN_TIME,CLOSE_TIME,TICKET,SYMBOL,MAGIC,OPEN_PRICE,CLOSE_PRICE,TYPE,LOTS,STOP_LOSS,TAKE_PROFIT,SWAP,COMMISSION,COMMENT,PROFIT" + Environment.NewLine);

            foreach (var item in jresult["TRADES"])
            {
                System.IO.File.AppendAllText("tradeHistory.csv",
                    item["OPEN_TIME"].ToString() + "," +
                    item["CLOSE_TIME"].ToString() + "," +
                    item["TICKET"].ToString() + "," +
                    item["SYMBOL"].ToString() + "," +
                    item["MAGIC"].ToString() + "," +
                    item["PRICE_CLOSE"].ToString() + "," +
                    item["PRICE_OPEN"].ToString() + "," +
                    item["TYPE"].ToString() + "," +
                    item["LOTS"].ToString() + "," +
                    item["STOP_LOSS"].ToString() + "," +
                    item["TAKE_PROFIT"].ToString() + "," +
                    item["SWAP"].ToString() + "," +
                    item["COMMISSION"].ToString() + "," +
                    item["COMMENT"].ToString() + "," +
                    item["PROFIT"].ToString() +
                    Environment.NewLine);
            }

            Console.WriteLine("Finished!");

            stream.Close();
            tcpClient.Close();
        }
    }
}