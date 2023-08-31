// Example 1 - Get current actual EURUSD price
// 
// PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
//
// WARNING: All these source codes are only examples for testing purposes. 
// WARNING: We don’t provide any guarantee or responsibility about it. 
// WARNING: Use these examples at your own risk.

const Net = require('net');

const client = new Net.Socket();

client.connect(77, "localhost", function() {
    client.write('{"MSG":"QUOTE","SYMBOL":"EURUSD"}' + '\r\n');
});

client.on('data', function(chunk) {
    console.log(`${chunk.toString()}`);
    client.end();
});
