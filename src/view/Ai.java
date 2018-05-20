package view;

import javafx.util.Pair;

import java.util.ArrayList;

public class Ai {

    private boolean endOfDepth = false;

    public class BestMove{
        private Pair<Integer, Integer> originMove;
        private Pair<Integer, Integer> newMove;
        private double bestMoveValue;

        public BestMove(){}

        public double getBestMoveValue(){
            return bestMoveValue;
        }

        public BestMove(Pair<Integer, Integer> originMove, Pair<Integer, Integer> newMove, double bestMoveValue){
            this.originMove = originMove;
            this.newMove = newMove;
            this.bestMoveValue = bestMoveValue;
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
        pieceCaptureValue = (double)game.catchPieces(y,x,player).size() / 6.0;
    }

    public void calculateBlockPieceCaptureValue(int y, int x, int player){
        blockPieceCaptureValue = (double)game.verifyIfPieceCaptureWasBlocked(y,x,player).size() / 6.0;
    }

    public double getDefensiveHeuristic(int player){

        if(game.verifyVictoryOrDrawState(player) == 1)
            return 1.0;
        else if(game.verifyVictoryOrDrawState(player) == -1)
            return -2.0;
        else if(game.verifyDefeat(player))
            return -1.0;
        else{
            return blockKingCaptureValue * 0.4 + kingCaptureValue * 0.3 + pieceCaptureValue * 0.10 + blockPieceCaptureValue * 0.20;
        }
    }

    public double getOfensiveHeuristic(int player){

        if(game.verifyVictoryOrDrawState(player) == 1)
            return 1.0;
        else if(game.verifyVictoryOrDrawState(player) == -1)
            return -2.0;
        else if(game.verifyDefeat(player))
            return -1.0;
        else
            return kingCaptureValue * 0.4 + blockKingCaptureValue * 0.3 + pieceCaptureValue * 0.20 + blockPieceCaptureValue * 0.10;
    }

    public double minimaxPruning(Pair<Integer, Integer> possibleMove, int player, int depth, boolean isMax, double alpha, double beta, int chosenDepth){

        executeHeuristic(possibleMove.getKey(), possibleMove.getValue(), player);
        double heuristicValue;

        if(game.getPlayerKing(player) == game.getKing())
            if(!isMax)
                heuristicValue = getDefensiveHeuristic(player);
            else
                heuristicValue = -getDefensiveHeuristic(player);
        else
            if(!isMax)
                heuristicValue = getOfensiveHeuristic(player);
            else
                heuristicValue = -getOfensiveHeuristic(player);

        if(heuristicValue == -1.0 || heuristicValue == 1.0 || heuristicValue == -2.0)
            return heuristicValue;



        if(depth >= (chosenDepth-1)){
            return heuristicValue;
        }


        if(isMax){

            double best = -3.0;

            ArrayList<Pair<Integer,Integer>> currentPossibleMoves;

            //for das possible moves do jogador atual
            for(int i = 0; i < game.getBoardArray().length; i++){
                loop:
                for(int j = 0; j < game.getBoardArray()[i].length; j++){
                    if(game.getBoardArray()[i][j] == player){
                        currentPossibleMoves = game.possibleMoves(i,j,player);
                        for (int k = 0; k < currentPossibleMoves.size(); k++){

                            int [][] boardGameCopy = game.matrixDeepCopy(game.getBoardArray());

                            //make move
                            game.makeMove(i, j, currentPossibleMoves.get(k).getKey(), currentPossibleMoves.get(k).getValue(), player);

                            if(player == 1)
                                best =  Math.max(best, heuristicValue + minimaxPruning(currentPossibleMoves.get(k), 2, depth + 1, !isMax, alpha, beta, chosenDepth));
                            else
                                best =  Math.max(best, heuristicValue + minimaxPruning(currentPossibleMoves.get(k), 1, depth + 1, !isMax, alpha, beta, chosenDepth));

                            alpha = Math.max( alpha, best);
                            if (beta <= alpha)
                                break loop;

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
                loop:
                for(int j = 0; j < game.getBoardArray()[i].length; j++){
                    if(game.getBoardArray()[i][j] == player){
                        currentPossibleMoves = game.possibleMoves(i,j,player);
                        for (int k = 0; k < currentPossibleMoves.size(); k++){

                            int [][] boardGameCopy = game.matrixDeepCopy(game.getBoardArray());

                            //make move
                            game.makeMove(i, j, currentPossibleMoves.get(k).getKey(), currentPossibleMoves.get(k).getValue(), player);

                            if(player == 1)
                            {best =  Math.min(best, heuristicValue + minimaxPruning(currentPossibleMoves.get(k), 2, depth + 1, !isMax, alpha, beta, chosenDepth));}
                            else
                            {best =  Math.min(best, heuristicValue + minimaxPruning(currentPossibleMoves.get(k), 1, depth + 1, !isMax, alpha, beta, chosenDepth));}

                            beta = Math.min( beta, best);
                            if(beta <= alpha)
                            break loop;

                            //undo move
                            game.setNewBoardMatrix(boardGameCopy);

                        }
                    }
                }
            }

            return best;
        }
    }

    public double minimax(Pair<Integer, Integer> possibleMove, int player, int depth, boolean isMax, int chosenDepth){

            executeHeuristic(possibleMove.getKey(), possibleMove.getValue(), player);

            double heuristicValue;

            if(game.getPlayerKing(player) == game.getKing())
                if(!isMax){
                    heuristicValue = getDefensiveHeuristic(player);}
                else
                    heuristicValue = -getDefensiveHeuristic(player);
            else
            if(!isMax)
                heuristicValue = getOfensiveHeuristic(player);
            else
                heuristicValue = -getOfensiveHeuristic(player);



            if(depth >= (chosenDepth -1)){
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


                            if(player == 1)
                                best =  Math.max(best, heuristicValue + minimax(currentPossibleMoves.get(k), 2, depth + 1, !isMax, chosenDepth));
                            else
                                best =  Math.max(best, heuristicValue + minimax(currentPossibleMoves.get(k), 1, depth + 1, !isMax, chosenDepth));

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

                            if(player == 1)
                                {best =  Math.min(best, heuristicValue + minimax(currentPossibleMoves.get(k), 2, depth + 1, !isMax, chosenDepth));}
                            else
                                {best =  Math.min(best, heuristicValue + minimax(currentPossibleMoves.get(k), 1, depth + 1, !isMax, chosenDepth));}

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

    public BestMove findBestMove(int player, boolean prunning, int chosenDepth){

        ArrayList<Pair<Integer,Integer>> currentPossibleMoves;
        Pair<Integer,Integer> currentPiece = new Pair<>(-1,-1);
        Pair<Integer,Integer> bestPieceMove = new Pair<>(-1,-1);
        double currentValue;
        double bestValue = -3.0;

        for(int i = 0; i < game.getBoardArray().length; i++){
            for(int j = 0; j < game.getBoardArray()[i].length; j++){
                if(game.getBoardArray()[i][j] == player){
                    currentPossibleMoves = game.possibleMoves(i,j,player);
                    for (int k = 0; k < currentPossibleMoves.size(); k++){
                        int [][] boardGameCopy = game.matrixDeepCopy(game.getBoardArray());

                        //make move
                        game.makeMove(i, j, currentPossibleMoves.get(k).getKey(), currentPossibleMoves.get(k).getValue(), player);

                        /*
                        if(!prunning)
                            if(player == 2)
                                currentValue = minimax(currentPossibleMoves.get(k), 1, 0, false, chosenDepth);
                            else
                                currentValue = minimax(currentPossibleMoves.get(k), 2, 0, false, chosenDepth);
                        else
                            if(player == 2)
                                currentValue = minimaxPruning(currentPossibleMoves.get(k), 1, 0, false, -3.0, 3.0, chosenDepth);
                            else
                                currentValue = minimaxPruning(currentPossibleMoves.get(k), 2, 0, false, -3.0, 3.0, chosenDepth);
                        */

                        if(!prunning)
                            currentValue = minimax(currentPossibleMoves.get(k), player, 0, false, chosenDepth);
                        else
                            currentValue = minimaxPruning(currentPossibleMoves.get(k), player, 0, false, -3.0, 3.0, chosenDepth);


                        //undo move
                        game.setNewBoardMatrix(boardGameCopy);

                        if(currentValue == 1.0)
                        {
                            currentPiece = new Pair<>(i,j);
                            bestPieceMove = currentPossibleMoves.get(k);
                            bestValue = currentValue;

                            System.out.println("BestValue (Win Condition): " + bestValue);
                            return new BestMove(currentPiece, bestPieceMove, bestValue);

                        }

                        if(currentValue > bestValue){
                            currentPiece = new Pair<>(i,j);
                            bestPieceMove = currentPossibleMoves.get(k);
                            bestValue = currentValue;
                        }
                    }
                }
            }
        }

        System.out.println("BestValue: " + bestValue);
        return new BestMove(currentPiece, bestPieceMove, bestValue);

    }
}
