import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * GUI implementation 
 * holds the game layout and the other intractable elements
 *  
 * @author Adam Prins
 * 			100 879 683
 * @version 1.5.1
 * 		GUI layout edited to look better
 * 		removed print statement from reset
 * 		
 */
public class JumpInGUI implements ActionListener {
	
	public static void main(String[] args) {
		new JumpInGUI();
	}
	
	public static final int BOARD_SIZE = 5;
	
	private int puzzleNumber=1;
	
	/* The reset menu item */
    private JMenuItem resetItem;
    
    /* The quit menu item */
    private JMenuItem quitItem;
    
    /* the selected tile and the game board */
    private ButtonTile selectedTile;
    private ButtonTile board[][];
    
    /* The undo and redo Buttons */
    private JButton undo;
    private JButton redo;
    
    /* The next level button */
    private JButton nextLevel;
    
    /* The output fields */
    private JLabel outputStatic;
    private JLabel output;
    
    /* The Game */
    private Game game;

    /**
     * Constructor of the GUI 
     * Initializes the frame and configures the layouts
     * Sets all listeners
     */
	public JumpInGUI() {
		JFrame frame = new JFrame("JumpIN"); 
	    Container contentPane = frame.getContentPane(); 
	    contentPane.setLayout(new GridBagLayout());
	    // get the content pane so we can put stuff in
	
	    JMenuBar menubar = new JMenuBar();
	    frame.setJMenuBar(menubar); // add menu bar to our frame
	
	    JMenu fileMenu = new JMenu("Options"); // create a menu
	    menubar.add(fileMenu); // and add to our menu bar
	
	    resetItem = new JMenuItem("Reset"); // create a menu item called "Reset"
	    fileMenu.add(resetItem); // and add to our menu
	
	    quitItem = new JMenuItem("Quit"); // create a menu item called "Quit"
	    fileMenu.add(quitItem); // and add to our menu
	
	    // listen for menu selections
	    resetItem.addActionListener(this); 
	    quitItem.addActionListener(this); // create an anonymous inner class
	
	    
	    
	    
	    JPanel interfacePanel = new JPanel();
	    interfacePanel.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.anchor = (GridBagConstraints.LINE_START);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    
	    c.gridx = 0;
	    c.weightx=1;
	    interfacePanel.add(Box.createGlue(), c);
	    
	    c.weightx=0;
	    c.weighty=0;
	    c.gridx = 1;
	    c.gridy = 0;
	    undo = new JButton("undo");
	    undo.setPreferredSize(new Dimension(60,40));
	    undo.addActionListener(this);
	    interfacePanel.add(undo, c);
	    
	    c.gridx = 2;
	    redo = new JButton("redo");
	    redo.setPreferredSize(new Dimension(60,40));
	    redo.addActionListener(this);
	    interfacePanel.add(redo, c);
	    
	    c.gridx = 3;
	    c.weightx=1;
	    interfacePanel.add(Box.createGlue(), c);
	    
	    outputStatic = new JLabel("Output: ");
	    c.gridx = 0;
	    c.gridy = 5;
	    interfacePanel.add(outputStatic,c);
	    c.weightx=1;
	    c.gridx = 0;
	    c.gridwidth=4;
	    c.gridy = 6;
	    output = new JLabel("Game Start");
	    output.setPreferredSize(new Dimension(150,30));
	    interfacePanel.add(output,c);
	    
	    c.weightx=1;
	    c.weighty=1;
	    c.gridx = 0;
	    c.gridy = 10;
	    c.gridwidth=4;
	    nextLevel = new JButton("Next Level");
	    nextLevel.setPreferredSize(new Dimension(150,30));
	    nextLevel.addActionListener(this);
	    nextLevel.setEnabled(false);
	    interfacePanel.add(nextLevel,c);
	    
	    c.weightx=1;
	    c.weighty=1;
	    c.gridx = 0;
	    c.gridy = 20;
	    c.gridwidth=3;
	    interfacePanel.add(Box.createGlue(),c);
	    
	    
	    JPanel boardPanel = new JPanel();
	    boardPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        
        
	    /* The button that is clicked to increment the counter. */
	    board = new ButtonTile[BOARD_SIZE][BOARD_SIZE];
		
		for (int x=0; x<BOARD_SIZE; x++) {
			for (int y=0; y<BOARD_SIZE; y++) {
                c.gridx = x;
                c.gridy = y;
                board[x][y]= new ButtonTile(new Coord(x,y));
                board[x][y].setPreferredSize(new Dimension(100,100));
                board[x][y].setMargin(new Insets(0,0,0,0));
                board[x][y].setEnabled(true);
                boardPanel.add(board[x][y],c);
                board[x][y].addActionListener(this);
			}
		}
	    
		
		
	    c = new GridBagConstraints();
	    c.anchor = GridBagConstraints.LINE_START;
	    c.gridx=0;	c.gridwidth=6;
	    c.gridy=0;	c.gridheight=6;
	    c.ipadx = 10; c.ipady = 10;
	    c.weightx=0;
	    contentPane.add(boardPanel,c);
	    
	    c.gridx=6;	c.gridwidth=1;
	    c.gridy=0;	c.gridheight=6;
	    c.ipadx = 10; c.ipady = 5;	//c.ipadx fully controls the space between contentPane and interfacePanel
	    c.weightx=0;
	    contentPane.add(Box.createGlue(),c);
	    
	    c.gridx=7;	c.gridwidth=4;
	    c.gridy=0;	c.gridheight=6;
	    c.ipadx = 5; c.ipady = 5;
	    c.weightx=1;
	    contentPane.add(interfacePanel,c);
	    
	    frame.setPreferredSize(new Dimension(750,600));
	    frame.pack(); // pack contents into our frame
        frame.setResizable(false); // we can resize it
        frame.setVisible(true); // make it visible
        
        try {
			game = new Game(board, puzzleNumber);
			drawButtons();
		} catch (Exception e) {
			output.setText(e.getMessage());
		}
        //drawButtons();

	}
	
	
	/** 
	 * This action listener is called when the user clicks on 
     * any of the GUI's buttons. 
     */
    public void actionPerformed(ActionEvent e)
    {
        Object o = e.getSource(); // get the action 

        // see if it's a JButton
        if (o instanceof ButtonTile) {
        	ButtonTile button = (ButtonTile) o;
        	try {
        		if (selectedTile!=null)  selectedTile.setSelected(false);
        		game.selectTile(button.getCoord());
        		selectedTile = (ButtonTile) game.getSelectedTile();
        		if (selectedTile!=null)  selectedTile.setSelected(true);
        		
        		drawButtons();
        		if (game.endGame()) endGame();
        		
        	} catch (Exception exception) {
        		output.setText(exception.getMessage());
        		selectedTile=null;
        	}
        	
        	
        } 
        else if (o instanceof JButton) {
        	JButton button = (JButton) o;
        	 if (button == undo) {
        		 try {
        		 game.undoMove();
        		 drawButtons();
        		 } catch (Exception exception) {
        			 output.setText(exception.getMessage());
        		 }
        	 }
        	 else if (button == redo) {
        		 try {
        			 game.redoMove();
        			 drawButtons();
        		 } catch (Exception exception) {
        			 output.setText(exception.getMessage());
        		 }
        	 }
        	 else if (button == nextLevel) {
        		 setBoard(++puzzleNumber);
        	 }
        }
        else if (o instanceof JMenuItem){ // it's a JMenuItem
            JMenuItem item = (JMenuItem)o;

            if (item == resetItem) {
            	setBoard(puzzleNumber);
            } 
            else if (item == quitItem) {
                System.exit(0);
            }
        }

    }
    
    /**
     * Handles the logic of setting the board
     * 
     * @param puzzleNumber the puzzle number that is to be set up
     */
    private void setBoard(int puzzleNumber) {
    	this.puzzleNumber=puzzleNumber;
    	if (selectedTile!=null)  selectedTile.setSelected(false);
		selectedTile=null;
		
		for(ButtonTile[] tileLine:board) {
			for(ButtonTile tile:tileLine) {
				tile.setEnabled(true);
			}
		}
		try {
			game.setBoard(Puzzles.getPuzzle(puzzleNumber));
		} catch(Exception e) {
			System.out.print(e.getMessage());
		}
		undo.setEnabled(true);
		redo.setEnabled(true);
		nextLevel.setEnabled(false);
		drawButtons();
		output.setText("Welcome to puzzle: " + puzzleNumber);
    }
    
    private void endGame() {
    	if (selectedTile!=null)  selectedTile.setSelected(false);
		selectedTile=null;
		
		for(ButtonTile[] tileLine:board) {
			for(ButtonTile tile:tileLine) {
				tile.setEnabled(false);
			}
		}

		undo.setEnabled(false);
		redo.setEnabled(false);
		nextLevel.setEnabled(true);
    }
    
    private void drawButtons() {
    	for(ButtonTile[] tileLine:board) {
			for(ButtonTile tile:tileLine) {
				Piece piece = tile.getPiece();
				
				//TODO replace with pictures
				if (piece == null) {
					tile.setText("   ");
				} else if (piece instanceof Bunny) {
					tile.setText("Bun");
				} else if (piece instanceof Mushroom) {
					tile.setText("Shr");
				} else if (piece instanceof Fox) {
					tile.setText("Fox");
				}
				
			}
		}
    }
    
}