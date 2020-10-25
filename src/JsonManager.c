#include <stdio.h>
#include <stdlib.h>
#include "../lib/json-maker.h"
#include "../lib/tiny-json.h"
#include "GameStructure.c"
#include "Constants.h"


/** jsonGame(): convertir el struct game_t en un string en formato json
 * char* dest: donde se guardará el string
 * game_t* src: struct game actual
 */
char* jsonGame( char* dest, game_t* src ){
    char* p = dest;                                         // p always points to the null character
    //Abrir el JSON
    p = json_objOpen( p, NULL );                            // --> {\0

    //Insertar la matriz en formato json
    p = json_arrOpen( p, "matrix" );            //Abrir un arreglo para la matriz
    for( int i = 0; i < MAXROWS ; i++){
        p = json_arrOpen(p, NULL);              //Abrir un subarreglo
        for (int j = 0; j < MAXCOLS; j++){
            p = json_int( p, NULL, src->matrix[i][j] );    //Colocar los elementos
        }
        p = json_arrClose( p );                 //Cerrar subarreglo
    }
    p = json_arrClose( p );                     //Cerrar arreglo de la matriz
    
    p = json_int( p, "lives", src->lives );                 // --> {"lives":3,\0
    p = json_int( p, "ballQuantity", src->ballQuantity );   // --> {"lives":3,"ballQuantity":1,\0
    p = json_double( p, "ballSpeed", src->ballSpeed ); 
    
    p = json_arrOpen( p, "ballPosition" );
    for (int k = 0; k < GAMEDIMENSION; k++) {
        p = json_int( p, NULL, src->ballPosition[k]);       //Agregar las coordenadas como un arreglo en foramto json
    }
    p = json_arrClose( p );     

    p = json_double( p, "racketLenght", src->racketLenght ); 
    p = json_int( p, "racketPosition", src->racketPosition ); 

    p = json_int( p, "level", src->level ); 
    p = json_int( p, "score", src->score ); 

    p = json_int( p, "greenBrickValue", src->greenBrickValue );
    p = json_int( p, "yellowBrickValue", src->yellowBrickValue );
    p = json_int( p, "orangeBrickValue", src->orangeBrickValue );
    p = json_int( p, "redBrickValue", src->redBrickValue );

    p = json_objClose( p );                                 // --> {"lives":3,"ballQuantity":1}\0
    p = json_end( p );                                      // --> {"lives":3,"ballQuantity":1}\0

    return p ;       
}



//============================== READER ==================================//

/* jsonParse(): leer un archivo json
 * 
 */
int jsonParse(){

    FILE *fp;
    char buff[MAX_READ_JSON];       //Buff es el texto del archivo
    if ((fp = fopen("./levels/level1.json", "r")) == NULL) {
        printf("Error! opening file");
        return -1;
    }
    fscanf(fp, "%s", buff);
    printf("Texto del JSON recibido: %s\n", buff );
    
    fclose(fp);

}



/*
 * useJsonFile: ingresar el string json en un archivo .json
 * char* newJson: string en formato json
 */
void useJsonFile(char* newJson){
    FILE *fp;
    fp = fopen("game.json", "w+");
    fputs(newJson, fp);
    fclose(fp);
}

/**
 * Actualizar el puntaje del jugador 
 * brickID: número del ladrillo en el json
 */
void updateScore(game_t* currentGame, int brickID){
    switch (brickID){
    case 1:
        currentGame->score += currentGame->greenBrickValue;
        break;
    case 2:
        currentGame->score += currentGame->yellowBrickValue;
        break;
    case 3:
        currentGame->score += currentGame->orangeBrickValue;
        break;
    case 4:
        currentGame->score += currentGame->redBrickValue;
        break;
    default:
        break;
    }
}


void receiveClientMessage(char* msg, game_t* currentGame){
    short cont = 0;
    char tmp[256];
    strcpy(tmp, msg);       //Copiar msg en tmp

    char* splitMsg = strtok(tmp, " ");      // splitMsg equivalente msg[0]
    printf("Mensaje del cliente recibido: %s ", splitMsg);


    //=======================    Actualizar si se rompió un bloque    =======================//
                                                    
    if (strcmp(splitMsg, "Broke") == 0){  
        
        splitMsg=strtok(NULL," ");          // splitMsg equivalente msg[1]
        int row = atoi(splitMsg);
        printf("%i ", row);

        splitMsg=strtok(NULL," ");          // splitMsg equivalente msg[2]  
        int col = atoi(splitMsg);
        printf("%i \n", col);

        updateScore(currentGame, currentGame->matrix[row][col]);

        currentGame->matrix[row][col] = 0;
    }


    //====================    Actualizar la cantidad de vidas    ====================//

    else if (strcmp(splitMsg, "LostLife") == 0){
        currentGame->lives--;    
    }


    //====================    Actualizar la cantidad de bolas    =======================//

    else if (strcmp(splitMsg, "BallQuantity") == 0){
        currentGame->ballQuantity+=1;    
    }


    //====================    Actualizar la velocidad de la bola    =======================//

    else if (strcmp(splitMsg, "BallSpeed") == 0){
        splitMsg=strtok(NULL," ");          // splitMsg equivalente msg[1]
        printf("%s \n", splitMsg);
        if (strcmp(splitMsg, "Up") == 0){
            currentGame->ballSpeed = (1.3 * currentGame->ballSpeed);
        } 
        else if (strcmp(splitMsg, "Down") == 0){
            currentGame->ballSpeed = (0.7 * currentGame->ballSpeed);
        }
    } 


    //====================    Actualizar la posición de la bola    =======================//

    else if (strcmp(splitMsg, "BallPosition") == 0){
        splitMsg=strtok(NULL," ");          // splitMsg equivalente msg[1]
        int xPos = atoi(splitMsg);
        printf("%i ", xPos);

        splitMsg=strtok(NULL," ");          // splitMsg equivalente msg[2]  
        int yPos = atoi(splitMsg);
        printf("%i \n", yPos);

        currentGame->ballPosition[0] = xPos;
        currentGame->ballPosition[1] = yPos;
    }


    //====================    Actualizar la longitud de la raqueta    =======================//

    else if (strcmp(splitMsg, "RacketLenght") == 0){
        splitMsg=strtok(NULL," ");          // splitMsg equivalente msg[1]
        printf("%s \n", splitMsg);
        if (strcmp(splitMsg, "Up") == 0){
            currentGame->racketLenght = (2 * currentGame->racketLenght);
        } 
        else if (strcmp(splitMsg, "Down") == 0){
            currentGame->racketLenght = (0.5 * currentGame->racketLenght);
        }    
    } 


    //====================    Actualizar la posición de la raqueta    =======================//

    else if (strcmp(splitMsg, "RacketPosition") == 0){
        splitMsg=strtok(NULL," ");          // splitMsg equivalente msg[1]
        int pos = atoi(splitMsg);
        printf("%i \n", pos);
        
        currentGame->racketPosition = pos;
    }
    else{
        printf("Mensaje inválido o no procesado");
    } 
}



//================================================================//


void receiveUserMessage(char* msg, game_t* currentGame){
    short cont = 0;
    char tmp[256];
    strcpy(tmp, msg);       //Copiar msg en tmp

    char* splitMsg = strtok(tmp, " ");      // splitMsg equivalente msg[0]
    printf("Mensaje del usuario recibido: %s ", splitMsg);

    //=======================    Actualizar el valor de los bloques    =======================//

    if (strcmp(splitMsg, "BrickValues") == 0){  
        
        splitMsg=strtok(NULL," ");          // splitMsg equivalente msg[1]
        int green = atoi(splitMsg);
        printf("%i ", green);

        splitMsg=strtok(NULL," ");          // splitMsg equivalente msg[2]  
        int yellow = atoi(splitMsg);
        printf("%i ", yellow);

        splitMsg=strtok(NULL," ");          // splitMsg equivalente msg[3]  
        int orange = atoi(splitMsg);
        printf("%i ", orange);

        splitMsg=strtok(NULL," ");          // splitMsg equivalente msg[4]  
        int red = atoi(splitMsg);
        printf("%i ", red);

        currentGame->greenBrickValue = green;
        currentGame->yellowBrickValue = yellow;
        currentGame->orangeBrickValue = orange;
        currentGame->redBrickValue = red;
    }


    //====================    Actualizar la cantidad de vidas    ====================//

    else if (strcmp(splitMsg, "Life") == 0){
        splitMsg = strtok(NULL," ");          // splitMsg equivalente msg[1]
        int row = atoi(splitMsg);
        printf("%i ", row);

        splitMsg = strtok(NULL," ");          // splitMsg equivalente msg[2]  
        int col = atoi(splitMsg);
        printf("%i \n", col);

        currentGame->matrix[row][col] = BRICK_LIFE;    
    }


    //====================    Actualizar la cantidad de bolas    =======================//

    else if (strcmp(splitMsg, "BallQuantity") == 0){
        splitMsg = strtok(NULL," ");          // splitMsg equivalente msg[1]
        int row = atoi(splitMsg);
        printf("%i ", row);

        splitMsg = strtok(NULL," ");          // splitMsg equivalente msg[2]  
        int col = atoi(splitMsg);
        printf("%i \n", col);

        currentGame->matrix[row][col] = BRICK_BALL; 
    }


    //====================    Actualizar la velocidad de la bola    =======================//

    else if (strcmp(splitMsg, "BallSpeed") == 0){
        splitMsg=strtok(NULL," ");          // splitMsg equivalente msg[1]
        printf("%s ", splitMsg);
        if (strcmp(splitMsg, "Up") == 0){
            splitMsg = strtok(NULL," ");          // splitMsg equivalente msg[1]
            int row = atoi(splitMsg);
            printf("%i ", row);

            splitMsg = strtok(NULL," ");          // splitMsg equivalente msg[2]  
            int col = atoi(splitMsg);
            printf("%i \n", col);
            currentGame->matrix[row][col] = BRICK_SPEED_UP;
        } 
        else if (strcmp(splitMsg, "Down") == 0){
            splitMsg = strtok(NULL," ");          // splitMsg equivalente msg[1]
            int row = atoi(splitMsg);
            printf("%i ", row);

            splitMsg = strtok(NULL," ");          // splitMsg equivalente msg[2]  
            int col = atoi(splitMsg);
            printf("%i \n", col);

            currentGame->matrix[row][col] = BRICK_SPEED_DOWN;
        }
    } 


    //====================    Actualizar la longitud de la raqueta    =======================//

    else if (strcmp(splitMsg, "RacketLenght") == 0){
        splitMsg=strtok(NULL," ");          // splitMsg equivalente msg[1]
        printf("%s ", splitMsg);

        if (strcmp(splitMsg, "Up") == 0){
            splitMsg = strtok(NULL," ");          // splitMsg equivalente msg[2]
            int row = atoi(splitMsg);
            printf("%i ", row);

            splitMsg = strtok(NULL," ");          // splitMsg equivalente msg[3]  
            int col = atoi(splitMsg);
            printf("%i \n", col);
            currentGame->matrix[row][col] = BRICK_RACKET_UP;
        } 
        else if (strcmp(splitMsg, "Down") == 0){
            splitMsg = strtok(NULL," ");          // splitMsg equivalente msg[2]
            int row = atoi(splitMsg);
            printf("%i ", row);

            splitMsg = strtok(NULL," ");          // splitMsg equivalente msg[3]  
            int col = atoi(splitMsg);
            printf("%i \n", col);

            currentGame->matrix[row][col] = BRICK_RACKET_DOWN;
        } 
    } 
    else{
        printf("Mensaje inválido o no procesado");
    } 
}
