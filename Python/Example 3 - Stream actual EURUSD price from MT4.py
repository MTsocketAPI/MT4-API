# Example 3 - Stream actual EURUSD price from MT4
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

s_cmd = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s_cmd.settimeout(3)     # 3 seconds timeout if we don't receive response from the API
s_cmd.connect(("127.0.0.1", 77)) # Command port

s_cmd.sendall(b'{"MSG": "TRACK_PRICES", "SYMBOLS": ["EURUSD"]}\r\n')
print(s_cmd.recv(buffer_size).decode('ascii'))

s_data = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s_data.connect(("127.0.0.1", 78)) # Data port for receiving prices

while True:
   print(s_data.recv(buffer_size).decode('ascii'))

