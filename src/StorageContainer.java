
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Stack;

/**
 * Storage container that contains the Box objects stacked on top of the base
 * and uses Stack data structure to store the boxes
 * 
 * @author Erick Chin
 * @date April 04, 2015
 */

public class StorageContainer {

    // Stack of Box objects used to store the boxes
    Stack<Box> boxes = new Stack<Box>();
    // Base of the container
    Rectangle base;
    // Constants for the width and height of the base
    final int WIDTH = 50;
    final int HEIGHT = 15;
    // Coordinates of the container
    private int x;
    private int y;
    // Used to see if storage container is visible
    boolean isVisible;

    /**
     * Constructor used and sets isVisible to false
     */
    public StorageContainer() {
        isVisible = false;
    }
    
    /**
     * Constructor that takes in x and y coordinates, generates the boxes and set 
     * isVisible to true
     * 
     * @param x x coordinate of the storage container
     * @param y y coordinate of the storage container
     */
    public StorageContainer(int x, int y) {
        this.x = x;
        this.y = y;
        generateBoxes();
        isVisible = true;
    }


    /**
     * Returns the result if the stack is empty
     * 
     * @return if boxes is empty
     */
    public boolean isEmpty() {
        return boxes.empty();
    }
    
    /**
     * Draws the storage container
     * @param g2 graphics object used to draw the objects
     */
    public void draw(Graphics2D g2) {
        base = new Rectangle(x, y, WIDTH, HEIGHT);
        g2.setColor(Color.DARK_GRAY);
        g2.fill(base);
    }

    /**
     * Generates 5 Box objects and pushes it into boxes
     */
    public void generateBoxes() {
        // Used to store the ascii value of the character
        // A: 65
        int letter = 65;
        // Iterates 5 times and pushes new box objects
        for (int i = 0; i < 5; i++) {
            boxes.push(new Box(x + 15, y - (HEIGHT * 2 * i) - 20, letter));
            letter++;
        }
    }

    /**
     * Pop the latest box object
     * @return the box object that is being removed
     */
    public Box popBox() {
        return boxes.pop();
    }

    /**
     * Adds a box onto the storage container
     * @param box the Box object being added onto the container
     */
    public void pushBox(Box box) {
        boxes.push(new Box(x + 15, y - (HEIGHT * 2 * boxes.size()) - 20, box.letterASCII));
    }
}
