# Example 2 (Advanced) - Stream OHLC data (using JSON library) from MT4
#
# PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
#
# WARNING: All these source codes are only examples for testing purposes. 
# WARNING: We don’t provide any guarantee or responsibility about it. 
# WARNING: Use these examples at your own risk.

$IP = [System.Net.Dns]::GetHostAddresses("127.0.0.1")
$Port_cmd = 77
$Port_data = 78

$Address = [System.Net.IPAddress]::Parse($IP)
$Socket_cmd = New-Object System.Net.Sockets.TCPClient($Address,$Port_cmd)
$Socket_data = New-Object System.Net.Sockets.TCPClient($Address,$Port_data)

$Stream_cmd = $Socket_cmd.GetStream()
$Stream_data = $Socket_data.GetStream()

$Writer_cmd = New-Object System.IO.StreamWriter($Stream_cmd)
$Reader_cmd = New-Object System.IO.StreamReader($Stream_cmd)

$Writer_data = New-Object System.IO.StreamWriter($Stream_data)
$Reader_data = New-Object System.IO.StreamReader($Stream_data)

$Symbols = @()
$Symbols += "EURUSD"

$Cmd = "" | Select MSG,SYMBOLS,TIMEFRAME
$Cmd.MSG = "TRACK_OHLC"
$Cmd.SYMBOLS = $Symbols
$Cmd.TIMEFRAME = "PERIOD_M1"

$Json = ConvertTo-Json $Cmd -Compress

$Writer_cmd.WriteLine($Json)
$Writer_cmd.Flush()

$Response_cmd = $Reader_cmd.ReadLine()
Write-Host $Response_cmd

while ($true)
{
   $Response_data = $Reader_data.ReadLine()
   Write-Host $Response_data
}

$Stream_cmd.Close()
$Socket_cmd.Close()

$Stream_data.Close()
$Socket_data.Close()


