# Example 3 (Advanced) - Export Trade History from MT4 to CSV
#
# PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
#
# WARNING: All these source codes are only examples for testing purposes. 
# WARNING: We don’t provide any guarantee or responsibility about it. 
# WARNING: Use these examples at your own risk.

library("rjson")

con <- socketConnection(host="localhost", port = 77, blocking = TRUE)
df <- data.frame(MSG = c("TRADE_HISTORY"), FROM_DATE = c("2022.09.09 10:00:00"), TO_DATE = c("2022.09.20 23:59:59"))
writeLines(paste0(toJSON(df),"\r\n"), con)
server_resp <- readLines(con, 1)
json_resp <- fromJSON(server_resp)

if (json_resp["ERROR_ID"] == 0) {
  #print(server_resp)
  open_time <- c()
  close_time <- c()
  symbol <- c()
  ticket <- c()
  price_open <- c()
  price_close <- c()
  profit <- c()
  for (row in json_resp[["TRADES"]]) {
    open_time <- append(open_time,row[["OPEN_TIME"]])
    close_time <- append(close_time,row[["CLOSE_TIME"]])
    symbol <- append(symbol,row[["SYMBOL"]])
    ticket <- append(ticket,row[["TICKET"]])
    price_open <- append(price_open,row[["PRICE_OPEN"]])
    price_close <- append(price_close,row[["PRICE_CLOSE"]])
    profit <- append(profit,row[["PROFIT"]])
  }
  df <- data.frame(OPEN_TIME=open_time,CLOSE_TIME=close_time,SYMBOL=symbol,TICKET=ticket,PRICE_OPEN=price_open,PRICE_CLOSE=price_close,PROFIT=profit)
  write.csv(df,file="tradeHistoryFinal.csv",row.names = FALSE)
  print("File created successfully!")
} else {
  print(json_resp["ERROR_DESCRIPTION"])
}
close(con)

