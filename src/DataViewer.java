
import javax.swing.JFrame;

/**
 * Setup the frame to display an interface
 *
 * @author Erick Chin
 * @date April 04, 2015
 */
public class DataViewer {

    // Constants for the size of the window
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 600;

    /**
     * Main that sets up the frame
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        // Create a new DataFrame
        JFrame frame = new DataFrame();
        // Set properties of the frame
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Data Structure Simulator");
        frame.setVisible(true);
    }
}
