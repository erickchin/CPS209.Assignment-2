
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Vehicle class gives the core properties of a generic cart
 * 
 * @author Erick
 * @date April 04, 2015
 */

public abstract class Vehicle {

    // X and Y coordinates of the vehicle
    private int x;
    private int y;
    // If the vehicle is selected
    public boolean isSelected;
    // If the vehicle is a trailer
    public boolean isTrailer;
    // If the vehicle has a box
    public boolean hasBox;
    // If the vehicle is a train engine
    public boolean isTrainEngine;
    // The cart attached to
    public Vehicle next;
    public Vehicle prev = null;
    // Body of the cart
    public Rectangle2D.Double body;
    // Width of the vehicle
    public double width;

    /**
     *
     * @param x
     * @param y
     */
    public Vehicle(int x, int y) {
        this.x = x;
        this.y = y;
        hasBox = false;
        isSelected = false;
        isTrailer = false;
    }

    /**
     * Returns the X coordinate of the vehicle
     * @return x variable
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y coordinate of the vehicle
     * @return y variable
     */
    public int getY() {
        return y;
    }

    /**
     * Add onto the x coordinate of the vehicle
     * @param x additional x coordinate
     */
    public void addX(int x) {
        this.x += x;
    }

    /**
     * Add onto the y coordinate of the vehicle
     * @param y additional y coordinate
     */
    public void addY(int y) {
        this.y += y;
    }

    /**
     * Set x coordinate to a specific value
     * @param x x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set y coordinate to a specific value
     * @param y y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Check if the vehicle is selected
     * @return isSelected
     */
    public boolean checkSelection() {
        return isSelected;
    }

    /**
     * Abstract method for subclasses to draw the vehicle
     * 
     * @param color color used to draw
     * @param g2 the graphic object used to draw
     */
    abstract void draw(Color color, Graphics2D g2);
    
    /**
     * Abstract method for subclasses to check if the vehicle is inside the mouse coordinates
     * 
     * @param mouseX x coordinate of mouse
     * @param mouseY y coordinate of mouse
     */
    abstract boolean canBeClicked(int mouseX, int mouseY);
}
