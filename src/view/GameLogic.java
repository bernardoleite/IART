package view;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

    private int [][] boardArray;

    public int[][] getBoardArray() {
        return boardArray;
    }
    public int[][] colorSchemaBoardArray = new int[][]
        {
            {1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,1},
            {1,2,3,3,3,3,3,3,3,3,3,2,1},
            {1,2,3,4,4,4,4,4,4,4,3,2,1},
            {1,2,3,4,5,5,5,5,5,4,3,2,1},
            {1,2,3,4,5,6,6,6,5,4,3,2,1},
            {1,2,3,4,5,6,0,6,5,4,3,2,1},
            {1,2,3,4,5,6,6,6,5,4,3,2,1},
            {1,2,3,4,5,5,5,5,5,4,3,2,1},
            {1,2,3,4,4,4,4,4,4,4,3,2,1},
            {1,2,3,3,3,3,3,3,3,3,3,2,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1}
        };

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

        //verifica que apanhou uma peça na horizontal
        boardArray[1][1] = 2;
        boardArray[1][2] = 1;
        boardArray[1][3] = 2;

        printCaughtPieces(catchPiece(1,1,2));

        //verifica que apanhou uma peça na diagonal
        boardArray[2][2] = 2;
        boardArray[3][3] = 1;
        boardArray[4][4] = 2;

        printCaughtPieces(catchPiece(2,2,2));

        //teste de simetria
        /*boardArray[1][2] = 2;
        boardArray[10][1] = 2;
        boardArray[11][10] = 2;
        boardArray[2][11] = 2;*/
        //verifyCaptureKing(1,2,2);

        //teste de print de jogadas possiveis
        //printPossibleMoves(possibleMoves(0,12,1));

        //teste de printar a board para verificar o inicio random
        printBoard();

    }

    public void setBoardArray(int y, int x, int player){
        boardArray[y][x] = player;
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

    public void printPossibleMoves(ArrayList<Pair<Integer,Integer>> possibleMoves){
        System.out.println();
        for (int i= 0; i < possibleMoves.size(); i++){
            System.out.println("Possible move " + i + ": (" + possibleMoves.get(i).getKey() + "," + possibleMoves.get(i).getValue() + ")");
        }
    }

    public void printCaughtPieces(ArrayList<Pair<Integer,Integer>> CaughtPieces){
        System.out.println();
        for (int i= 0; i < CaughtPieces.size(); i++){
            System.out.println("Catch Piece " + i + ": (" + CaughtPieces.get(i).getKey() + "," + CaughtPieces.get(i).getValue() + ")");
        }
    }

    public ArrayList<Pair<Integer,Integer>> possibleMoves(int currentY, int currentX, int player){

        ArrayList<Pair<Integer,Integer>> possibleMoves = new ArrayList<Pair<Integer,Integer>>();
        int currentColor = colorSchemaBoardArray[currentY][currentX];

        //todos os comentarios estão em conta que a peça se encontra no 1º quadrante
        //Verificação na horizontal da posição atual para a frente.
        if(currentX < 12)
            for(int i = currentX+1; i < boardArray[currentY].length; i++){
                if(getBoardArray()[currentY][i] == 0)
                    if(colorSchemaBoardArray[currentY][i] > currentColor)
                        possibleMoves.add(new Pair<Integer, Integer>(currentY, i));
                    else
                        break;
                else
                    break;
            }

        //Verificação na horizontal da posição atual para a trás.
        if(currentX > 0)
            for(int i = currentX-1; i < boardArray[currentY].length; i--){
                if(getBoardArray()[currentY][i] == 0)
                    if(colorSchemaBoardArray[currentY][i] > currentColor)
                        possibleMoves.add(new Pair<Integer, Integer>(currentY, i));
                    else
                        break;
                else
                    break;
            }

        //Verificação na vertical da posição atual para baixo.
        if(currentY < 12)
            for(int i = currentY+1; i < boardArray.length; i++){
                if(getBoardArray()[i][currentX] == 0)
                    if(colorSchemaBoardArray[i][currentX] > currentColor)
                        possibleMoves.add(new Pair<Integer, Integer>(i, currentX));
                    else
                        break;
                else
                    break;
            }

        //Verificação na vertical da posição atual para cima.
        if(currentY > 0)
            for(int i = currentY-1; i >= 0; i--){
                if(getBoardArray()[i][currentX] == 0)
                    if(colorSchemaBoardArray[i][currentX] > currentColor)
                        possibleMoves.add(new Pair<Integer, Integer>(i, currentX));
                    else
                        break;
                else
                    break;
            }

        //Verificação diagonal cima para a direita (-1 em x) (+1 em y)
        if(currentY > 0 && currentX < 12)
            for(int i = 1; i >= 0; i++){
                if(getBoardArray()[currentY-i][currentX+i] == 0)
                    if(colorSchemaBoardArray[currentY-i][currentX+i] > currentColor)
                        possibleMoves.add(new Pair<Integer, Integer>(currentY-i, currentX+i));
                    else
                        break;
                else
                    break;
            }

        //Verificação diagonal cima para a esquerda (-1 em x) (-1 em y)
        if(currentY > 0 && currentX > 0)
            for(int i = 1; i >= 0; i++){
                if(getBoardArray()[currentY-i][currentX-i] == 0)
                    if(colorSchemaBoardArray[currentY-i][currentX-i] > currentColor)
                        possibleMoves.add(new Pair<Integer, Integer>(currentY-i, currentX-i));
                    else
                        break;
                else
                    break;
            }

        //Verificação diagonal baixo para a direita (+1 em x) (+1 em y)
        if(currentY < 12 && currentX < 12)
            for(int i = 1; i >= 0; i++){
                if(getBoardArray()[currentY+i][currentX+i] == 0)
                    if(colorSchemaBoardArray[currentY+i][currentX+i] > currentColor)
                        possibleMoves.add(new Pair<Integer, Integer>(currentY+i, currentX+i));
                    else
                        break;
                else
                    break;
            }

        //Verificação diagonal baixo para a esquerda (-1 em x) (-1 em y)
        if(currentY < 12 && currentX > 0)
            for(int i = 1; i >= 0; i++){
                if(getBoardArray()[currentY+i][currentX-i] == 0)
                    if(colorSchemaBoardArray[currentY+i][currentX-i] > currentColor)
                        possibleMoves.add(new Pair<Integer, Integer>(currentY+i, currentX-i));
                    else
                        break;
                else
                    break;
            }

        return possibleMoves;
    }

    public  ArrayList<Pair<Integer,Integer>> catchPiece(int currentY, int currentX, int player){
        ArrayList<Pair<Integer,Integer>> eatenPieces = new  ArrayList<Pair<Integer,Integer>>();

        //todos os comentarios estão em conta que a peça se encontra no 1º quadrante
        //Verificação na horizontal da posição atual para a frente.
        if(currentX < 11)
            if(getBoardArray()[currentY][currentX+1] != player && getBoardArray()[currentY][currentX+1] != 0)
                if(getBoardArray()[currentY][currentX+2] == player && getBoardArray()[currentY][currentX+2] != 0){
                    eatenPieces.add(new Pair<Integer, Integer>(currentY, currentX+1));
                }


        //Verificação na horizontal da posição atual para a trás.
        if(currentX > 1)
            if(getBoardArray()[currentY][currentX-1] != player && getBoardArray()[currentY][currentX-1] != 0)
                if(getBoardArray()[currentY][currentX-2] == player && getBoardArray()[currentY][currentX-2] != 0){
                    eatenPieces.add(new Pair<Integer, Integer>(currentY, currentX-1));
                }

        //Verificação na vertical da posição atual para baixo.
        if(currentY < 11)
            if(getBoardArray()[currentY+1][currentX] != player && getBoardArray()[currentY+1][currentX] != 0)
                if(getBoardArray()[currentY+2][currentX] == player && getBoardArray()[currentY+2][currentX] != 0){
                    eatenPieces.add(new Pair<Integer, Integer>(currentY+1, currentX));
                }

        //Verificação na vertical da posição atual para cima.
        if(currentY > 1)
            if(getBoardArray()[currentY-1][currentX] != player && getBoardArray()[currentY-1][currentX] != 0)
                if(getBoardArray()[currentY-2][currentX] == player && getBoardArray()[currentY-2][currentX] != 0){
                    eatenPieces.add(new Pair<Integer, Integer>(currentY-1, currentX));
                }

        //Verificação diagonal cima para a direita (-1 em x) (+1 em y)
        if(currentY > 1 && currentX < 11)
            if(getBoardArray()[currentY-1][currentX+1] != player && getBoardArray()[currentY-1][currentX+1] != 0)
                if(getBoardArray()[currentY-2][currentX+2] == player && getBoardArray()[currentY-2][currentX+2] != 0){
                    eatenPieces.add(new Pair<Integer, Integer>(currentY-1, currentX+1));
                }

        //Verificação diagonal cima para a esquerda (-1 em x) (-1 em y)
        if(currentY > 1 && currentX > 1)
            if(getBoardArray()[currentY-1][currentX-1] != player && getBoardArray()[currentY-1][currentX-1] != 0)
                if(getBoardArray()[currentY-2][currentX-2] == player && getBoardArray()[currentY-2][currentX-2] != 0){
                    eatenPieces.add(new Pair<Integer, Integer>(currentY-1, currentX-1));
                }

        //Verificação diagonal baixo para a direita (+1 em x) (+1 em y)
        if(currentY < 11 && currentX < 11)
            if(getBoardArray()[currentY+1][currentX+1] != player && getBoardArray()[currentY+1][currentX+1] != 0)
                if(getBoardArray()[currentY+2][currentX+2] == player && getBoardArray()[currentY+2][currentX+2] != 0){
                    eatenPieces.add(new Pair<Integer, Integer>(currentY+1, currentX+1));
                }

        //Verificação diagonal baixo para a esquerda (-1 em x) (-1 em y)
        if(currentY < 11 && currentX > 1)
            if(getBoardArray()[currentY+1][currentX-1] != player && getBoardArray()[currentY+1][currentX-1] != 0)
                if(getBoardArray()[currentY+2][currentX-2] == player && getBoardArray()[currentY+2][currentX-2] != 0){
                    eatenPieces.add(new Pair<Integer, Integer>(currentY+1, currentX-1));
                }

        return eatenPieces;
    }

    public void printBoard(){
        System.out.println();
        for (int i = 0; i < 13; i++){
            for(int j = 0; j < 13; j++)
                System.out.print(boardArray[i][j] + " ");
            System.out.println();
        }
    }

}
