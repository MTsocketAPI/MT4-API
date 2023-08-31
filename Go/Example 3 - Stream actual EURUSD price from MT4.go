// Example 3 - Stream actual EURUSD price from MT4
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
    con1, err := net.Dial("tcp", "localhost:77")
    if err != nil { fmt.Println(err) }
    con2, err := net.Dial("tcp", "localhost:78")
    if err != nil { fmt.Println(err) }
    defer con1.Close()
    defer con2.Close()
    msg := "{\"MSG\":\"TRACK_PRICES\",\"SYMBOLS\":[\"EURUSD\"]}\r\n"
    _, err = con1.Write([]byte(msg))
    if err != nil { fmt.Println(err) }
    reply := make([]byte, 1024)
    _, err = con1.Read(reply)
    if err != nil { fmt.Println(err) }
    fmt.Println(string(reply))
    for{
        _, err = con2.Read(reply)
        if err != nil { fmt.Println(err) }
        fmt.Println(string(reply))
    }
}
