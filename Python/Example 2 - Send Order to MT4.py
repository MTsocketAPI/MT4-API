# Example 2 - Send Order to MT4
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
host = "127.0.0.1"
port = 77           # Default command port used by the server
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.settimeout(3)     # 3 seconds timeout if we don't receive response from the API
s.connect((host, port))

s.sendall(b'{"MSG": "ORDER_SEND", "SYMBOL": "EURUSD","TYPE":"ORDER_TYPE_BUY","VOLUME":0.02}\r\n')
print(s.recv(buffer_size).decode('ascii'))

