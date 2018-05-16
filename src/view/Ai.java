package view;

import java.text.DecimalFormat;

public class Ai {

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

    public double getDefensiveHeuristic(){
        return blockKingCaptureValue * 0.4 + kingCaptureValue * 0.3 + pieceCaptureValue * 0.15 + blockPieceCaptureValue * 0.15;
    }

    public double getOfensiveHeuristic(){

        System.out.println("kingCaptureValue: " + kingCaptureValue);
        System.out.println("blockKingCaptureValue: " + blockKingCaptureValue);
        System.out.println("pieceCaptureValue: " + pieceCaptureValue);
        System.out.println("blockPieceCaptureValue: " + blockPieceCaptureValue);

        return kingCaptureValue * 0.4 + blockKingCaptureValue * 0.3 + pieceCaptureValue * 0.15 + blockPieceCaptureValue * 0.15;
    }
}
