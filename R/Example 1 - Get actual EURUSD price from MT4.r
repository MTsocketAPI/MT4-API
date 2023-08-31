# Example 1 - Get current actual EURUSD price
# 
# PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
#
# WARNING: All these source codes are only examples for testing purposes. 
# WARNING: We don’t provide any guarantee or responsibility about it. 
# WARNING: Use these examples at your own risk.

con <- socketConnection(host="localhost", port = 77, blocking = TRUE)
writeLines("{\"MSG\":\"QUOTE\",\"SYMBOL\":\"EURUSD\"}\r\n", con)
server_resp <- readLines(con, 1)
close(con)
print(server_resp)
