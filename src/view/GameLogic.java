package view;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

    private int [][] boardArray;

    public int[][] getBoardArray() {
        return boardArray;
    }

    GameLogic(){
        boardArray = new int[13][13];
        Random random = new Random();
        int rnd;


        int blacks = 24; // random 2
        int whites = 24; // random 1

        for(int i = 0; i < 13; i++){
            if(i == 0 || i == 12)
                for (int j = 0; j < 13; j++){
                        rnd = random.nextInt(1-0 + 1);

                        if(rnd == 0 && whites > 0){
                            whites --;
                            boardArray[i][j] = 1;

                            if(j != i)
                                boardArray[j][i] = 2;
                        }
                        else if(rnd == 1 && blacks > 0){
                            blacks --;
                            boardArray[i][j] = 2;

                            if(j != i)
                                boardArray[j][i] = 1;
                        }
                    }
            }

            if(boardArray[0][0] == boardArray[12][12])
                if(boardArray[0][0] == 1)
                    boardArray[12][12] = 2;
                else
                    boardArray[12][12] = 1;


        printBoard();
    }

    boolean verifyCaptureKing(int actualMoveY, int actualMoveX, int player){
        int newMoveY = actualMoveY - 6;
        int newMoveX = actualMoveX - 6;

        for(int i = 0; i < 3; i++){
            System.out.println(newMoveY + " " +newMoveX);
            newMoveY = newMoveY * (int)Math.cos(Math.toRadians(180)) + newMoveX * (int)Math.sin(Math.toRadians(180));
            newMoveX = newMoveX * (int)Math.cos(Math.toRadians(180)) - newMoveY * (int)Math.sin(Math.toRadians(180));

            System.out.println(newMoveY + " " +newMoveX);

            if(boardArray[newMoveX+6][newMoveY+6] != player)
                return false;
        }

        System.out.println("Existe uma simetria!");

        return true;
    }

    void printBoard(){
        for (int i = 0; i < 13; i++){
            for(int j = 0; j < 13; j++)
                System.out.print(boardArray[i][j] + " ");
            System.out.println();
        }
    }

}
