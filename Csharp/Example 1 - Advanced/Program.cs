using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.Net.Sockets;
using System.Xml;

namespace Example_1_Advanced
{
    internal class Program
    {
        static int bufferLen = 8192;
        static void Main(string[] args)
        {
            TcpClient tcpClient = new TcpClient("127.0.0.1", 77);

            JObject jo = new JObject();
            jo["MSG"] = "QUOTE";
            jo["SYMBOL"] = "EURUSD";

            Byte[] data = System.Text.Encoding.ASCII.GetBytes(jo.ToString(Newtonsoft.Json.Formatting.None) + "\r\n");

            NetworkStream stream = tcpClient.GetStream();

            stream.Write(data, 0, data.Length);

            data = new Byte[bufferLen];

            String responseData = String.Empty;

            int bytes = stream.Read(data, 0, data.Length);

            responseData = System.Text.Encoding.ASCII.GetString(data, 0, bytes);

            JObject jresult = JsonConvert.DeserializeObject<JObject>(responseData);

            Console.WriteLine("Symbol: {0}  Ask: {1}  Bid: {2}  Time: {3}", jresult["SYMBOL"], jresult["ASK"], jresult["BID"], jresult["TIME"]);

            stream.Close();
            tcpClient.Close();
        }
    }
}