
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;
import javax.swing.Timer;

/**
 * Sub class of JPanel and it sets up the panel that is placed onto the JFrame
 * Adds mouse event handling that summons the objects onto the panel and allow
 * the user to drag and connect rail cars and place boxes on top of them from
 * the container
 *
 * @author Erick Chin
 * @date April 04, 2015
 */
public class DataPanel extends JPanel {

    // Create a array list for Vehicle objects
    ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
    // Create a Vehicle variable
    Vehicle selected;
    // Used to see if selected an object
    boolean hasSelection = false;
    // Create a StorageContainer variable
    StorageContainer container;
    // Create a MouseAdapter variable 
    MouseAdapter frameListener;
    // Create a Timer variable
    Timer updateTimer;
    // Count the amount of clicks
    int clickCount = 0;
    // Save coordinates
    int x;
    int y;

    /**
     * Initiates the setup of the panel onto the frame, checks mouse inputs from
     * the user and check if user click, drag or release the mouse to allow
     * interaction with the objects
     */
    public DataPanel() {
        /**
         * Extends MouseAdapter and checks user's mouse inputs
         */
        class FrameListener extends MouseAdapter {

            /**
             * Check mouse inputs and spawns the vehicle objects depending on
             * how many times the user clicked
             *
             * @param event take user's mouse inputs
             */
            public void mousePressed(MouseEvent event) {
                // Get mouse coordinates
                x = event.getX();
                y = event.getY();
                // Check the status of the clickCount and creates objects depending on the amount of times clicked
                switch (clickCount) {
                    case 0:
                        vehicles.add(new TrainEngine(x, y));
                        clickCount++;
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        vehicles.add(new RailCar(x, y, clickCount));
                        clickCount++;
                        break;
                    case 6:
                        container = new StorageContainer(x, y);
                        container.isVisible = true;
                        clickCount++;
                    default:
                        // If clickClount is equal to 7, it will allow the user to select objects
                        if (clickCount == 7) {
                            // Iterate through all the vehicles
                            for (Vehicle vehicle : vehicles) {
                                // If vehicle is inside the mouse coordinate and is not selected, it will select it
                                if (vehicle.canBeClicked(x, y) && vehicle.checkSelection() == false) {
                                    removeSelected();
                                    vehicle.isSelected = true;
                                    selected = vehicle;
                                    hasSelection = true;
                                    break;
                                }
                            }
                        }
                }
            }

            /**
             * Check user's mouse hold input and allow user to drag objects
             * around the panel
             *
             * @param event take user's mouse inputs
             */
            public void mouseDragged(MouseEvent event) {
                // Set coordinates of the difference between the mouse and movement
                int dx = event.getX() - x;
                int dy = event.getY() - y;
                // If clickCount is 7, it will allow the user to drag
                if (clickCount == 7) {
                    // Iterates through all the vehicle objects in vehicles
                    for (Vehicle vehicle : vehicles) {
                        // If the coordinates are inside the object and selected, it will move the object depending on the mouse
                        if (vehicle.canBeClicked(x, y) && vehicle.checkSelection() == true) {
                            vehicle.addX(dx);
                            vehicle.addY(dy);
                            repaint();
                        }
                    }
                }
                // Add the difference
                x += dx;
                y += dy;
            }

            /**
             * Check if the user had placed an vehicle object onto another
             * object to allow it to link
             *
             * @param event take user's mouse inputs
             */
            public void mouseReleased(MouseEvent event) {
                // If clickCount is equal to 7 and a object is selected, it will allow it to connect to another object
                if (clickCount == 7 && hasSelection) {
                    // Iterates through all the vehicle objects inside vehicles
                    for (Vehicle vehicle : vehicles) {
                        // If vehicle is not selected, and the selected intersects with the other, the other object.next is null and selected is not a train engine, it will allow it to link 
                        if (vehicle.isSelected == false && selected.body.intersects(vehicle.body) && vehicle.next == null && !selected.isTrainEngine && !vehicle.isTrailer) {
                            vehicle.next = selected;
                            vehicle.next.prev = vehicle;
                            vehicle.isTrailer = true;
                            break;
                        }
                    }
                }
            }
        }
        // Add MouseListener and MouseMotionListener to the panel
        frameListener = new FrameListener();
        addMouseListener(frameListener);
        addMouseMotionListener(frameListener);
        /*
         * Implements ActionListener interface and repaints in 10 milliseconds
         */
        class UpdateListener implements ActionListener {

            // Overrides actionPerformed in ActionListener object and checks the timer's duration
            @Override
            public void actionPerformed(ActionEvent event) {
                repaint();
            }
        }
        // Create a UpdateListener object and saves it into updateListener
        UpdateListener updateListener = new UpdateListener();
        // Creates a new Timer object and takes in values of 10 milliscecond and updateListener object
        updateTimer = new Timer(10, updateListener);
        // Start the timer
        updateTimer.start();
    }

    /**
     * Overrides paintComponent in JPanel class and paints all the objects
     *
     * @param g graphics object used to draw the objects onto the panel
     */
    @Override
    public void paintComponent(Graphics g) {
        // Runs paintComponent in JPanel
        super.paintComponent(g);
        // Cast Graphics object into Graphics2D
        Graphics2D g2 = (Graphics2D) g;
        // Iterates through all of the vehicle objects and draws it onto the JPanel
        for (Vehicle vehicle : vehicles) {
            // If the vehicle is a trailer and prev is null, it will run drawTrailer method
            if (vehicle.isTrailer && vehicle.prev == null) {
                // If the vehicle is selected, it will run drawTrailer() method with the color red
                if (vehicle.isSelected) {
                    drawTrailer(vehicle, Color.RED, g2);
                    // If the vehicle is not selected, it will run drawTrailer() method with color black
                } else {
                    drawTrailer(vehicle, Color.BLACK, g2);
                }
            // If the vehicle is not a trailer and prev is null, it will run only drawVehicle
            } else if (vehicle.prev == null){
                // If the vehicle is selected, it will run drawVehicle() with the color red
                if (vehicle.isSelected) {
                    vehicle.draw(Color.RED, g2);
                // If the vehicle is not selected, it will run drawVehicle() with the color black
                } else {
                    vehicle.draw(Color.BLACK, g2);
                }
            }
            
        }
        // If container is not null and container is visible, it will draw the objects and the base
        if (container != null && container.isVisible) {
            container.draw(g2);
            Stack<Box> temp = container.boxes;
            // Iterates through all the Boxes
            for (Box single : temp) {
                single.draw(g2);
            }
        }
    }

    /**
     * Remove all the selection status of each vehicle
     */
    public void removeSelected() {
        // Iterates through all of the objects in vehicle and sets all the vehicles to be not selected
        for (Vehicle vehicle : vehicles) {
            vehicle.isSelected = false;
        }
    }

    /**
     * Draws the linked list of vehicles in a recursive format
     *
     * @param vehicle the start of the object
     * @param color color used to draw it
     * @param g2 the graphics used to draw
     */
    public void drawTrailer(Vehicle vehicle, Color color, Graphics2D g2) {
        // Base Case: If the vehicle is null, it will stop the recursion
        if (vehicle == null) {
            return;
        }
        vehicle.draw(color, g2);
        // Declare x and y coordinates
        int nextX;
        int nextY;
        // If vehicle's next is not null, it will setup the vehicle's next coordinates
        if (vehicle.next != null) {
            nextX = vehicle.getX() + (int) vehicle.width;
            nextY = vehicle.getY();
            vehicle.next.setX(nextX);
            vehicle.next.setY(nextY);
        }
        // Run the method again with vehicle.next
        drawTrailer(vehicle.next, color, g2);
    }

    /**
     * Draws the vehicle through its own method draw()
     *
     * @param vehicle the object itself
     * @param color color used to draw it
     * @param g2 the graphics used to draw
     */
    public void drawVehicle(Vehicle vehicle, Color color, Graphics2D g2) {
        vehicle.draw(color, g2);
    }

    /**
     * Removes the first railcar on the train engine
     */
    public void removeFirst() {
        // Set the train engine onto train variable
        Vehicle train = vehicles.get(0);
        // If train.next is null, it will do nothing
        if (train.next == null) {
            return;
        }
        // If the train.next's next is null, it will remove it
        if (train.next.next == null) {
            Vehicle removedRail = train.next;
            removedRail.setX(200);
            removedRail.setY(200);
            train.isTrailer = false;
            train.next = null;
            removedRail.prev = null;
        // If theres more than one rail car on the train engine, it will save from 2nd last until end and replace the train.next to it
        } else {
            // Save the rail car that is being removed
            Vehicle removedRail = train.next;
            removedRail.setX(200);
            removedRail.setY(200);
            removedRail.isTrailer = false;
            train.next = train.next.next;
            removedRail.next = null;
            removedRail.prev = null;
        }
        removeSelected();
    }

    /**
     * Removes the last railcar on the train engine
     */
    public void removeLast() {
        // Set train engine as current variable
        Vehicle current = vehicles.get(0);
        // Set previous variable to null
        Vehicle previous = null;
        // If the train engine's next is null, it will do nothing
        if (current.next == null) {
            return;
        }
        // Loops until the end of the linked list
        while (current.next != null) {
            previous = current;
            current = current.next;
        }
        previous.next.setX(200);
        previous.next.setY(200);
        previous.isTrailer = false;
        previous.next = null;
        current.prev = null;
        repaint();
        removeSelected();
    }

    /**
     * Adds the selected railcar onto the train engine's first connection
     */
    public void addFirst() {
        // Set train engine as current variable
        Vehicle current = vehicles.get(0);
        // If hasSelected is true, selected is not null and selected is not a train engine, it will add the selected object onto the train engine
        if (hasSelection && selected != null && !selected.isTrainEngine) {
            // If the train engine is not a trailer, it will add the selected to it
            if (current.next == null) {
                current.next = selected;
                current.isTrailer = true;
            // If the train engine is a trailer, it will add the selected inbetween the trail and the train engine
            } else {
                // Save variable for the selected last trailer
                Vehicle selectLast = selected;
                // Loops until the last 
                while (selectLast.next != null) {
                    selectLast = selectLast.next;
                }
                Vehicle others = current.next;
                current.next = selected;
                selected.isTrailer = true;
                selectLast.next = others;
            }
        }
        removeSelected();
    }

    /**
     * Adds the selected railcar onto the train engine's last connection
     */
    public void addLast() {
        // Set train engine as current variable 
        Vehicle current = vehicles.get(0);
        // If hasSelection is true, selected is not null and select is not a train engine, it will add selected to last of the train engine
        if (hasSelection && selected != null && !selected.isTrainEngine) {
            // If the train engine is not a trailer, it will set train engine's next to selected and stop the method
            if (current.next == null) {
                current.next = selected;
                current.isTrailer = true;
                return;
            }
            // Loop until the last object of the trailer
            while (current.next != null) {
                current = current.next;
            }
            current.next = selected;
            current.isTrailer = true;
            repaint();
        }
        removeSelected();
    }

    /**
     * Adds a box onto the selected trailer or train engine
     */
    public void popItem() {
        // Iterates through all of the objects in vehicles
        for (Vehicle vehicle : vehicles) {
            // If the vehicle is selected, does not have a box and the container is not empty, it will add a box onto the car
            if (vehicle.checkSelection() == true && !container.isEmpty()) {
                // If the vehicle is a trailer, it will run checkTrailerBox to see what does not have a box
                if (vehicle.isTrailer) {
                    // Runs checkTrailer method and cast the object
                    RailCar temp = (RailCar)checkTrailerBox(vehicle);
                    // If there was no return it will stop the method
                    if (temp == null) {
                        return;
                    }
                    // Stores the container's box object when popping it
                    Box tempBox = container.popBox();
                    temp.addBox(tempBox);
                    break;
                // If the vehicle does not have a box and is not a train engine, it will add the box on the selected
                } else if (!vehicle.hasBox && !vehicle.isTrainEngine) {
                    RailCar temp = (RailCar)vehicle;
                    Box box = container.popBox();
                    temp.addBox(box);
                    break;
                }
            }
        }
    }

    /**
     * Remove the selected railcar onto the train engine's last connection
     */
    public void pushItem() {
        // Iterates through all the objects in vehicles
        for (Vehicle vehicle : vehicles) {
            // If the vehicle is selected and vehicle has a box, it will remove the box
            if (vehicle.checkSelection() == true) {
                // If the vehicle is a trailer, it will find the earliest rail car and remove the box
                if (vehicle.isTrailer) {
                    // Save the vehicle that has a box and cast to a RailCar
                    RailCar temp = (RailCar)findTrailerBox(vehicle);
                    // If temp is null, it will stop the method
                    if (temp == null) {
                        return;
                    }
                    container.pushBox(temp.getBox());
                    temp.removeBox();
                    break;
                 // If the vehicle has a box, it will remove it and add it back to the container
                } else if (vehicle.hasBox) {
                    // Change vehicle to RailCar since train engine can not have a box
                    RailCar temp = (RailCar)vehicle;
                    container.pushBox(temp.getBox());
                    temp.removeBox();
                    break;
                }
            }
        }
    }

    /**
     * Recursive method that checks the trailer to see what vehicle does not
     * have a box
     *
     * @param vehicle start of the vehicles that are being searched
     * @return vehicle that does not have a box
     */
    public RailCar checkTrailerBox(Vehicle vehicle) {
        // If the vehicle is null, it will return null
        if (vehicle == null) {
            return null;
        } 
        // If the vehicle is a train engine, it will run the method again with the next on the linked list
        else if (vehicle.isTrainEngine) {
            return checkTrailerBox(vehicle.next);
        } 
        // If the vehicle does not have a box, it will return the vehicle
        else if (!vehicle.hasBox) {
            return (RailCar)vehicle;
        // If none of the conditions are true, it will run the method again with the next vehicle in the trailer
        } else {
            return checkTrailerBox(vehicle.next);
        }
    }

    /**
     * Recursive method that finds that trailer that has a box and returns it
     *
     * @param vehicle start of the vehicles that are being searched
     */
    public RailCar findTrailerBox(Vehicle vehicle) {
        // If the vehicle is null, it will return null
        if (vehicle == null) {
            return null;
        }
        // If the vehicle is a train engine, it will run again with the next
        if (vehicle.isTrainEngine) {
            return findTrailerBox(vehicle.next);
        }
        // If the vehicle has a box it will return it
        if (vehicle.hasBox) {
            return (RailCar)vehicle;
        // If all of the conditions are false, it will run with the next vehicle in the linked list
        } else {
            return findTrailerBox(vehicle.next);
        }
    }
}
