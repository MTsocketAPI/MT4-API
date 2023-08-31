# Example 1 - Get current actual EURUSD price
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

$Writer.WriteLine('{"MSG":"QUOTE","SYMBOL":"EURUSD"}')
$Writer.Flush()

$Response = $Reader.ReadLine()
Write-Host $Response

$Stream.Close()
$Socket.Close()