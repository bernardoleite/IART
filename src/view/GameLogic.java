package view;

import javafx.util.Pair;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

	private int size;
	private int colorDelta;
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

    GameLogic(int size){
    	this.size = size;
    	this.colorDelta = (13-size)/2;
    	
        boardArray = new int[this.size][this.size];

        //Random Start the Board
        Random random = new Random();
        for(int i = 0; i < this.size; i++){
            int rng = random.nextInt(2);
            if(rng == 0){
            	boardArray[i][0] = 2;
            	boardArray[this.size-1-i][this.size-1] = 1;
            }else{
            	boardArray[i][0] = 1;
            	boardArray[this.size-1-i][this.size-1] = 2;
            }
        }
        for(int i = 1; i < this.size-1; i++){
            int rng = random.nextInt(2);
            if(rng == 0){
            	boardArray[0][i] = 2;
            	boardArray[this.size-1][this.size-1-i] = 1;
            }else{
            	boardArray[0][i] = 1;
            	boardArray[this.size-1][this.size-1-i] = 2;
            }
        }

        //função responsável por realizar todos os testes da lógica
        testes();

    }

    public void testes(){
        //TESTE DE UM ESTADO FINAL
        /*
            boardArray = new int[13][13];
            boardArray[8][0] = 1;
            boardArray[8][1] = 1;
            boardArray[3][2] = 2;

            boardArray[3][3] = 2;
            boardArray[4][3] = 2;
            boardArray[5][3] = 2;
            boardArray[6][3] = 1;
            boardArray[7][3] = 1;
            boardArray[8][3] = 1;
            boardArray[9][3] = 1;

            boardArray[4][4] = 2;
            boardArray[5][4] = 2;
            boardArray[6][4] = 1;
            boardArray[7][4] = 2;
            boardArray[8][4] = 1;
            boardArray[9][4] = 1;

            boardArray[3][5] = 2;
            boardArray[4][5] = 2;
            boardArray[5][5] = 2;
            boardArray[6][5] = 1;
            boardArray[7][5] = 2;
            boardArray[8][5] = 1;
            boardArray[9][5] = 1;

            boardArray[3][6] = 2;
            boardArray[4][6] = 2;
            boardArray[5][6] = 2;
            boardArray[6][6] = 1;
            boardArray[7][6] = 1;
            boardArray[8][6] = 2;
            boardArray[9][6] = 1;

            boardArray[3][7] = 2;
            boardArray[4][7] = 1;
            boardArray[5][7] = 2;
            boardArray[6][7] = 2;
            boardArray[7][7] = 2;
            boardArray[8][7] = 1;
            boardArray[9][7] = 1;

            boardArray[4][8] = 2;
            boardArray[5][8] = 1;
            boardArray[7][8] = 1;
            boardArray[8][8] = 1;
            boardArray[9][8] = 1;

            boardArray[3][9] = 2;
            boardArray[6][9] = 1;
            boardArray[9][9] = 1;

            boardArray[10][10] = 1;
            boardArray[11][10] = 1;
            boardArray[10][11] = 1;
            boardArray[10][12] = 1;
         */


        //verifica que apanhou uma peça na horizontal
        /*
            boardArray[1][1] = 2;
            boardArray[1][2] = 1;
            boardArray[1][3] = 2;
            printCaughtPieces(catchPiece(1,1,2));
        */


        //verifica que apanhou uma peça na diagonal
        /*
            boardArray[2][2] = 2;
            boardArray[3][3] = 1;
            boardArray[4][4] = 2;
            printCaughtPieces(catchPiece(2,2,2));
         */


        //teste de simetria
        /*
            boardArray[1][2] = 2;
            boardArray[this.size-3][1] = 2;
            boardArray[this.size-2][this.size-3] = 2;
            boardArray[2][this.size-2] = 2;
            verifyCaptureKing(1,2,2);
        */


        //verifica se a jogada bloqueia uma possivel captura do trono
        /*
            boardArray[this.size-3][1] = 2;
            boardArray[this.size-2][this.size-3] = 2;
            boardArray[2][this.size-2] = 2;
            if(verifyifKingWasBlocked(1,2, 2))
                System.out.println("Sim, bloqueia um rei.");
        */


        //verifica se a jogada bloqueia uma possivel captura de peça
        /*
            boardArray[2][2] = 2;
            boardArray[2][3] = 1;
            if(verifyIfPieceCaptureWasBlocked(2,4,1))
                System.out.println("Sim, bloqueia uma peça.");
        */


        //teste de print de jogadas possiveis
        /*
            printPossibleMoves(possibleMoves(0,0,1));
        */

        //AI Heuristic test for pieceCapture
        /*
            Ai ai = new Ai(this);

            boardArray[1][11] = 2;
            boardArray[3][9] = 2;
            boardArray[4][8] = 1;
            printCaughtPieces(catchPieces(2,10,1));

            ai.executeHeuristic(2,10,1);
            System.out.println("Ofensive Heuristic Value: " + String.format( "%.5f", ai.getOfensiveHeuristic()));
            System.out.println("Defensive Heuristic Value: " + String.format( "%.5f",ai.getDefensiveHeuristic()));
        */

        //AI Heuristic test for blockKingCapture
        /*
            Ai ai = new Ai(this);

            boardArray[this.size-3][1] = 2;
            boardArray[this.size-2][this.size-3] = 2;
            boardArray[2][this.size-2] = 2;

            ai.executeHeuristic(1,2, 1);
            System.out.println("Ofensive Heuristic Value: " + String.format( "%.5f", ai.getOfensiveHeuristic()));
            System.out.println("Defensive Heuristic Value: " + String.format( "%.5f",ai.getDefensiveHeuristic()));
        */

        //AI Heuristic test for KingCapture
        /*
        Ai ai = new Ai(this);

            boardArray[this.size-3][1] = 2;
            boardArray[this.size-2][this.size-3] = 2;
            boardArray[2][this.size-2] = 2;

            ai.executeHeuristic(1,2, 2);
            System.out.println("Ofensive Heuristic Value: " + String.format( "%.5f", ai.getOfensiveHeuristic()));
            System.out.println("Defensive Heuristic Value: " + String.format( "%.5f",ai.getDefensiveHeuristic()));
        */

        //AI Heuristic test for blockPieceCapture
        /*
            Ai ai = new Ai(this);

            boardArray[2][2] = 2;
            boardArray[2][3] = 1;

            ai.executeHeuristic(2,4, 1);
            System.out.println("Ofensive Heuristic Value: " + String.format( "%.5f", ai.getOfensiveHeuristic()));
            System.out.println("Defensive Heuristic Value: " + String.format( "%.5f",ai.getDefensiveHeuristic()));
         */


        //teste de printar a board para verificar o inicio random
        printBoard();
    }

    public void setBoardArray(int y, int x, int player){
        boardArray[y][x] = player;
    }

    public boolean verifyCaptureKing(int currentMoveY, int currentMoveX, int player){
        int newMoveY = currentMoveY - 6;
        int newMoveX = currentMoveX - 6;
        int[][] reducedQuad = new int[size][size];

        //Isto faz uma deep Copy do tabuleiro para não ficar alterado.
        for (int i = 0; i < boardArray.length; i++)
            for (int j = 0; j < boardArray.length; j++)
                reducedQuad[i][j] = boardArray[i][j];

        for(int i = 0; i < 3; i++){
            rotate90Degree(reducedQuad);
            if(reducedQuad[currentMoveY][currentMoveX] != player){
                System.out.println("entra");
                return false;
            }
        }

        System.out.println("Symmetry found!");

        return true;
    }

    public boolean makeMove(int actualY, int actualX, int newY, int newX, int player){
    	
    	if(actualY < 0 || actualY > size - 1)
    		return false;
    	if(actualX < 0 || actualX > size - 1)
    		return false;
    	if(newY < 0 || newY > size - 1)
    		return false;
    	if(newX < 0 || newX > size - 1)
    		return false;
    	
        boardArray[actualY][actualX] = 0;
        boardArray[newY][newX] = player;
        return true;
    }

    /*public int[][] reduceQuadrant(int currentY, int currentX){
        int [][] reducedQuad = new int[size][size];
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

        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++)
                System.out.print(reducedQuad[i][j] + " ");
            System.out.println();
        }

        return reducedQuad;
    }*/

    //sentido anti-horário a começar no canto superior esquerdo
    /*private int solveQuad(int y, int x) {

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
    }*/

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
        int currentColor = colorSchemaBoardArray[colorDelta + currentY][colorDelta + currentX];

        //todos os comentarios estão em conta que a peça se encontra no 1º quadrante
        //Verificação na horizontal da posição atual para a frente.
        if(currentX < 12)
            for(int i = currentX+1; i < boardArray[currentY].length; i++){
                if(getBoardArray()[currentY][i] == 0)
                    if(colorSchemaBoardArray[colorDelta + currentY][colorDelta + i] > currentColor)
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
                    if(colorSchemaBoardArray[colorDelta + currentY][colorDelta + i] > currentColor)
                        possibleMoves.add(new Pair<Integer, Integer>(currentY, i));
                    else
                        break;
                else
                    break;
            }

        //Verificação na vertical da posição atual para baixo.
        if(currentY < size-1)
            for(int i = currentY+1; i < boardArray.length; i++){
                if(getBoardArray()[i][currentX] == 0)
                    if(colorSchemaBoardArray[colorDelta + i][colorDelta + currentX] > currentColor)
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
                    if(colorSchemaBoardArray[colorDelta + i][colorDelta + currentX] > currentColor)
                        possibleMoves.add(new Pair<Integer, Integer>(i, currentX));
                    else
                        break;
                else
                    break;
            }

        //Verificação diagonal cima para a direita (-1 em x) (+1 em y)
        if(currentY > 0 && currentX < size-1)
            for(int i = 1; i >= 0; i++){
                if(getBoardArray()[currentY-i][currentX+i] == 0)
                    if(colorSchemaBoardArray[colorDelta + currentY-i][colorDelta + currentX+i] > currentColor)
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
                    if(colorSchemaBoardArray[colorDelta + currentY-i][colorDelta + currentX-i] > currentColor)
                        possibleMoves.add(new Pair<Integer, Integer>(currentY-i, currentX-i));
                    else
                        break;
                else
                    break;
            }

        //Verificação diagonal baixo para a direita (+1 em x) (+1 em y)
        if(currentY < size-1 && currentX < size-1)
            for(int i = 1; i >= 0; i++){
                if(getBoardArray()[currentY+i][currentX+i] == 0)
                    if(colorSchemaBoardArray[colorDelta + currentY+i][colorDelta + currentX+i] > currentColor)
                        possibleMoves.add(new Pair<Integer, Integer>(currentY+i, currentX+i));
                    else
                        break;
                else
                    break;
            }

        //Verificação diagonal baixo para a esquerda (-1 em x) (-1 em y)
        if(currentY < size-1 && currentX > 0)
            for(int i = 1; i >= 0; i++){
                if(getBoardArray()[currentY+i][currentX-i] == 0)
                    if(colorSchemaBoardArray[colorDelta + currentY+i][colorDelta + currentX-i] > currentColor)
                        possibleMoves.add(new Pair<Integer, Integer>(currentY+i, currentX-i));
                    else
                        break;
                else
                    break;
            }

        return possibleMoves;
    }

    public boolean verifyIfKingCaptureWasBlocked(int currentY, int currentX, int player){
        if(player == 1)
            return verifyCaptureKing(currentY, currentX, 2);
        else
            return verifyCaptureKing(currentY, currentX, 1);
    }

    public ArrayList<Pair<Integer,Integer>> verifyIfPieceCaptureWasBlocked(int currentY, int currentX, int player){
        ArrayList<Pair<Integer,Integer>> capturesAvoided;

        if(player == 1)
            capturesAvoided = catchPieces(currentY, currentX, 2);
        else
            capturesAvoided = catchPieces(currentY, currentX, 1);

        return capturesAvoided;
    }

    public  ArrayList<Pair<Integer,Integer>> catchPieces(int currentY, int currentX, int player){
        ArrayList<Pair<Integer,Integer>> eatenPieces = new  ArrayList<Pair<Integer,Integer>>();

        //todos os comentarios estão em conta que a peça se encontra no 1º quadrante
        //Verificação na horizontal da posição atual para a frente.
        if(currentX < size-2)
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
        if(currentY < size-2)
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
        if(currentY > 1 && currentX < size-2)
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
        if(currentY < size-2 && currentX < size-2)
            if(getBoardArray()[currentY+1][currentX+1] != player && getBoardArray()[currentY+1][currentX+1] != 0)
                if(getBoardArray()[currentY+2][currentX+2] == player && getBoardArray()[currentY+2][currentX+2] != 0){
                    eatenPieces.add(new Pair<Integer, Integer>(currentY+1, currentX+1));
                }

        //Verificação diagonal baixo para a esquerda (-1 em x) (-1 em y)
        if(currentY < size-2 && currentX > 1)
            if(getBoardArray()[currentY+1][currentX-1] != player && getBoardArray()[currentY+1][currentX-1] != 0)
                if(getBoardArray()[currentY+2][currentX-2] == player && getBoardArray()[currentY+2][currentX-2] != 0){
                    eatenPieces.add(new Pair<Integer, Integer>(currentY+1, currentX-1));
                }

        return eatenPieces;
    }

    public void printBoard(){
        System.out.println();
        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++)
                System.out.print(boardArray[i][j] + " ");
            System.out.println();
        }
    }

}
