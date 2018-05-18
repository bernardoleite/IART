package view;

import javafx.util.Pair;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Ai {

    private boolean endOfDepth = false;

    public class BestMove{
        private Pair<Integer, Integer> originMove;
        private Pair<Integer, Integer> newMove;

        public BestMove(){}

        public BestMove(Pair<Integer, Integer> originMove, Pair<Integer, Integer> newMove){
            this.originMove = originMove;
            this.newMove = newMove;
        }

        public Pair<Integer, Integer> getOriginMove() {
            return originMove;
        }

        public Pair<Integer, Integer> getNewMove() {
            return newMove;
        }

        public void printBestMove(){
            System.out.println("Best move is combo (y,x):");
            System.out.println("Origin Move: (" + originMove.getKey() + "," + originMove.getValue() + ")");
            System.out.println("New Best Move: (" + newMove.getKey() + "," + newMove.getValue() + ")");
        }
    }

    private int mode = 0; //0 for ofensive mode; 1 for defensive mode;
    private double pieceCaptureValue = 0.0;
    private double kingCaptureValue = 0.0;
    private double blockKingCaptureValue = 0.0;
    private double blockPieceCaptureValue = 0.0;

    private GameLogic game;

    Ai(GameLogic game){
        this.game = game;
    }

    public void executeHeuristic(int y, int x, int player){
        calculateKingCaptureValue(y,x,player);
        calculateBlockKingCaptureValue(y,x,player);
        calculatePieceCaptureValue(y,x,player);
        calculateBlockPieceCaptureValue(y,x,player);
    }

    public void calculateKingCaptureValue(int y, int x, int player){
        if(game.verifyCaptureKing(y,x,player))
            kingCaptureValue = 1.0;
        else
            kingCaptureValue = 0.0;
    }

    public void calculateBlockKingCaptureValue(int y, int x, int player){
        if(game.verifyIfKingCaptureWasBlocked(y,x,player))
            blockKingCaptureValue = 1.0;
        else
            blockKingCaptureValue = 0.0;
    }

    public void calculatePieceCaptureValue(int y, int x, int player){
        pieceCaptureValue = game.catchPieces(y,x,player).size() / 6.0;
    }

    public void calculateBlockPieceCaptureValue(int y, int x, int player){
        blockPieceCaptureValue = game.verifyIfPieceCaptureWasBlocked(y,x,player).size() / 6.0;
    }

    public double getDefensiveHeuristic(int player){

        /*System.out.println("Defensive Heuristic Values");
        System.out.println("kingCaptureValue: " + kingCaptureValue);
        System.out.println("blockKingCaptureValue: " + blockKingCaptureValue);
        System.out.println("pieceCaptureValue: " + pieceCaptureValue);
        System.out.println("blockPieceCaptureValue: " + blockPieceCaptureValue);
        System.out.println();*/

        /*if(game.verifyVictoryOrDrawState(player) == 1)
            return 1.0;
        else if(game.verifyVictoryOrDrawState(player) == -1)
            return -2.0;
        else if(game.verifyDefeat(player))
            return -1.0;
        else{*/
            return blockKingCaptureValue * 0.4 + kingCaptureValue * 0.3 + pieceCaptureValue * 0.10 + blockPieceCaptureValue * 0.20;
        //}
    }

    public double getOfensiveHeuristic(int player){

        /*System.out.println("Ofensive Heuristic Values");
        System.out.println("kingCaptureValue: " + kingCaptureValue);
        System.out.println("blockKingCaptureValue: " + blockKingCaptureValue);
        System.out.println("pieceCaptureValue: " + pieceCaptureValue);
        System.out.println("blockPieceCaptureValue: " + blockPieceCaptureValue);
        System.out.println();

        if(game.verifyVictoryOrDrawState(player) == 1)
            return 1.0;
        else if(game.verifyVictoryOrDrawState(player) == -1)
            return -2.0;
        else if(game.verifyDefeat(player))
            return -1.0;
        else*/
            return kingCaptureValue * 0.4 + blockKingCaptureValue * 0.3 + pieceCaptureValue * 0.20 + blockPieceCaptureValue * 0.10;
    }

    public double minimax( Pair<Integer, Integer> possibleMove, int player, int depth, boolean isMax){

        if(possibleMove !=  null){
            executeHeuristic(possibleMove.getKey(), possibleMove.getValue(), player);
        }

            double heuristicValue = getOfensiveHeuristic(player);

            if(heuristicValue == -1.0 || heuristicValue == 1.0 || heuristicValue == -2.0)
                return heuristicValue;



            if(depth >= 1){
                return heuristicValue;
            }


        if(isMax){

            double best = -3.0;

            ArrayList<Pair<Integer,Integer>> currentPossibleMoves;

            //for das possible moves do jogador atual
            for(int i = 0; i < game.getBoardArray().length; i++){
                for(int j = 0; j < game.getBoardArray()[i].length; j++){
                    if(game.getBoardArray()[i][j] == player){
                        currentPossibleMoves = game.possibleMoves(i,j,player);
                        for (int k = 0; k < currentPossibleMoves.size(); k++){

                            int [][] boardGameCopy = game.matrixDeepCopy(game.getBoardArray());

                            //make move
                            game.makeMove(i, j, currentPossibleMoves.get(k).getKey(), currentPossibleMoves.get(k).getValue(), player);

                            /*if(depth < 1){
                                System.out.println();
                                System.out.println("Player: " + player);
                                System.out.println("Minimax Working.");
                                System.out.println("Possible move is:");
                                System.out.println("Origin Move: (" + i + "," + j + ")");
                                System.out.println("Possible Move analized: (" + currentPossibleMoves.get(k).getKey() + "," + currentPossibleMoves.get(k).getValue() + ")");

                                System.out.println();
                                game.printBoard();
                                System.out.println();
                            }*/

                            if(player == 1)
                                best =  Math.max(best, minimax(currentPossibleMoves.get(k), 2, depth + 1, !isMax));
                            else
                                best =  Math.max(best, minimax(currentPossibleMoves.get(k), 1, depth + 1, !isMax));

                            //System.out.println("Best: " + best);

                            //undo move
                            game.setNewBoardMatrix(boardGameCopy);


                        }
                    }
                }
            }
            return best;



        }
        else{



            double best = 3.0;

            ArrayList<Pair<Integer,Integer>> currentPossibleMoves;

            //for das possible moves do jogador atual
            for(int i = 0; i < game.getBoardArray().length; i++){
                for(int j = 0; j < game.getBoardArray()[i].length; j++){
                    if(game.getBoardArray()[i][j] == player){
                        currentPossibleMoves = game.possibleMoves(i,j,player);
                        for (int k = 0; k < currentPossibleMoves.size(); k++){

                            int [][] boardGameCopy = game.matrixDeepCopy(game.getBoardArray());

                            //make move
                            game.makeMove(i, j, currentPossibleMoves.get(k).getKey(), currentPossibleMoves.get(k).getValue(), player);

                            /*if(depth < 1) {
                                System.out.println();
                                System.out.println("Player:" + player);
                                System.out.println("Minimax Working.");
                                System.out.println("Possible move is:");
                                System.out.println("Origin Move: (" + i + "," + j + ")");
                                System.out.println("Possible Move analized: (" + currentPossibleMoves.get(k).getKey() + "," + currentPossibleMoves.get(k).getValue() + ")");
                                System.out.println();
                                game.printBoard();
                                System.out.println();
                            }*/


                            if(player == 1)
                                {best =  Math.min(best, minimax(null, 2, depth + 1, !isMax));}
                            else
                                {best =  Math.min(best, minimax(null, 1, depth + 1, !isMax));}

                            //System.out.println("Best: " + best);


                            //undo move
                            game.setNewBoardMatrix(boardGameCopy);

                        }
                    }
                }
            }

            return best;
        }
    }

    public BestMove findBestMove(int player){

        ArrayList<Pair<Integer,Integer>> currentPossibleMoves;
        Pair<Integer,Integer> currentPiece = new Pair<>(-1,-1);
        Pair<Integer,Integer> bestPieceMove = new Pair<>(-1,-1);
        double currentValue;
        double bestValue = -1.0;

        for(int i = 0; i < game.getBoardArray().length; i++){
            loop:
            for(int j = 0; j < game.getBoardArray()[i].length; j++){
                if(game.getBoardArray()[i][j] == player){
                    currentPossibleMoves = game.possibleMoves(i,j,player);
                    for (int k = 0; k < currentPossibleMoves.size(); k++){
                        int [][] boardGameCopy = game.matrixDeepCopy(game.getBoardArray());

                        //make move
                        game.makeMove(i, j, currentPossibleMoves.get(k).getKey(), currentPossibleMoves.get(k).getValue(), player);


                        if(player == 2)
                            currentValue = minimax(currentPossibleMoves.get(k), 1, 0, false);
                        else
                            currentValue = minimax(currentPossibleMoves.get(k), 2, 0, false);


                        /*System.out.println("Possible move: (" + currentPossibleMoves.get(k).getKey() + "," + currentPossibleMoves.get(k).getValue() + ")");
                        System.out.println("Possible best Value: " + currentValue);*/


                        //undo move
                        game.setNewBoardMatrix(boardGameCopy);

                        /*if(k > 0)
                            break loop;*/


                        if(currentValue == 1.0)
                        {
                            currentPiece = new Pair<>(i,j);
                            bestPieceMove = currentPossibleMoves.get(k);
                            bestValue = currentValue;

                            System.out.println("BestValue (Win Condition): " + bestValue);
                            return new BestMove(currentPiece, bestPieceMove);

                        }
                        else if(currentValue > bestValue){
                            currentPiece = new Pair<>(i,j);
                            bestPieceMove = currentPossibleMoves.get(k);
                            bestValue = currentValue;
                        }
                    }
                }
            }
        }

        System.out.println("BestValue: " + bestValue);
        return new BestMove(currentPiece, bestPieceMove);

    }
}
