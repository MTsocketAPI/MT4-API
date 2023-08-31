# Example 2 - Send Order To MT4
#
# PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
#
# WARNING: All these source codes are only examples for testing purposes. 
# WARNING: We don’t provide any guarantee or responsibility about it. 
# WARNING: Use these examples at your own risk.

con <- socketConnection(host="localhost", port = 77, blocking = TRUE)
writeLines("{\"MSG\":\"ORDER_SEND\",\"SYMBOL\":\"EURUSD\",\"VOLUME\":0.03,\"TYPE\":\"ORDER_TYPE_BUY\"}\r\n", con)
server_resp <- readLines(con, 1)
close(con)
print(server_resp)

