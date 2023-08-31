# Example 1 (Advanced) - Get actual EURUSD price (using JSON library) from MT4
#
# PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
#
# WARNING: All these source codes are only examples for testing purposes. 
# WARNING: We don’t provide any guarantee or responsibility about it. 
# WARNING: Use these examples at your own risk.


$IP = [System.Net.Dns]::GetHostAddresses("127.0.0.1")
$Port = 77

$Address = [System.Net.IPAddress]::Parse($IP)
$Socket = New-Object System.Net.Sockets.TCPClient($Address,$Port)
$Stream = $Socket.GetStream()

$Writer = New-Object System.IO.StreamWriter($Stream)
$Reader = New-Object System.IO.StreamReader($Stream)

$Cmd = "" | Select MSG,SYMBOL
$Cmd.MSG = "QUOTE"
$Cmd.SYMBOL = "EURUSD"

$Json = ConvertTo-Json $Cmd -Compress

$Writer.WriteLine($Json)
$Writer.Flush()

$Response = $Reader.ReadLine()
Write-Host $Response

$Stream.Close()
$Socket.Close()

