// Example 3 - Stream actual EURUSD price from MT4
//
// PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
//
// WARNING: All these source codes are only examples for testing purposes. 
// WARNING: We don’t provide any guarantee or responsibility about it. 
// WARNING: Use these examples at your own risk.

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

/*
*
*
* gcc -o trackprices trackprices.c (Compile)
* ./trackprices 127.0.0.1 (Run)
*
*/

int main(int argc, char *argv[])
{
    int sockfd_cmd, portno_cmd, n;
    int sockfd_data, portno_data;
    struct sockaddr_in serv_addr_cmd;
    struct sockaddr_in serv_addr_data;
    struct hostent *server_cmd;
    struct hostent *server_data;

    char msg[]="{\"MSG\":\"TRACK_PRICES\",\"SYMBOLS\":[\"EURUSD\"],\"TIMEFRAME\":\"PERIOD_M1\"}\r\n";

    char buffer[256];

    if (argc < 2) {
       fprintf(stderr,"usage %s hostname port\n", argv[0]);
       return 0;
    }

    portno_cmd = 77;
    portno_data = 78;
    sockfd_cmd = socket(AF_INET, SOCK_STREAM, 0);
    sockfd_data = socket(AF_INET, SOCK_STREAM, 0);

    if (sockfd_cmd < 0)
        printf("ERROR opening cmd socket\r\n");

    if (sockfd_data < 0)
        printf("ERROR opening data socket\r\n");

    server_cmd = gethostbyname(argv[1]);
    server_data = gethostbyname(argv[1]);

    if (server_cmd == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }

    bzero((char *) &serv_addr_cmd, sizeof(serv_addr_cmd));
    bzero((char *) &serv_addr_data, sizeof(serv_addr_data));

    serv_addr_cmd.sin_family = AF_INET;
    serv_addr_data.sin_family = AF_INET;

    bcopy((char *)server_cmd->h_addr,
         (char *)&serv_addr_cmd.sin_addr.s_addr,
         server_cmd->h_length);

    bcopy((char *)server_data->h_addr,
         (char *)&serv_addr_data.sin_addr.s_addr,
         server_data->h_length);

    serv_addr_cmd.sin_port = htons(portno_cmd);
    serv_addr_data.sin_port = htons(portno_data);

    if (connect(sockfd_cmd, (struct sockaddr *) &serv_addr_cmd, sizeof(serv_addr_cmd)) < 0)
    {
        printf("ERROR connecting to cmd port\r\n");
        return 0;
    }

    if (connect(sockfd_data, (struct sockaddr *) &serv_addr_data, sizeof(serv_addr_data)) < 0)
    {
        printf("ERROR connecting to data port\r\n");
        return 0;
    }

    n = write(sockfd_cmd, msg, strlen(msg));

    if (n < 0)
        printf("ERROR writing to socket\r\n");

    bzero(buffer,256);

    n = read(sockfd_cmd, buffer, 255);

    if (n < 0)
        printf("ERROR reading from socket\r\n");

    printf("%s\n", buffer);

    bzero(buffer,256);

    while(1) {
        n = read(sockfd_data, buffer, 255);

        printf("%s", buffer);
    }

    close(sockfd_cmd);
    close(sockfd_data);

    return 0;
}

