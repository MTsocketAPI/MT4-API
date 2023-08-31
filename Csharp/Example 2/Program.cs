using System.Net.Sockets;

namespace Example_2
{
    internal class Program
    {
        static int bufferLen = 8192;
        static void Main(string[] args)
        {
            TcpClient tcpClient = new TcpClient("127.0.0.1", 77);

            Byte[] data = System.Text.Encoding.ASCII.GetBytes("{\"MSG\":\"ORDER_SEND\",\"SYMBOL\":\"EURUSD\",\"VOLUME\":0.02,\"TYPE\":\"ORDER_TYPE_SELL\"}\r\n");

            NetworkStream stream = tcpClient.GetStream();

            stream.Write(data, 0, data.Length);

            data = new Byte[bufferLen];

            String responseData = String.Empty;

            int bytes = stream.Read(data, 0, data.Length);

            responseData = System.Text.Encoding.ASCII.GetString(data, 0, bytes);

            Console.WriteLine(responseData);

            stream.Close();
            tcpClient.Close();
        }
    }
}