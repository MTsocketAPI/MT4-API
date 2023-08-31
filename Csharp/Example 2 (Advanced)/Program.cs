using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.Net.Sockets;
using System.Xml;

namespace Example_2_Advanced
{
    internal class Program
    {
        static int bufferLen = 8192;
        static void Main(string[] args)
        {
            TcpClient tcpClient_cmd = new TcpClient("127.0.0.1", 77);
            TcpClient tcpClient_data = new TcpClient("127.0.0.1", 78);

            JArray ja = new JArray();
            ja.Add("EURUSD");

            JObject jo = new JObject();
            jo["MSG"] = "TRACK_OHLC";
            jo["TIMEFRAME"] = "PERIOD_M1";
            jo["SYMBOLS"] = ja;

            Byte[] data = System.Text.Encoding.ASCII.GetBytes(jo.ToString(Newtonsoft.Json.Formatting.None) + "\r\n");

            NetworkStream stream_cmd = tcpClient_cmd.GetStream();
            NetworkStream stream_data = tcpClient_data.GetStream();

            stream_cmd.Write(data, 0, data.Length);

            data = new Byte[bufferLen];

            String responseData = String.Empty;

            int bytes = stream_cmd.Read(data, 0, data.Length);

            responseData = System.Text.Encoding.ASCII.GetString(data, 0, bytes);

            Console.WriteLine(responseData);

            do
            {
                bytes = stream_data.Read(data, 0, data.Length);
                responseData = System.Text.Encoding.ASCII.GetString(data, 0, bytes);
                JObject jresult = JsonConvert.DeserializeObject<JObject>(responseData);
                if (jresult["MSG"].ToString() == "TRACK_OHLC") 
                    Console.WriteLine("Time: {0}  Symbol: {1}  Open: {2}  Close: {3}  High: {4}  Low: {5}", jresult["OHLC"][0]["TIME"], jresult["SYMBOL"], jresult["OHLC"][0]["OPEN"], jresult["OHLC"][0]["CLOSE"], jresult["OHLC"][0]["HIGH"], jresult["OHLC"][0]["LOW"]);
            } while (true);

            stream_cmd.Close();
            stream_data.Close();

            tcpClient_cmd.Close();
            tcpClient_data.Close();
        }
    }
}