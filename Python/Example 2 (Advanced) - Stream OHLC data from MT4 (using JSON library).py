# Example 2 (Advanced) - Stream OHLC data from MT4 (using JSON library)
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
    s_cmd.send(dataToSend.encode('ascii'))
    return json.loads(s_cmd.recv(buffer_size).decode('ascii'))

s_cmd = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s_cmd.settimeout(3)
s_cmd.connect(("127.0.0.1", 77)) # Command port

s_data = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s_data.connect(("127.0.0.1", 78)) # Data port

JSONobject = {}
JSONobject['MSG'] = 'TRACK_OHLC'
JSONobject['SYMBOLS'] = ['EURUSD'] #Symbol is case sensitive
JSONobject['TIMEFRAME'] = 'PERIOD_M1' # We will receive OHLC data every minute

JSONresult = SendCommand(JSONobject)

print('RAW JSON message received:',JSONresult)

while True:
    dataReceived = s_data.recv(buffer_size).decode('ascii')

    JSONprice = json.loads(dataReceived)
    
    if JSONprice["MSG"] == "TRACK_OHLC":
       #print('RAW data received:',dataReceived)
       print("Time:",JSONprice['OHLC'][0]['TIME'],"Symbol:",JSONprice['SYMBOL'],"Open:",JSONprice['OHLC'][0]['OPEN'],"Close:",JSONprice['OHLC'][0]['CLOSE'],"High:",JSONprice['OHLC'][0]['HIGH'],"Low:",JSONprice['OHLC'][0]['LOW'])


