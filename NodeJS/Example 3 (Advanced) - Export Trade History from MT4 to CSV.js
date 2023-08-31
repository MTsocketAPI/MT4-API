// Example 3 (Advanced) - Export Trade History from MT4 to CSV
//
// PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
//
// WARNING: All these source codes are only examples for testing purposes. 
// WARNING: We don’t provide any guarantee or responsibility about it. 
// WARNING: Use these examples at your own risk.

const Net = require('net');
const fs = require('fs');
const delimiter = ',';
const exportedFile = 'tradeHistory.csv';
const fromDate = "2022/04/12 13:00:00";
const toDate = "2022/04/15 00:00:00";
const client_cmd = new Net.Socket();

client_cmd.connect(77, "localhost", function() {
        const JSONobj = {};
        JSONobj["MSG"]="TRADE_HISTORY";
        JSONobj["FROM_DATE"]=fromDate;
        JSONobj["TO_DATE"]=toDate;
        client_cmd.write(JSON.stringify(JSONobj) + '\r\n');
});

client_cmd.on('data', function(chunk) {
        const JSONresult = JSON.parse(chunk.toString());
        const numTrades = Object.keys(JSONresult['TRADES']).length;
        console.log(`Number of trades: ${numTrades}`);
        
        //First we must write the CSV HEADER
        fs.writeFileSync('tradeHistory.csv','OPEN_TIME,CLOSE_TIME,TICKET,SYMBOL,LOTS,OPEN_PRICE,CLOSE_PRICE,PROFIT\r\n',{ flag: 'a+' });
        
        //Loop JSON array
        for(let i = 0; i < numTrades; i++) {
                //You can add more files like COMMENT, SWAP, COMMISSION...
                const OPEN_TIME = JSONresult['TRADES'][i]['OPEN_TIME'];
                const CLOSE_TIME = JSONresult['TRADES'][i]['CLOSE_TIME'];
                const TICKET = JSONresult['TRADES'][i]['TICKET'];
                const SYMBOL = JSONresult['TRADES'][i]['SYMBOL'];
                const LOTS = JSONresult['TRADES'][i]['LOTS'];
                const OPEN_PRICE = JSONresult['TRADES'][i]['OPEN_PRICE'];
                const CLOSE_PRICE = JSONresult['TRADES'][i]['CLOSE_PRICE'];
                const PROFIT = JSONresult['TRADES'][i]['PROFIT'];
                
                fs.writeFileSync(exportedFile, OPEN_TIME + delimiter + CLOSE_TIME + delimiter + TICKET + delimiter + SYMBOL + delimiter + LOTS + delimiter + OPEN_PRICE + delimiter + CLOSE_PRICE + delimiter + PROFIT + '\r\n',{ flag: 'a+' });
        }
        
        console.log(`Created the file ${exportedFile}!`);
        
        client_cmd.end();
});
