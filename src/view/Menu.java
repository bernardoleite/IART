package view;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import view.Board.Mode;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class Menu {

	private boolean firstTime = true;
	private JFrame frmMorelli;
	private JPanel board;
	private int size = 13;
	private Overlay overlay;
	private GameLogic game;
	private JComboBox modeBox;
	private JTextField cpuDepthInput;
	private JRadioButton pruningToggle;

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
		frmMorelli.setBounds(100, 100, 821, 654);
		frmMorelli.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMorelli.getContentPane().setLayout(null);
		
		//CPU Depth
		cpuDepthInput = new JTextField();
		cpuDepthInput.setText("2");
		cpuDepthInput.setBounds(688, 102, 35, 20);
		frmMorelli.getContentPane().add(cpuDepthInput);
		cpuDepthInput.setColumns(10);
		
		//Pruning Toggle
		pruningToggle = new JRadioButton("Pruning");
		pruningToggle.setSelected(true);
		pruningToggle.setBounds(588, 129, 121, 23);
		frmMorelli.getContentPane().add(pruningToggle);
		
		//Mode Box
		modeBox = new JComboBox();
		modeBox.setModel(new DefaultComboBoxModel(new String[] {"Player vs Player", "Player vs CPU", "CPU vs CPU"}));
		modeBox.setBounds(602, 48, 121, 29);
		frmMorelli.getContentPane().add(modeBox);
		
		newGame();	
		
		JLabel cpuDepthLabel = new JLabel("CPU Depth:");
		cpuDepthLabel.setBounds(588, 105, 76, 14);
		frmMorelli.getContentPane().add(cpuDepthLabel);
		
		//New Game Button
		JButton startBtn = new JButton("New Game");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGame();				
			}
		});
		startBtn.setBounds(602, 169, 121, 36);
		frmMorelli.getContentPane().add(startBtn);
	
	}
	
	private void newGame(){
		
		if(!firstTime){
			frmMorelli.getContentPane().remove(board);
			frmMorelli.getContentPane().remove(overlay.CPUTurn);
			frmMorelli.getContentPane().remove(overlay.playerTurn);
		}else
			firstTime = false;
		
		overlay = initializeOverlay();
		game = new GameLogic(size);
		boolean pruning = pruningToggle.isSelected();
		int depth = Integer.parseInt(cpuDepthInput.getText());
		
		switch((String)modeBox.getSelectedItem()){
		case "Player vs Player":
			board = new Board(size, 500, game, overlay, Mode.PvP, pruning, depth);
			break;
		case "Player vs CPU":
			board = new Board(size, 500, game, overlay, Mode.PvC, pruning, depth);
			break;
		case "CPU vs CPU":
			board = new Board(size, 500, game, overlay, Mode.CvC, pruning, depth);
			break;
		}
		
		board.setBackground(UIManager.getColor("MenuBar.shadow"));
		board.setBounds(21, 24, 494, 494);
		frmMorelli.getContentPane().add(board);
		
		board.revalidate();
		board.repaint();
		
		frmMorelli.revalidate();
		frmMorelli.repaint();
		
	}
	
	private Overlay initializeOverlay(){
		//Text Format
		SimpleAttributeSet normal = new SimpleAttributeSet();
        StyleConstants.setFontSize(normal, 16);
        StyleConstants.setBold(normal, true);
        StyleConstants.setAlignment(normal, StyleConstants.ALIGN_CENTER);
        
        //Next Player Text
		JTextPane playerTurn = new JTextPane();
		playerTurn.setEditable(false);
		playerTurn.setBounds(176, 544, 186, 25);
		playerTurn.setCharacterAttributes(normal, true);
		frmMorelli.getContentPane().add(playerTurn);
		
		JButton CPUTurn = new JButton("CPU Turn");
		CPUTurn.setBounds(602, 216, 121, 36);
		frmMorelli.getContentPane().add(CPUTurn);
		
		return new Overlay(playerTurn, CPUTurn);
	}
}
