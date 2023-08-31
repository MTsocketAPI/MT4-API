// Example 2 - Send Order To MT4
//
// PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
//
// WARNING: All these source codes are only examples for testing purposes. 
// WARNING: We don’t provide any guarantee or responsibility about it. 
// WARNING: Use these examples at your own risk.

package main

import (
    "fmt"
    "net"
)

func main() {
    con, err := net.Dial("tcp", "localhost:77")
    if err != nil { fmt.Println(err) }
    defer con.Close()
    msg := "{\"MSG\":\"ORDER_SEND\",\"SYMBOL\":\"EURUSD\",\"TYPE\":\"ORDER_TYPE_BUY\",\"VOLUME\":0.02}\r\n"
    _, err = con.Write([]byte(msg))
    if err != nil { fmt.Println(err) }
    reply := make([]byte, 1024)
    _, err = con.Read(reply)
    if err != nil { fmt.Println(err) }
    fmt.Println(string(reply))
}
