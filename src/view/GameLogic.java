package view;

import javafx.util.Pair;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

	private int size = 12;
	private int colorDelta;
    private int [][] boardArray;
    private int king = 0;

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

    public int getKing(){
        return king;
    }

    public void setKing(int player){
        if(player == 1)
            king = 3;
        else
            king = 4;
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
            printCaughtPieces(catchPieces(2,2,2));
        */


        //teste de simetria
            /*boardArray[1][2] = 1;
            //boardArray[this.size-3][1] = 2;
            boardArray[this.size-2][this.size-3] = 1;
            boardArray[2][this.size-2] = 1;
            verifyCaptureKing(1,2,2);*/



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

        //CASO AI: Heuristic test for pieceCapture
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

        //CASO AI: Heuristic test for blockKingCapture
        /*
            Ai ai = new Ai(this);

            boardArray[this.size-3][1] = 2;
            boardArray[this.size-2][this.size-3] = 2;
            boardArray[2][this.size-2] = 2;

            ai.executeHeuristic(1,2, 1);
            System.out.println("Ofensive Heuristic Value: " + String.format( "%.5f", ai.getOfensiveHeuristic()));
            System.out.println("Defensive Heuristic Value: " + String.format( "%.5f",ai.getDefensiveHeuristic()));
        */

        //CASO AI: Heuristic test for KingCapture
        /*
        Ai ai = new Ai(this);

            boardArray[this.size-3][1] = 2;
            boardArray[this.size-2][this.size-3] = 2;
            boardArray[2][this.size-2] = 2;

            ai.executeHeuristic(1,2, 2);
            System.out.println("Ofensive Heuristic Value: " + String.format( "%.5f", ai.getOfensiveHeuristic()));
            System.out.println("Defensive Heuristic Value: " + String.format( "%.5f",ai.getDefensiveHeuristic()));
        */

        //CASO AI: Heuristic test for blockPieceCapture
        /*
            boardArray[2][3] = 2;
            boardArray[2][4] = 1;
            boardArray[2][5] = 1;

            Ai ai = new Ai(this);
            ai.executeHeuristic(2,5, 1);
            System.out.println("Ofensive Heuristic Value: " + ai.getOfensiveHeuristic(1));
            System.out.println("Defensive Heuristic Value: " + ai.getDefensiveHeuristic(1));
        */


        //CASO AI: Heuristic test for begin of the Game
        /*
            Ai ai = new Ai(this);

            ai.executeHeuristic(2,4, 1);
            System.out.println("Ofensive Heuristic Value: " + String.format( "%.5f", ai.getOfensiveHeuristic(1)));
            System.out.println("Defensive Heuristic Value: " + String.format( "%.5f",ai.getDefensiveHeuristic(1)));
        */

        /* CASO AI: Captura de uma peça do adversário que leva a uma perda da mesma peça que a capturou na jogada seguinte
         *
         * Num caso em que a depth é menor que 3, é natural que a AI escolha a pos(y,x)->(3,5) pois pode capturar a peça do adversário.
         * No entanto, considerando que a depth é maior que 3, e colocando a peça branca nesta posição, é possivel observar que na proxima
         * jogada que o adversário faz, o mesmo pode come-la, e portanto a AI evita essa casa,
         * escolhendo outra. Neste caso, a posição pos(y,x)->(9,3) ou pos(y,x)->(9,9) são as escolhidas pois
         * são jogadas que à posteriori irão capturar o trono (é possivel saber observado as pos(y,x)->(3,3) e pos(y,x)->(3,9)).
         */
            /*boardArray[3][9] = 2;
            boardArray[3][4] = 1;
            boardArray[4][6] = 1;
            boardArray[3][3] = 2;*/



        /* CASO AI: Capturar uma peça ou duas? Qual a melhor jogada?
        *
        * Como a captura de duas peças tem maior valor na euristica que capturar uma, a ai opta por
        * escolher a pos(y,x)->(5,9) ao invés da pos(y,x)->(3,5).
        *
            boardArray[3][9] = 1;
            boardArray[3][4] = 2;
            boardArray[4][9] = 2;
            boardArray[3][3] = 1;
            boardArray[6][9] = 2;
            boardArray[7][9] = 2;
        */




        //AI CALL
        /*
            Ai ai = new Ai(this);

            printBoard();
            System.out.println();

            Ai.BestMove bestMove = ai.findBestMove(1, true, 1);
           bestMove.printBestMove();
       */
    }

    public void setNewBoardMatrix(int[][] newMatrix){
        this.boardArray = newMatrix;
    }

    public void setBoardArray(int y, int x, int player){
        boardArray[y][x] = player;
    }

    public int[][] matrixDeepCopy(int[][] matrix){
        int[][] newMatrix = new int[size][size];

        //Isto faz uma deep Copy do tabuleiro para não ficar alterado.
        for (int i = 0; i < boardArray.length; i++)
            for (int j = 0; j < boardArray.length; j++)
                newMatrix[i][j] = boardArray[i][j];

        return newMatrix;
    }

    void makeRandomMove(int player){

        Random rand = new Random();

        int rndY = rand.nextInt((0 - 12) + 1);
        int rndX = rand.nextInt((0 - 12) + 1);

        while(this.boardArray[rndY][rndX] != player){
            rndY = rand.nextInt((0 - 12) + 1);
            rndX = rand.nextInt((0 - 12) + 1);
        }

        ArrayList<Pair<Integer,Integer>>  possibleMoves = possibleMoves(rndY, rndX, player);

        int rndMove = rand.nextInt((0 - possibleMoves.size()) + 1);

        makeMove(rndY, rndX, possibleMoves.get(rndMove).getKey(), possibleMoves.get(rndMove).getValue(), player);

    }

    public boolean verifyCaptureKing(int currentMoveY, int currentMoveX, int player){
        int newMoveY = currentMoveY - 6;
        int newMoveX = currentMoveX - 6;
        int[][] reducedQuad;

        reducedQuad = matrixDeepCopy(boardArray);

        for(int i = 0; i < 3; i++){
            rotate90Degree(reducedQuad);
            if(reducedQuad[currentMoveY][currentMoveX] != player){
                return false;
            }
        }

        return true;
    }

    public int verifyHeuristicCaptureKing(int currentMoveY, int currentMoveX, int player){
        if(boardArray[currentMoveY][currentMoveX] == player){
            return 0;
        }

        int newMoveY = currentMoveY - 6;
        int newMoveX = currentMoveX - 6;
        int[][] reducedQuad;

        reducedQuad = matrixDeepCopy(boardArray);

        for(int i = 0; i < 3; i++){
            rotate90Degree(reducedQuad);
            if(reducedQuad[currentMoveY][currentMoveX] != player){
                //System.out.println("entra");
                return i+1;
            }
        }

        return 4;
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
        ArrayList<Pair<Integer,Integer>> capturesAvoided = new  ArrayList<>();

        //todos os comentarios estão em conta que a peça se encontra no 1º quadrante
        //Verificação na horizontal da posição atual para a frente.
        if(getBoardArray()[currentY][currentX] != player)
            return capturesAvoided;

        if(currentX < size-2)
            if(getBoardArray()[currentY][currentX+1] == player && getBoardArray()[currentY][currentX+1] != 0)
                if(getBoardArray()[currentY][currentX+2] != player && getBoardArray()[currentY][currentX+2] != 0){
                    capturesAvoided.add(new Pair<Integer, Integer>(currentY, currentX+1));
                }


        //Verificação na horizontal da posição atual para a trás.
        if(currentX > 1)
            if(getBoardArray()[currentY][currentX-1] == player && getBoardArray()[currentY][currentX-1] != 0)
                if(getBoardArray()[currentY][currentX-2] != player && getBoardArray()[currentY][currentX-2] != 0){
                    capturesAvoided.add(new Pair<Integer, Integer>(currentY, currentX-1));
                }

        //Verificação na vertical da posição atual para baixo.
        if(currentY < size-2)
            if(getBoardArray()[currentY+1][currentX] == player && getBoardArray()[currentY+1][currentX] != 0)
                if(getBoardArray()[currentY+2][currentX] != player && getBoardArray()[currentY+2][currentX] != 0){
                    capturesAvoided.add(new Pair<Integer, Integer>(currentY+1, currentX));
                }

        //Verificação na vertical da posição atual para cima.
        if(currentY > 1)
            if(getBoardArray()[currentY-1][currentX] == player && getBoardArray()[currentY-1][currentX] != 0)
                if(getBoardArray()[currentY-2][currentX] != player && getBoardArray()[currentY-2][currentX] != 0){
                    capturesAvoided.add(new Pair<Integer, Integer>(currentY-1, currentX));
                }

        //Verificação diagonal cima para a direita (-1 em x) (+1 em y)
        if(currentY > 1 && currentX < size-2)
            if(getBoardArray()[currentY-1][currentX+1] == player && getBoardArray()[currentY-1][currentX+1] != 0)
                if(getBoardArray()[currentY-2][currentX+2] != player && getBoardArray()[currentY-2][currentX+2] != 0){
                    capturesAvoided.add(new Pair<Integer, Integer>(currentY-1, currentX+1));
                }

        //Verificação diagonal cima para a esquerda (-1 em x) (-1 em y)
        if(currentY > 1 && currentX > 1)
            if(getBoardArray()[currentY-1][currentX-1] == player && getBoardArray()[currentY-1][currentX-1] != 0)
                if(getBoardArray()[currentY-2][currentX-2] != player && getBoardArray()[currentY-2][currentX-2] != 0){
                    capturesAvoided.add(new Pair<Integer, Integer>(currentY-1, currentX-1));
                }

        //Verificação diagonal baixo para a direita (+1 em x) (+1 em y)
        if(currentY < size-2 && currentX < size-2)
            if(getBoardArray()[currentY+1][currentX+1] == player && getBoardArray()[currentY+1][currentX+1] != 0)
                if(getBoardArray()[currentY+2][currentX+2] != player && getBoardArray()[currentY+2][currentX+2] != 0){
                    capturesAvoided.add(new Pair<Integer, Integer>(currentY+1, currentX+1));
                }

        //Verificação diagonal baixo para a esquerda (-1 em x) (-1 em y)
        if(currentY < size-2 && currentX > 1)
            if(getBoardArray()[currentY+1][currentX-1] == player && getBoardArray()[currentY+1][currentX-1] != 0)
                if(getBoardArray()[currentY+2][currentX-2] != player && getBoardArray()[currentY+2][currentX-2] != 0){
                    capturesAvoided.add(new Pair<Integer, Integer>(currentY+1, currentX-1));
                }

        return capturesAvoided;
    }

    public  ArrayList<Pair<Integer,Integer>> catchPieces(int currentY, int currentX, int player){
        ArrayList<Pair<Integer,Integer>> eatenPieces = new  ArrayList<Pair<Integer,Integer>>();

        //todos os comentarios estão em conta que a peça se encontra no 1º quadrante
        //Verificação na horizontal da posição atual para a frente.
        if(getBoardArray()[currentY][currentX] != player)
            return eatenPieces;

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

    public boolean verifyDefeat(int player){
        if(player == 1)
            if(verifyVictoryOrDrawState(2) == 1)
                return true;
            else
                return false;
        else
            if(verifyVictoryOrDrawState(1) == 1)
                return true;
            else
                return false;
    }

    public boolean verifyIfPlayerHasNoPossibleMoves(int player){
        for(int i = 0; i < boardArray.length; i++){
            for (int j = 0; j < boardArray[i].length; j++){
                if(boardArray[i][j] == player){
                    if(possibleMoves(i,j,player).size() > 0)
                        return false;
                }
            }
        }
        return true;
    }

    public int getPlayerKing(int player){
        if(player == 1)
            return 3;
        else
            return 4;
    }

    public int verifyVictoryOrDrawState(int player){
        //1 victory, -1 draw
        if(verifyIfPlayerHasNoPossibleMoves(player))
            if(boardArray[size/2][size/2] == getPlayerKing(player))
                return 1;
            else if(boardArray[size/2][size/2] == 0)
                return -1;

        return 0;
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
