package view;

import javax.swing.JButton;
import javax.swing.JTextPane;

public class Overlay {
	public JTextPane playerTurn;
	public JButton startBtn;
	
	Overlay(JTextPane playerTurn, JButton startBtn){
		this.playerTurn = playerTurn;
		this.startBtn = startBtn;
	}
	
}
