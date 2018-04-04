package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Piece {

	private int x;
	private int y;
	private int size;
	private Color cl;
	
	public Piece(int x, int y, int size, Color cl){		
		this.size = size;	
		this.cl = cl;
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(cl);
        g2d.fillOval(x, y, size, size);
	}
	
}
