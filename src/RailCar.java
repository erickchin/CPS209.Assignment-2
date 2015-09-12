
import java.awt.*;
import java.awt.geom.*;

/**
 * Subclass of Vehicle and can be connected to other rail cars or trains It is
 * also able to store a box with a unique identity
 *
 * @author Erick Chin
 * @date April 04, 2015
 */
public class RailCar extends Vehicle {

    // Constants used to define the dimensions of the RailCar
    public static final int UNIT = 10;
    public static final int U6 = 6 * UNIT;
    public static final int U5 = 5 * UNIT;
    public static final int U4 = 4 * UNIT;
    public static final int U3 = 3 * UNIT;
    public static final int U2 = 2 * UNIT;
    public static final int U15 = UNIT + UNIT / 2;
    public static final int U05 = UNIT / 2;
    public static final int BODY_WIDTH = U3;
    public static final int BODY_HEIGHT = U2;

    // Rail number
    private final int railNumber;

    // Box on top of the rail car
    public Box boxOnCar;

    /**
     * Constructor to setup the rail car
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param number rail number
     */
    public RailCar(int x, int y, int number) {
        super(x, y);
        railNumber = number;
        isTrainEngine = false;
        width = U6 + UNIT;
    }

    /**
     * Returns the rail number
     *
     * @return railNumber
     */
    public int getNumber() {
        return railNumber;
    }

    /**
     * Draw the rail car
     *
     * @param g2 the graphics context
     */
    @Override
    public void draw(Color color, Graphics2D g2) {
        int xValue = getX();
        int yValue = getY();
        g2.setColor(color);
        // Draw box
        drawBox(g2);
        // Draws the objects for the rail car
        body = new Rectangle2D.Double(xValue, yValue + UNIT, U6, UNIT);
        Ellipse2D.Double frontTire = new Ellipse2D.Double(xValue + UNIT, yValue + U2, UNIT, UNIT);
        Ellipse2D.Double rearTire = new Ellipse2D.Double(xValue + U4, yValue + U2, UNIT, UNIT);
        // Bottom of the front windshield
        Point2D.Double r1 = new Point2D.Double(xValue + UNIT, yValue + UNIT);
        // Front of the roof
        Point2D.Double r2 = new Point2D.Double(xValue + U2, yValue);
        // Rear of the roof
        Point2D.Double r3 = new Point2D.Double(xValue + U4, yValue);
        // Bottom of the rear windshield
        Point2D.Double r4 = new Point2D.Double(xValue + U5, yValue + UNIT);
        // Right end of the hitch
        Point2D.Double r5 = new Point2D.Double(xValue + U6, yValue + U15);
        // Left end of the hitch
        Point2D.Double r6 = new Point2D.Double(xValue + U6 + U05, yValue + U15);
        Line2D.Double hitch = new Line2D.Double(r5, r6);
        g2.draw(body);
        g2.draw(hitch);
        g2.draw(frontTire);
        g2.draw(rearTire);
        g2.draw(body);
        g2.drawString("" + getNumber(), xValue + U2, yValue + U2);
    }

    /**
     * Checks if the rail car if the mouse coordinates are in it
     * 
     * @param mouseX x coordinate of mouse
     * @param mouseY y coordinate of mouse
     * @return if mouse is inside the rail car
     */
    public boolean canBeClicked(int mouseX, int mouseY) {
        // If the body of the rail car contains the mouse coordinates, it will return false
        if (body.contains(mouseX, mouseY)) {
            return true;
        // If not, it will return false;
        } else {
            return false;
        }
    }

    /**
     * Adds a Box object onto the rail car
     * 
     * @param box
     */
    public void addBox(Box box) {
        // If the rail car does not have a box, it will add it 
        if (!hasBox) {
            hasBox = true;
            boxOnCar = box;
        }
    }

    /**
     *  Set hasBox to false if it has a box
     */
    public void removeBox() {
        if (hasBox) {
            hasBox = false;
        }
    }

    /**
     * Returns the box object on the rail car 
     * 
     * @return the box on the rail car
     */
    public Box getBox() {
        return boxOnCar;
    }

    /**
     * Draws the box that is on the rail car
     * 
     * @param g2 graphics object used to draw it on the panel
     */
    public void drawBox(Graphics2D g2) {
        // If the railcar has a box, it will draw the box ontop of it
        if (hasBox) {
            boxOnCar = new Box(getX() + U6 / 3, getY() - UNIT, boxOnCar.letterASCII);
            boxOnCar.draw(g2);
        }
    }
}
