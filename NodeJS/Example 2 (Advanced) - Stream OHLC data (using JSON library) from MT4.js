// Example 2 (Advanced) - Stream OHLC data (using JSON library) from MT4
//
// PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
//
// WARNING: All these source codes are only examples for testing purposes. 
// WARNING: We don’t provide any guarantee or responsibility about it. 
// WARNING: Use these examples at your own risk.

const Net = require('net');

const client_cmd = new Net.Socket();
const client_data = new Net.Socket();

client_cmd.connect(77, "localhost", function() {
        const JSONobj = {};
        JSONobj["MSG"]="TRACK_OHLC";
        JSONobj["SYMBOLS"]=["EURUSD"];
        JSONobj["TIMEFRAME"]="PERIOD_M1";
        client_cmd.write(JSON.stringify(JSONobj) + '\r\n');
});

client_cmd.on('data', function(chunk) {
            console.log(`${chunk.toString()}`);
            client_cmd.end();
});

client_data.connect(78, "localhost");

client_data.on('data', function(chunk) {
        const JSONresult = JSON.parse(chunk.toString());
        console.log(`Time: ${JSONresult["OHLC"][0]["TIME"]} Symbol: ${JSONresult["SYMBOL"]} Close Price: ${JSONresult["OHLC"][0]["CLOSE"]}`);
});
