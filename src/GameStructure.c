#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>

#include "Constants.h"


typedef struct game{
    int matrix[MAXROWS][MAXCOLS];
    int lives;
    int ballQuantity;
    double ballSpeed;
    
    int ballPosition[GAMEDIMENSION];
    double racketLenght;
    int racketPosition;

    int level;
    int score;
    
    int greenBrickValue;
    int yellowBrickValue;
    int orangeBrickValue;
    int redBrickValue;
} game_t;

/** createGame: inicializar por defecto todos los miembros del struct game 
 * game_t* newGame: game por inicializar
 * int greenVal: valor para los ladrillos verdes
 * int yellowVal: valor para los ladrillos amarillos
 * int orangeVal: valor para los ladrillos anaranjados
 * int redVal: valor para los ladrillos rojos
 */

void createGame(game_t* newGame, int greenVal, 
                                int yellowVal, 
                                int orangeVal, 
                                int redVal, double newBallSpeed, int levelUp, int newScore){
    //Inicializar todos los datos del juego actual

    //Inicializar la matriz de juego
    for (int i = 0; i < MAXROWS; i++){
        for (int j = 0; j < MAXCOLS; j++){
            if (i == 0 || i == 1){
                newGame->matrix[i][j] = 4;
                continue;
            } 
            else if(i == 2 || i == 3){
                newGame->matrix[i][j] = 3;
                continue;
            }
            else if(i == 4 || i == 5){
                newGame->matrix[i][j] = 2;
                continue;
            }
            else if(i == 6 || i == 7){
                newGame->matrix[i][j] = 1;
                continue;
            }
        }
    }
    
    //Inicializar vidas, y demás miembros
    newGame->lives = INIT_LIVES;
    newGame->ballQuantity = 1;
    newGame->ballSpeed = newBallSpeed;
    newGame->ballPosition[0] = 0;
    newGame->ballPosition[1] = 0;
    newGame->racketLenght = 10;
    newGame->racketPosition = 0;

    newGame->level = levelUp;
    newGame->score = newScore;

    newGame->greenBrickValue = greenVal;
    newGame->yellowBrickValue = yellowVal;
    newGame->orangeBrickValue = orangeVal;
    newGame->redBrickValue = redVal;

    printf("Valores de ladrillos: G = %i, Y = %i, O = %i, R = %i \n", 
        newGame->greenBrickValue, 
        newGame->yellowBrickValue, 
        newGame->orangeBrickValue, 
        newGame->redBrickValue);
}


bool levelFinished(game_t* currentGame){
    for (int i = 0; i < MAXROWS; i++){
        for (int j = 0; j < MAXCOLS; j++){
            if (currentGame->matrix[i][j] != 0){
                return false;
            }
        }
    }
    return true;
}

/**
 * nextLevel: revisa si el nivel ha terminado, 
 * si es así, vuelve a inicializar el juego con los nuevos valores correspondientes 
 */
void nextLevel(game_t* currrentGame){
    if(levelFinished(currrentGame) == true){
        //Reiniciar el juego con los mismos valores de ladrillos, pero con el doble de la velocidad de la bola
        createGame(currrentGame,currrentGame->greenBrickValue, 
                                currrentGame->yellowBrickValue, 
                                currrentGame->orangeBrickValue,
                                currrentGame->redBrickValue, 
                                                            currrentGame->ballSpeed*2, 
                                                            currrentGame->level++,
                                                            currrentGame->score);
    }else{
        printf("No se ha terminado el nivel");
    }
}

bool lost(game_t* currentGame){
    return currentGame->lives == 0;
}

