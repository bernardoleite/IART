package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu {

	private JFrame frmMorelli;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frmMorelli.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		frmMorelli = new JFrame();
		frmMorelli.setTitle("Morelli");
		frmMorelli.setBounds(100, 100, 821, 587);
		frmMorelli.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMorelli.getContentPane().setLayout(null);
		
		JPanel board = new Board(13, 500);
		board.setBackground(UIManager.getColor("MenuBar.shadow"));
		board.setBounds(21, 24, 500, 500);
		frmMorelli.getContentPane().add(board);
		
		JButton startBtn = new JButton("New Game");
		startBtn.setBounds(604, 30, 121, 36);
		frmMorelli.getContentPane().add(startBtn);

		GameLogic game = new GameLogic();
	}
}
