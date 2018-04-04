package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Tile {
	
	private int borderSize = 1;
	private int size;
	private Color cl;
	private int x;
	private int y;	
	
	public Tile(int x, int y, int size, Color cl){		
		this.size = size;	
		this.cl = cl;
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
        Rectangle rect = new Rectangle(x + borderSize, y + borderSize, size - borderSize * 2, size - borderSize * 2);
        Rectangle border = new Rectangle(x, y, size, size);

        g2d.setColor(Color.black);
        g2d.fill(border);
        g2d.setColor(cl);
        g2d.fill(rect);
	}

}
