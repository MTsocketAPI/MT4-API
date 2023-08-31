using System.Net.Sockets;

namespace Example_3
{
    internal class Program
    {
        static int bufferLen = 8192;
        static void Main(string[] args)
        {
            TcpClient tcpClient_cmd = new TcpClient("127.0.0.1", 77);
            TcpClient tcpClient_data = new TcpClient("127.0.0.1", 78);

            Byte[] data = System.Text.Encoding.ASCII.GetBytes("{\"MSG\":\"TRACK_PRICES\",\"SYMBOLS\":[\"EURUSD\"]}\r\n");

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
                Console.WriteLine(responseData);
            } while (true);

            stream_cmd.Close();
            stream_data.Close();

            tcpClient_cmd.Close();
            tcpClient_data.Close();
        }
    }
}