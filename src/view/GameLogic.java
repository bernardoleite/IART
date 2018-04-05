package view;

import javafx.util.Pair;

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


        //boardArray[11][11] = 2;

        boardArray[1][2] = 2;
        boardArray[10][1] = 2;
        boardArray[11][10] = 2;
        boardArray[2][11] = 2;

        verifyCaptureKing(1,2,2);

        printBoard();

        reduceQuadrant(11,11);

    }

    public boolean verifyCaptureKing(int currentMoveY, int currentMoveX, int player){
        int newMoveY = currentMoveY - 6;
        int newMoveX = currentMoveX - 6;
        int[][] reducedQuad = new int[13][13];

        for (int i = 0; i < boardArray.length; i++)
            for (int j = 0; j < boardArray.length; j++)
                reducedQuad[i][j] = boardArray[i][j];

        for(int i = 0; i < 4; i++){
            rotate90Degree(reducedQuad);
            if(reducedQuad[currentMoveY][currentMoveX] != boardArray[currentMoveY][currentMoveX])
                return false;
        }



        /*for(int i = 0; i < 3; i++){
            newMoveY = newMoveY * (int)Math.cos(Math.toRadians(90)) + newMoveX * (int)Math.sin(Math.toRadians(90));
            newMoveX = newMoveX * (int)Math.cos(Math.toRadians(90)) - newMoveY * (int)Math.sin(Math.toRadians(90));

            if(boardArray[newMoveY+6][newMoveX+6] != player)
                return false;
        }*/

        System.out.println("Symmetry found!");

        return true;
    }

    public boolean makeMove(int actualY, int actualX, int newY, int newX, int player){
        return true;
    }

    public int[][] reduceQuadrant(int currentY, int currentX){
        int [][] reducedQuad = new int[13][13];
        int quadrant = solveQuad(currentY, currentX);
        int alpha = 0;

        if(quadrant == 0){
            System.out.println("Error! Something went wrong with solveQuad!");
            System.exit(-1);
        }

        for (int i = 0; i < boardArray.length; i++)
            for (int j = 0; j < boardArray.length; j++)
                reducedQuad[i][j] = boardArray[i][j];

        for(int i = 0; i < quadrant; i++)
            rotate90Degree(reducedQuad);

        System.out.println();

        for (int i = 0; i < 13; i++){
            for(int j = 0; j < 13; j++)
                System.out.print(reducedQuad[i][j] + " ");
            System.out.println();
        }

        return reducedQuad;
    }

    private static void transpose(int[][] m) {

        for (int i = 0; i < m.length; i++) {
            for (int j = i; j < m[0].length; j++) {
                int x = m[i][j];
                m[i][j] = m[j][i];
                m[j][i] = x;
            }
        }
    }

    private void rotate90Degree(int[][] rotatedQuad) {
        transpose(rotatedQuad);

        for (int  j = 0; j < rotatedQuad[0].length/2; j++) {
            for (int i = 0; i < rotatedQuad.length; i++) {
                int x = rotatedQuad[i][j];
                rotatedQuad[i][j] = rotatedQuad[i][rotatedQuad[0].length -1 -j];
                rotatedQuad[i][rotatedQuad[0].length -1 -j] = x;
            }
        }
    }


    //sentido anti-horário a começar no canto superior esquerdo
    private int solveQuad(int y, int x) {

        //excepção dos quadrantes genéricos
        if(x < 6 && y < 6)
            return 1;
        else if(x < 6 && y >6)
            return 2;
        else if(x > 6 && y > 6)
            return 3;
        else if(x > 6 && y < 6)
            return 4;

        //excepção para o cruzamento com o rei
        else if(x == 6 && y < 6)
            return 1;
        else if( y == 6 && x < 6)
            return 2;
        else if(x == 6 && y > 6)
            return 3;
        else if(y == 6 && x > 6)
            return 4;

        return 0;
    }

    ArrayList<Pair<Integer,Integer>> possibleMoves(int currentY, int currentX, int player){

        ArrayList<Pair<Integer,Integer>> possibleMoves = new ArrayList<Pair<Integer,Integer>>();
        int[][] reducedQuadrant = reduceQuadrant(currentY, currentX);

        if(currentX == currentY)
            for(int i = currentX; i < 5; i++);

        //Verificação na horizontal da posição atual para a frente.
        /*for(int i = currentX+1; i < boardArray[currentY].length; i++){
            if(getBoardArray()[currentY][i] == 0)
                possibleMoves.add(new Pair<Integer, Integer>(currentY, i));
            else
                break;
        }

        //Verificação na horizontal da posição atual para a trás.
        for(int i = currentX-1; i < boardArray[currentY].length; i--){
            if(getBoardArray()[currentY][i] == 0)
                possibleMoves.add(new Pair<Integer, Integer>(currentY, i));
            else
                break;
        }

        //Verificação na vertical da posição atual para baixo.
        for(int i = currentY+1; i < boardArray.length; i++){
            if(getBoardArray()[i][currentX] == 0)
                possibleMoves.add(new Pair<Integer, Integer>(i, currentX));
            else
                break;
        }

        //Verificação na vertical da posição atual para cima.
        for(int i = currentY-1; i >= 0; i--){
            if(getBoardArray()[i][currentX] == 0)
                possibleMoves.add(new Pair<Integer, Integer>(i, currentX));
            else
                break;
        }*/

        return possibleMoves;
    }

    public void printBoard(){
        for (int i = 0; i < 13; i++){
            for(int j = 0; j < 13; j++)
                System.out.print(boardArray[i][j] + " ");
            System.out.println();
        }
    }

}
