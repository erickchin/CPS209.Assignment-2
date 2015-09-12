
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Subclass of JFrame which sets up the tool bar with the tabs to adjust the
 * objects And creates a to be placed on itself to allow user interaction
 *
 * @author Erick Chin
 * @date April 04, 2015
 */
public final class DataFrame extends JFrame {

    // Constants for the window
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 650;
    // Define a new panel used for visuals
    private final DataPanel panel;

    /**
     * Constructs / Initiates the frame of the window
     */
    public DataFrame() {
        // Constructs the menu bar      
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        // Add File, Stack and List tabs onto the menu bar
        menuBar.add(createFileMenu());
        menuBar.add(createStackMenu());
        menuBar.add(createListMenu());
        // Set the size of the window
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        // Create a new panel and adds it to the frame
        panel = new DataPanel();
        add(panel);
    }

    /**
     * Implements ActionListener and exits the application when user presses
     * exit
     */
    class ExitItemListener implements ActionListener {

        /**
         * Overrides and checks user's input
         *
         * @param event take user's input
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }

    /**
     * Implements ActionListener and resets the JPanel
     */
    class NewItemListener implements ActionListener {

        /**
         * Overrides and checks user's inputs
         *
         * @param event take user's input
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            // Reset all of the objects and properties on the panel
            panel.vehicles = new ArrayList<Vehicle>();
            panel.clickCount = 0;
            panel.container = new StorageContainer();
            panel.selected = null;
            panel.hasSelection = false;
        }
    }

    /**
     * Implements ActionListener and adds an object in front of the linked list
     * through the panel's method, addFirst()
     */
    class AddFirstItemListener implements ActionListener {

        /**
         * Overrides and checks user's input
         *
         * @param event take user's input
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            panel.addFirst();
        }
    }

    /**
     * Implements ActionListener and adds an object at the bottom of the linked
     * list through the panel's method, addLast()
     */
    class AddLastItemListener implements ActionListener {

        /**
         * Overrides and checks user's input
         *
         * @param event take user's input
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            panel.addLast();
        }
    }

    /**
     * Implements ActionListener and removes the first item in the linked list
     * through the panel's method, removeFirst()
     */
    class RemoveFirstItemListener implements ActionListener {

        /**
         * Overrides and checks user's input
         *
         * @param event take user's input
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            panel.removeFirst();
        }
    }

    /**
     * Implements ActionListener and removes the last item in the linked list
     * through the panel's method, removeLast()
     */
    class RemoveLastItemListener implements ActionListener {

        /**
         * Overrides and checks user's input
         *
         * @param event take user's input
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            panel.removeLast();
        }
    }

    /**
     * Implements ActionListener and adds a Box object onto the select rail car
     * through the panel's method, pushItem()
     */
    
    class PopItemListener implements ActionListener {

        /**
         * Overrides and checks user's input
         *
         * @param event take user's input
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            panel.popItem();
        }
    }

    /**
     * Implements ActionListener and removes the box object and places it back
     * onto the container through the panel's method, popItem()
     */
    class PushItemListener implements ActionListener {

        /**
         * Overrides and checks user's input
         *
         * @param event take user's input
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            panel.pushItem();
        }
    }

    /**
     * Creates the File menu
     *
     * @return the menu
     */
    public JMenu createFileMenu() {
        JMenu menu = new JMenu("File");
        // Create a New button
        JMenuItem newItem = new JMenuItem("New");
        ActionListener fileListener = new NewItemListener();
        newItem.addActionListener(fileListener);
        menu.add(newItem);
        // Create an Exit button
        JMenuItem exitItem = new JMenuItem("Exit");
        ActionListener exitListener = new ExitItemListener();
        exitItem.addActionListener(exitListener);
        menu.add(exitItem);
        // Returning the new items
        return menu;
    }

    /**
     * Creates the List menu
     *
     * @return the menu
     */
    public JMenu createListMenu() {
        JMenu menu = new JMenu("List");
        // Create an Add First button
        JMenuItem addFirstItem = new JMenuItem("AddFirst");
        ActionListener addFirstListener = new AddFirstItemListener();
        addFirstItem.addActionListener(addFirstListener);
        menu.add(addFirstItem);
        // Create an Add Last
        JMenuItem addLastItem = new JMenuItem("AddLast");
        ActionListener addLastListener = new AddLastItemListener();
        addLastItem.addActionListener(addLastListener);
        menu.add(addLastItem);
        // Create a Remove First
        JMenuItem removeFirstItem = new JMenuItem("RemoveFirst");
        ActionListener removeFirstListener = new RemoveFirstItemListener();
        removeFirstItem.addActionListener(removeFirstListener);
        menu.add(removeFirstItem);
        // Create a Remove Last
        JMenuItem removeLastItem = new JMenuItem("RemoveLast");
        ActionListener removeLastListener = new RemoveLastItemListener();
        removeLastItem.addActionListener(removeLastListener);
        menu.add(removeLastItem);
        // Returning the new items
        return menu;
    }

    /**
     * Creates the Stack menu.
     *
     * @return the menu
     */
    public JMenu createStackMenu() {
        JMenu menu = new JMenu("Stack");
        // Create a Pop button
        JMenuItem popItem = new JMenuItem("pop");
        ActionListener popListener = new PopItemListener();
        popItem.addActionListener(popListener);
        menu.add(popItem);
        // Create a Push button
        JMenuItem pushItem = new JMenuItem("push");
        ActionListener pushListener = new PushItemListener();
        pushItem.addActionListener(pushListener);
        menu.add(pushItem);
        // Returning the new items
        return menu;
    }
}
