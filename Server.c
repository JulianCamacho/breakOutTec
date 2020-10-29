#include <stdio.h>
#include<winsock2.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include "Constants.h"
int iResult, iSendResult;
SOCKET sAcceptSocket;
char recvbuf[DEFAULT_BUFLEN];
char prueba[512]="{\"matrix\":[[4,4,4,4,4,4,4,4,4,4,4,4,4,4],[4,4,0,4,4,4,4,4,4,4,4,4,4,4],[3,3,3,3,3,3,3,3,3,3,3,3,3,3],[3,3,3,3,3,3,3,3,3,3,3,0,3,3],[2,2,2,2,2,2,2,2,2,2,2,2,2,2],[2,2,2,2,2,2,2,2,2,2,0,2,2,2],[1,1,1,1,1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1,1,1,1,1]],\"lives\":0,\"ballQuantity\":2,\"ballSpeed\":7,\"ballPosition\":[42,69],\"racketLenght\":20,\"racketPosition\":50,\"level\":1,\"score\":900,\"greenBrickValue\":100,\"yellowBrickValue\":200,\"orangeBrickValue\":300,\"redBrickValue\":400\n"
                 "}" ;


int run(char * *mensaje){
    char Envtoken[512];
   strcpy(Envtoken,mensaje);
    printf("\t\t------SERVER------\t\n");
    //LOCAL VARIABLES
    WSADATA Winsockdata;
    int iWasaStartup;
    int iWasaCleanup;

    SOCKET TCPServerSocket;
    int iCloseSocket;

    struct sockaddr_in TCPServerAdd;
    struct sockaddr_in TCPClientAdd;
    int iTCPClientAdd=sizeof(TCPClientAdd);
    int iBind;
    int iListen;

    int iSend;
    char SenderBuffer[512]="Hola desde el Servidor";
    int iSenderBuffer= strlen(SenderBuffer)+1;
    int iRecv;
    char RecvBuffer[512];
    int iRecvBuffer=strlen(RecvBuffer)+1;

    // WSAStartup
    iWasaStartup=WSAStartup(MAKEWORD(2,2),&Winsockdata);
    if(iWasaStartup!=0){
        printf("Fallo el WasaStartup\n");
    }

    //Estructura
    TCPServerAdd.sin_family=AF_INET;
    TCPServerAdd.sin_addr.s_addr=inet_addr("127.0.0.1");
    TCPServerAdd.sin_port=htons(PORT);

    //Creacion
    TCPServerSocket=socket(AF_INET,SOCK_STREAM,IPPROTO_TCP);
    if(TCPServerSocket == SOCKET_ERROR){
        printf("Fallo en el TCPServerSocket");
    }

    //Bind
    iBind=bind(TCPServerSocket,(SOCKADDR*)&TCPServerAdd,sizeof(TCPServerAdd));
    if(iBind==SOCKET_ERROR){
        printf("Fallo en bind\n");
    }

    //Listen
    iListen=listen(TCPServerSocket,2);
    if(iListen==SOCKET_ERROR){
        printf("Fallo en listen\n");
    }

    //Aceptar conexiones
    printf("\t\tEsperando Cliente.....\n");
    sAcceptSocket= accept(TCPServerSocket,(SOCKADDR*)&iTCPClientAdd,&iTCPClientAdd);

    if(sAcceptSocket==INVALID_SOCKET){
        printf("Fallo en aceptar\n");
    }
    printf("Cliente Conectado.....\n");



    int recvbuflen = DEFAULT_BUFLEN;
// Receive until the peer shuts down the connection
    do {
        memset(recvbuf,0,recvbuflen);
        iResult = recv(sAcceptSocket, recvbuf, recvbuflen, 0);
        char* token=strtok(recvbuf,"/");
        if(token!=" ") {
            printf("Token: %s\n", token);
        }
        if (iResult > 0) {
            printf("Bytes received: %d\n", iResult);
            // Echo the buffer back to the sender
            iSendResult = send(sAcceptSocket, Envtoken, strlen(Envtoken)+1, 0);
            if (iSendResult == SOCKET_ERROR) {
                printf("send failed: %d\n", WSAGetLastError());
                closesocket(sAcceptSocket);
                WSACleanup();
                return 1;
            }
            printf("Bytes sent: %d\n", iSendResult);
        } else if (iResult == 0)
            printf("Connection closing...\n");
        else {
            printf("recv failed: %d\n", WSAGetLastError());
            closesocket(sAcceptSocket);
            WSACleanup();
            return 1;
        }

    } while (iResult > 0);
}