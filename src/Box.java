
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * Box objects that are used for the container that holds 5 of these objects
 * which are defined by a character and is drawn into a rectangle and is able to 
 * be placed onto a rail car with the menu bar stack --> push or pop when 
 * selecting a rail car or train engine
 *
 * @author Erick Chin 
 * @date April 04, 2015
 */
public class Box {

    // Define x and y coordinates
    public int x;
    public int y;
    // Stores int value of a character
    int letterASCII;
    // Stores the rectangle object
    Rectangle structure;
    // Constant for he box size
    final int SQUARE_WIDTH = 20;

    /**
     * Constructor that sets up the Box object by the parameters and creates a
     * new rectangle object set to structure
     *
     * @param x coordinate for x
     * @param y coordinate for y
     * @param character int value of a character
     */
    public Box(int x, int y, int character) {
        this.x = x;
        this.y = y;
        letterASCII = character;
        structure = new Rectangle(x, y, SQUARE_WIDTH, SQUARE_WIDTH);
    }

    /**
     * Draws the object (Rectangle and Character) with Graphics2D parameter
     *
     * @param g2 the graphics context
     */
    public void draw(Graphics2D g2) {
        g2.draw(structure);
        g2.drawString("" + (char) letterASCII, x + 5, y + 15);
    }
}
