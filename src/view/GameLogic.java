package view;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {
    private int [][] boardArray;

    GameLogic(){
        boardArray = new int[13][13];
        Random random = new Random();
        int rnd;


        int blacks = 24; // random 1
        int whites = 24; // random 0

        for(int i = 0; i < 13; i++){
            if(i == 0 || i == 12)
                for (int j = 0; j < 13; j++){
                        rnd = random.nextInt(1-0 + 1);

                        if(rnd == 0 && whites > 0){
                            whites --;
                            boardArray[i][j] = 0;

                            if(j != i)
                                boardArray[j][i] = 1;
                        }
                        else if(rnd == 1 && blacks > 0){
                            blacks --;
                            boardArray[i][j] = 1;

                            if(j != i)
                                boardArray[j][i] = 0;
                        }
                    }
            }
        printBoard();
    }

    void printBoard(){
        for (int i = 0; i < 13; i++){
            for(int j = 0; j < 13; j++)
                System.out.print(boardArray[i][j] + " ");
            System.out.println();
        }
    }

}
