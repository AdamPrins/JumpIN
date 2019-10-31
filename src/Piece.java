import javax.swing.ImageIcon;

/**
 * 
 * @author Jay McCracken
 * 			101066860
 * @version 2.0.0
 * 
 * This is the abstract class for the generic piece
 * Objects extending this will be the Bunny, Mushroom, and Fox
 *
 */
public abstract class Piece {
	
	// The location of the piece
	protected Coord coord;
	
	// The picture of the ground and hole for use on buttons
	public static final ImageIcon icon = new ImageIcon("ButtonIcons/spaceBlank.png");
	public static final ImageIcon iconHole = new ImageIcon("ButtonIcons/SpaceHole.png");
	
	
	public Piece(Coord coord) {
		this.coord = coord;
	}
	/**
     * Checking if a piece can move, default no
     * @return
     */
	public boolean isValidMove(Coord coord) {
		return false;
	};	
	
	/**
     * Grabbing the specific object
     * @return
     */
	public Piece getPiece() {
		return this;
	};	
	
	/**
     * setting the new coordinate of the piece throws error if it can not
     * @param coord
     */
	public void setCoord(Coord coord) throws Exception{
    	throw new IllegalArgumentException("You can not move this piece.");
    }
	
	/**
     * Grabbing the coordinate of the specific object
     * @return the coordinate
     */
	public Coord getCoord() {
		return this.coord;
	};	
}
