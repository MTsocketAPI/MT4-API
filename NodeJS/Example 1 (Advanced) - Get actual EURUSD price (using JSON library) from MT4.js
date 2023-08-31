// Example 1 (Advanced) - Get actual EURUSD price (using JSON library) from MT4
//
// PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
//
// WARNING: All these source codes are only examples for testing purposes. 
// WARNING: We don’t provide any guarantee or responsibility about it. 
// WARNING: Use these examples at your own risk.

const Net = require('net');

const client_cmd = new Net.Socket();

client_cmd.connect(77, "localhost", function() {
        const JSONobj = {};
        JSONobj["MSG"]="QUOTE";
        JSONobj["SYMBOL"]="EURUSD";
        client_cmd.write(JSON.stringify(JSONobj) + '\r\n');
});

client_cmd.on('data', function(chunk) {
        //console.log(`${chunk.toString()}`);
        const JSONresult = JSON.parse(chunk.toString());
        console.log(`Time: ${JSONresult["TIME"]} Symbol: ${JSONresult["SYMBOL"]} Ask: ${JSONresult["ASK"]} Bid: ${JSONresult["BID"]}`);
        client_cmd.end();
});

