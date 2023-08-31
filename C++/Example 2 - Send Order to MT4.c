// Example 2 - Send Order To MT4
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
* gcc -o sendorder sendorder.c (Compile)
* ./sendorder 127.0.0.1 77 (Run)
*
*/

int main(int argc, char *argv[])
{
    int sockfd, portno, n;
    struct sockaddr_in serv_addr;
    struct hostent *server;

    char msg[]="{\"MSG\":\"ORDER_SEND\",\"SYMBOL\":\"EURUSD\",\"VOLUME\":0.03,\"TYPE\":\"ORDER_TYPE_BUY\"}\r\n";

    char buffer[256];

    if (argc < 3) {
       fprintf(stderr,"usage %s hostname port\n", argv[0]);
       return 0;
    }

    portno = atoi(argv[2]);
    sockfd = socket(AF_INET, SOCK_STREAM, 0);

    if (sockfd < 0)
        printf("ERROR opening socket\r\n");

    server = gethostbyname(argv[1]);

    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }

    bzero((char *) &serv_addr, sizeof(serv_addr));

    serv_addr.sin_family = AF_INET;

    bcopy((char *)server->h_addr,
         (char *)&serv_addr.sin_addr.s_addr,
         server->h_length);

    serv_addr.sin_port = htons(portno);

    if (connect(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0)
    {
        printf("ERROR connecting\r\n");
        return 0;
    }

    n = write(sockfd, msg, strlen(msg));

    if (n < 0)
        printf("ERROR writing to socket\r\n");

    bzero(buffer,256);

    n = read(sockfd, buffer, 255);

    if (n < 0)
        printf("ERROR reading from socket\r\n");

    printf("%s\n", buffer);

    close(sockfd);

    return 0;
}

