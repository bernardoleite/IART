package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Board extends JPanel {

	private int size;
	private ArrayList<Tile> tiles;
	private ArrayList<Piece> pieces;
	
	public Board(int size, int pixels, GameLogic logic){
		
		this.size = size;
		
		tiles = new ArrayList<Tile>();
		pieces = new ArrayList<Piece>();	
		
		int tileSize = pixels / this.size;
		
		//Initialise Tiles
		for(int i = 0; i < this.size; i++){
			for(int j = 0; j < this.size; j++){
				int dist = centerDistance(i,j,size/2,size/2);
				Color cl = colorFromDist(dist);				
				tiles.add(new Tile(i*tileSize,j*tileSize,tileSize,cl));
			}
		}
		
		//Initialise Pieces
		for(int i = 0; i < logic.getBoardArray().length; i++){
			for(int j = 0; j < logic.getBoardArray()[i].length; j++){
				if(logic.getBoardArray()[i][j] == 1)
					pieces.add(new Piece(j*tileSize + tileSize/6,i*tileSize + tileSize/6,tileSize*2/3,Color.white));
				else if(logic.getBoardArray()[i][j] == 2)
					pieces.add(new Piece(j*tileSize + tileSize/6,i*tileSize + tileSize/6,tileSize*2/3,Color.black));

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
		for(int i = 0; i < this.tiles.size(); i++){
			this.tiles.get(i).draw(g);
		}
		for(int i = 0; i < this.pieces.size(); i++){
			this.pieces.get(i).draw(g);
		}
	}
	
}
