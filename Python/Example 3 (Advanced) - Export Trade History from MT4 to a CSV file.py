# Example 3 (Advanced) - Export Trade History from MT4 to a CSV file
# 
# PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
#
# WARNING: All these source codes are only examples for testing purposes. 
# WARNING: We don’t provide any guarantee or responsibility about it. 
# WARNING: Use these examples at your own risk.

import socket
import sys
import json
import csv

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
JSONobject['MSG'] = 'TRADE_HISTORY'
JSONobject['FROM_DATE'] = '2023/08/23 00:00:00'
JSONobject['TO_DATE'] = '2023/08/24 00:00:00'

JSONresult = SendCommand(JSONobject)

#print('RAW JSON message received:',JSONresult)

header = ['SYMBOL', 'MAGIC', 'TICKET', 'OPEN_TIME','CLOSE_TIME','PRICE_OPEN','PRICE_CLOSE','TYPE','LOTS','STOP_LOSS','TAKE_PROFIT','SWAP','COMMISSION','COMMENT','PROFIT']

f = open('History.csv', 'w')
writer = csv.writer(f)
writer.writerow(header)

for item in JSONresult['TRADES']:
    #print('row:',item['TICKET'])
    row = [item['SYMBOL'],item['MAGIC'],item['TICKET'],item['OPEN_TIME'],item['CLOSE_TIME'],item['PRICE_OPEN'],item['PRICE_CLOSE'],item['TYPE'],item['LOTS'],item['STOP_LOSS'],item['TAKE_PROFIT'],item['SWAP'],item['COMMISSION'],item['COMMENT'],item['PROFIT']]
    writer.writerow(row)

f.close()

print('Exported successfully!')