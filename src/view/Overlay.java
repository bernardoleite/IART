package view;

import javax.swing.JButton;
import javax.swing.JTextPane;

public class Overlay {
	
	public JTextPane playerTurn;
	public JButton CPUTurn;
	
	Overlay(JTextPane playerTurn, JButton CPUTurn){
		this.playerTurn = playerTurn;
		this.CPUTurn = CPUTurn;
	}
	
}
