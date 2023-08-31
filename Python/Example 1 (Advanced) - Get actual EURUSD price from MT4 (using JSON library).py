# Example 1 (Advanced) - Get actual EURUSD price from MT4 (using JSON library)
# 
# PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
#
# WARNING: All these source codes are only examples for testing purposes. 
# WARNING: We don’t provide any guarantee or responsibility about it. 
# WARNING: Use these examples at your own risk.

import socket
import sys
import json

buffer_size = 326582

def SendCommand(farg):
    dataToSend = json.dumps(farg) + "\r\n"
    s.send(dataToSend.encode('ascii'))
    return json.loads(s.recv(buffer_size).decode('ascii'))
    
host = "127.0.0.1"
port = 77                   # The same port as used by the server
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.settimeout(3) # 3 seconds timeout if API does not respond

s.connect((host, port))

JSONobject = {}
JSONobject['MSG'] = 'QUOTE'
JSONobject['SYMBOL'] = 'EURUSD' # Symbol is case sensitive

JSONresult = SendCommand(JSONobject)

print('RAW JSON message received:',JSONresult)
print('Symbol:',JSONresult['SYMBOL'])
print('Ask:',JSONresult['ASK'])
print('Bid:',JSONresult['BID'])

