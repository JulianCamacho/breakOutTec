#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>
#include <Windows.h>

#include "JsonManager.c"
#include "Constants.h"
#include "../lib/json-maker.c"
#include "../lib/tiny-json.c"
//Undefined reference to -> include .c


DWORD WINAPI myThread(LPVOID lpParam){
    printf("Thread running! Escuchando sus sugerenias.. \n");
    char input[100];
    gets(input);
    printf("Ha ingresado: %s\n", input);
    receiveUserMessage(input, lpParam);
    return 0;
}



  
int main(int argc,char* argv[]) { 

//======================  MANEJO DE ARGUMENTOS  =======================//

    int counter; 
    printf("Program Name Is: %s\n",argv[0]); 

    //Si no se ingresó algún argumento
    if(argc==1) 
        printf("No Extra Command Line Argument Passed Other Than Program Name \n"); 

    //Imprime los valores de los argumentos ingresados     
    if(argc>=2) { 
        printf("Number Of Arguments Passed: %d \n",argc); 
        printf("----Following Are The Command Line Arguments Passed---- \n"); 
        for(counter=0;counter<argc;counter++) 
            printf("argv[%d]: %s\n",counter,argv[counter]); 
    } 

//=======================================================================//
    
    
    //Crear juego
    game_t* g1 = NULL;
    g1 = (game_t *) malloc(sizeof(game_t));
    if (g1 == NULL) {
        return -1;
    }

    /* Si el .exe se ejecuta con al menos 5 argumentos
        argv[0] = nombreArchivo
        argv[1] = valor ladrillo verde
        ...
    */
    if (argc = MINARGS){
        //Inicializar el juego
        createGame(g1, atoi(argv[1]), atoi(argv[2]), atoi(argv[3]), atoi(argv[4]), INIT_BALL_SPEED, INIT_LVL, INIT_SCORE);

        //Pruebas para revisar la actualización del juego con los mensajes del Cliente 
        receiveClientMessage("Broke 1 2", g1);
        receiveClientMessage("LostLife", g1);
        receiveClientMessage("BallQuantity", g1);
        receiveClientMessage("BallSpeed Down", g1);
        receiveClientMessage("Broke 5 10", g1);
        receiveClientMessage("BallPosition 42 69", g1);
        receiveClientMessage("RacketLenght Up", g1);
        receiveClientMessage("RacketPosition 50", g1);
        receiveClientMessage("Broke 3 11", g1);
        receiveClientMessage("LostLife", g1);
        receiveClientMessage("LostLife", g1);


        char buff[512];
        //Pasar la estructura actual del juego a JSON String
        jsonGame(buff, g1);
        printf("\nJSON del juego actual= %s\n", buff);
        //Escribir el JSON String en el archivo game.json
        useJsonFile(buff);
    } 
    else{ 
        printf("No se lograron asignar valores a los ladrillos \n"); 
    }

    //========================  Hacer un loop while ========================//
    
    //Verificar si hay mensajes nuevos


    //Verifica si hay que pasar de nivel
    nextLevel(g1);

    //Verifica si el jugador perdió
    if(lost(g1)){
        printf("You lost");
        //Mandar un mensaje, cerrar el juego
    }

    //=======================================================================//

    //Thread (no sirve del todo)
/*  
    HANDLE hThread;
    DWORD threadID;
    hThread = CreateThread(NULL, 0, myThread, g1, 0, &threadID);
    if (hThread == NULL){
        printf("No se ha podido crear el thread \n");
    }
    printf("Thread creado, su id es: %d \n", threadID);

    //WaitForSingleObject(hThread, INFINITE);
    CloseHandle(hThread);
    system("PAUSE");*/
    

    return 0; 
} 
