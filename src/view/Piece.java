package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Piece {

	public int x;
	public int y;
	public int size;
	public Color cl;
	public int player;
	
	public Piece(int x, int y, int size, Color cl, int player){		
		this.size = size;	
		this.cl = cl;
		this.x = x;
		this.y = y;
		this.player = player;
	}
	
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(cl);
        g2d.fillOval(x, y, size, size);
	}
	
}
