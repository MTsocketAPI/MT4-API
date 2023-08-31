// Example 1 (Advanced) - Get actual EURUSD price (using JSON library) from MT4
//
// PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
//
// WARNING: All these source codes are only examples for testing purposes. 
// WARNING: We don’t provide any guarantee or responsibility about it. 
// WARNING: Use these examples at your own risk.

package main

import (
    "encoding/json"
    "fmt"
    "net"
)

type quoteCommand struct {
    MSG    string
    SYMBOL      string
}

func main() {
    cmd := quoteCommand{
        MSG: "QUOTE",
        SYMBOL:   "EURUSD",
    }
    jsonData, err := json.Marshal(cmd)

    con, err := net.Dial("tcp", "localhost:77")
    if err != nil { fmt.Println(err) }

    msg := string(jsonData) + "\r\n"
    _, err = con.Write([]byte(msg))
    if err != nil { fmt.Println(err) }
    
    reply := make([]byte, 1024)
    msgLen, err := con.Read(reply)
    if err != nil { fmt.Println(err) }
    
    con.Close()

    bytes := []byte(string(reply[0:msgLen]))
    var result map[string]interface{}
    json.Unmarshal(bytes, &result)

    fmt.Printf("Symbol: %s  Ask: %f   Bid: %f\n", result["SYMBOL"],result["ASK"],result["BID"])
}
