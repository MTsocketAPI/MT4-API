# Example 2 (Advanced) - Stream OHLC data (using JSON library) from MT4
#
# PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
#
# WARNING: All these source codes are only examples for testing purposes. 
# WARNING: We don’t provide any guarantee or responsibility about it. 
# WARNING: Use these examples at your own risk.

library("rjson")

conCMD <- socketConnection(host="localhost", port = 77, blocking = TRUE)
conDATA <- socketConnection(host="localhost", port = 78, blocking = TRUE)

symbolList <- list("EURUSD")

result <- list(MSG = c("TRACK_OHLC"), SYMBOLS = symbolList,TIMEFRAME = c("PERIOD_M1"))

writeLines(paste0(toJSON(result),"\r\n"), conCMD)

server_resp <- readLines(conCMD, 1)
#print(server_resp)
jsonReply <- fromJSON(server_resp)
if (jsonReply["ERROR_ID"] == 0) {
  print("Waiting for OHLC data. Please wait...")
  repeat {
      JSONdata <- fromJSON(readLines(conDATA, 1))
      if (is.null(JSONdata[["OHLC"]]) == FALSE) {
        print(paste("OHLC Prices:",readLines(conDATA, 1)))  
      }
  }
} else {
  print(jsonReply["ERROR_DESCRIPTION"])
  }
close(conCMD)
close(conDATA)


