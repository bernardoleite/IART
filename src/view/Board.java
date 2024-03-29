package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.util.Pair;
import view.Ai.BestMove;

public class Board extends JPanel {	
	
	public enum Mode{
		PvP,
		PvC,
		CvC
	}
	
	private boolean pruning;
	private int depth;
	private boolean blockAll = false;
	private Ai ai;
	private Mode mode;
	private Overlay overlay;
	private int king = 0;
	private int nextPlayer = 1;
	private GameLogic logic;
	private Piece selected;
	private int size;
	private int tileSize;
	private ArrayList<Tile> tiles;
	private ArrayList<Piece> pieces;
	
	public Board(int size, int pixels, GameLogic logic, Overlay overlay, Mode mode, boolean pruning, int depth){
		this.pruning = pruning;
		this.depth = depth;
		this.mode = mode;
		this.overlay = overlay;
		this.logic = logic;
		this.ai = new Ai(this.logic);
		this.size = size;
		
		tiles = new ArrayList<Tile>();
		pieces = new ArrayList<Piece>();	
		
		tileSize = pixels / this.size;
		
		//Initialise Tiles
		tiles.clear();
		for(int i = 0; i < this.size; i++){
			for(int j = 0; j < this.size; j++){
				int dist = centerDistance(i,j,size/2,size/2);
				Color cl = colorFromDist(dist);				
				tiles.add(new Tile(i*tileSize,j*tileSize,tileSize,cl));
			}
		}
		
		//Initialise Pieces
		setupPieces();
		
		//Initialise Overlay
		updateOverlay();
		
		//CPU vs CPU
		if(mode == Mode.CvC){
			this.overlay.CPUTurn.setVisible(true);
			this.overlay.CPUTurn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					CPUTurn();
			    }
			});
		}else{
			this.overlay.CPUTurn.setVisible(false);
		}
		
		revalidate();
		repaint();
		
		addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
            	
            	if(SwingUtilities.isLeftMouseButton(e)){
            		
            		if(mode == Mode.CvC || blockAll){
            			return;
            		}
            		
            		if(selected == null){
            			detectPieceClick(e);
                	}else{                		
                		detectPieceClick(e);
                		
                		int tX = coorToId(selected.x);
            			int tY = coorToId(selected.y);
            			ArrayList<Pair<Integer,Integer>> moves = logic.possibleMoves(tY, tX, selected.player);
            			for(int i = 0; i < moves.size(); i++){
            				int aX = idToCoor(moves.get(i).getValue());
            				int aY = idToCoor(moves.get(i).getKey());
                    		if(aX < e.getX() && aX + tileSize > e.getX() && aY < e.getY() && aY + tileSize > e.getY()){
                    			logic.makeMove(tY, tX, moves.get(i).getKey(), moves.get(i).getValue(), selected.player);
                    			turnEnd(moves.get(i).getKey(), moves.get(i).getValue(), selected.player);
                    			repaint();
                    			revalidate();
                    			return;
                    		}
                    	}
                	}
            	}           	
            	
            	if(SwingUtilities.isRightMouseButton(e)){
            		selected = null;
            		repaint();
        			revalidate();
            	}
            	
            }
            
        });
		
	}
	
	private void detectPieceClick(MouseEvent e){
		for(int i = 0; i < pieces.size(); i++){
    		if(pieces.get(i).x < e.getX() && pieces.get(i).x + tileSize > e.getX() && pieces.get(i).y < e.getY() && pieces.get(i).y + tileSize > e.getY() && pieces.get(i).player == nextPlayer){
    			selected = pieces.get(i);
    			
    			//Ignore empty selects
    			int tX = coorToId(selected.x);
    			int tY = coorToId(selected.y);
    			ArrayList<Pair<Integer,Integer>> moves = logic.possibleMoves(tY, tX, selected.player);
    			if(moves.size() <= 0){
    				selected = null;
    				return;
    			}
    			
    			repaint();
    			revalidate();
    			return;
    		}
    	}
	}
	
	private void turnEnd(int moveY, int moveX, int player){
		
		if(blockAll)
			return;
		
		selected = null;
			
		checkTrap(moveY, moveX, player);
		checkCapture(moveY, moveX, player);
		swapPlayer();		
		
		setupPieces();
		updateOverlay();
		repaint();
		revalidate();
		
		checkEnd();
		
		if(this.mode == Mode.PvC)
			CPUTurn();
	}
	
	private void CPUTurn(){
		
		if(blockAll)
			return;
		
		BestMove move = this.ai.findBestMove(this.nextPlayer, this.pruning, this.depth);
		if(move.getBestMoveValue() != 0.0)
			this.logic.makeMove(move.getOriginMove().getKey(), move.getOriginMove().getValue(), move.getNewMove().getKey(), move.getNewMove().getValue(), this.nextPlayer);
		else
			this.logic.makeRandomMove(this.nextPlayer);

		checkTrap(move.getNewMove().getKey(), move.getNewMove().getValue(), this.nextPlayer);
		checkCapture(move.getNewMove().getKey(), move.getNewMove().getValue(), this.nextPlayer);
		swapPlayer();
		
		setupPieces();
		updateOverlay();
		repaint();
		revalidate();
		
		checkEnd();
	}
	
	private void checkEnd(){
		if(!logic.verifyIfPlayerHasNoPossibleMoves(this.nextPlayer))
			return;
		if(this.king == 0)
			overlay.playerTurn.setText("DRAW");
		else if(this.king == 1)
			overlay.playerTurn.setText("WINNER: WHITE");
		else if(this.king == 2)
			overlay.playerTurn.setText("WINNER: BLACK");
		this.blockAll = true;
	}
	
	private void updateOverlay(){
		//Next Player Text
		if(this.nextPlayer == 1)
			overlay.playerTurn.setText("NEXT PLAYER: WHITE");
		if(this.nextPlayer == 2)
			overlay.playerTurn.setText("NEXT PLAYER: BLACK");
	}
	
	private void checkCapture(int moveY, int moveX, int player){
		if(logic.verifyCaptureKing(moveY, moveX, player)){
			king = player;
			logic.setKing(player);
		}
	}
	
	private void checkTrap(int moveY, int moveX, int player){
		ArrayList<Pair<Integer,Integer>> trapped = logic.catchPieces(moveY, moveX, player);
		for(int i = 0; i < trapped.size(); i++){
			if(logic.getBoardArray()[trapped.get(i).getKey()][trapped.get(i).getValue()] != player){
				logic.setBoardArray(trapped.get(i).getKey(), trapped.get(i).getValue(), player);
				//Check trap causes capture too
				checkCapture(trapped.get(i).getKey(), trapped.get(i).getValue(), player);
				//Check recursively
				checkTrap(trapped.get(i).getKey(), trapped.get(i).getValue(), player);
			}
    	}
	}
	
	private void swapPlayer(){
		if(nextPlayer == 1)
			nextPlayer = 2;
		else
			nextPlayer = 1;
	}
	
	private void setupPieces(){
		pieces.clear();
		for(int i = 0; i < logic.getBoardArray().length; i++){
			for(int j = 0; j < logic.getBoardArray()[i].length; j++){
				if(logic.getBoardArray()[i][j] == 1)
					pieces.add(new Piece(idToCoor(j),idToCoor(i),tileSize*2/3,Color.white,1));
				else if(logic.getBoardArray()[i][j] == 2)
					pieces.add(new Piece(idToCoor(j),idToCoor(i),tileSize*2/3,Color.black,2));

			}
		}
	}
	
	private int centerDistance(int px, int py, int cx, int cy){
		int distX = Math.abs(px-cx);
		int distY = Math.abs(py-cy);
		
		if(distX > distY)
			return distX;
		else
			return distY;	
	}
	
	private Color colorFromDist(int dist){
		switch(dist){
		case 0:
			return new Color(150,68,134);
		case 1:
			return new Color(111,37,192);
		case 2:
			return new Color(9,125,226);
		case 3:
			return new Color(62,188,42);
		case 4:
			return new Color(222,195,4);
		case 5:
			return new Color(199,104,38);
		case 6:
			return new Color(195,35,37);
		default:
			return Color.BLACK;
		}		
	}
	
	@Override
	public void paintComponent(Graphics g){
		//Draw Board and tiles
		for(int i = 0; i < this.tiles.size(); i++){
			this.tiles.get(i).draw(g);
		}
		for(int i = 0; i < this.pieces.size(); i++){
			this.pieces.get(i).draw(g);
		}
		
		//Draw overlay
		if(this.selected != null){
			int tX = coorToId(this.selected.x);
			int tY = coorToId(this.selected.y);
			ArrayList<Pair<Integer,Integer>> moves = logic.possibleMoves(tY, tX, this.selected.player);
			
			g.setColor(this.selected.cl);
			int overlaySize = 20;
			for(int i = 0; i < moves.size(); i++){
		        g.fillOval(idToCoor(moves.get(i).getValue()) + overlaySize/6, idToCoor(moves.get(i).getKey()) + overlaySize/6, overlaySize, overlaySize);
			}
		}
		
		//Draw King
		if(king != 0){
			if(king == 1)
				g.setColor(Color.WHITE);
			if(king == 2)
				g.setColor(Color.BLACK);
			int kingSize = 20;
			g.fillRect(tileSize*6 + tileSize/2 - kingSize/2, tileSize*6 + tileSize/2 - kingSize/2, kingSize, kingSize);
		}
		
	}
	
	public int idToCoor(int id){
		return id*tileSize + tileSize/6;
	}
	
	public int coorToId(int coor){
		return (coor - tileSize/6) / tileSize;
	}
	
}



































