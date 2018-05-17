package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.util.Pair;

public class Board extends JPanel {	
	
	private int nextPlayer = 1;
	private GameLogic logic;
	private Piece selected;
	private int pixels;
	private int size;
	private int tileSize;
	private ArrayList<Tile> tiles;
	private ArrayList<Piece> pieces;
	
	public Board(int size, int pixels, GameLogic logic){
		
		this.logic = logic;
		this.size = size;
		this.pixels = pixels;
		
		tiles = new ArrayList<Tile>();
		pieces = new ArrayList<Piece>();	
		
		tileSize = pixels / this.size;
		
		//Initialise Tiles
		for(int i = 0; i < this.size; i++){
			for(int j = 0; j < this.size; j++){
				int dist = centerDistance(i,j,size/2,size/2);
				Color cl = colorFromDist(dist);				
				tiles.add(new Tile(i*tileSize,j*tileSize,tileSize,cl));
			}
		}
		
		//Initialise Pieces
		setupPieces();
		
		addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
            	
            	if(SwingUtilities.isLeftMouseButton(e)){
            		if(selected == null){
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
                	}else{
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
	
	private void turnEnd(int moveY, int moveX, int player){
		selected = null;
		swapPlayer();
		checkTrap(moveY, moveX, player);
		checkCapture(moveY, moveX, player);
		/*
		 * TODO
		 * Capture King
		 * Detect end of game
		 */
		setupPieces();
	}
	
	private void checkCapture(int moveY, int moveX, int player){
		if(logic.verifyCaptureKing(moveY, moveX, player)){
			
		}
	}
	
	private void checkTrap(int moveY, int moveX, int player){
		ArrayList<Pair<Integer,Integer>> trapped = logic.catchPieces(moveY, moveX, player);
		for(int i = 0; i < trapped.size(); i++){
			if(logic.getBoardArray()[trapped.get(i).getKey()][trapped.get(i).getValue()] != player){
				logic.setBoardArray(trapped.get(i).getKey(), trapped.get(i).getValue(), player);
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
		
	}
	
	public int idToCoor(int id){
		return id*tileSize + tileSize/6;
	}
	
	public int coorToId(int coor){
		return (coor - tileSize/6) / tileSize;
	}
	
}



































