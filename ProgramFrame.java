import javax.swing.*;
import java.awt.*;

/**
 * @since 27.05.2021
 * @author Ronny Klotz
 * Application window configuration
 */
public class ProgramFrame extends JFrame {

    /**
     * Constructor for setting up the values and configurated them
     */
    public ProgramFrame() {

        // Adds the draw Panel to the window
        this.add(new Setup());

        // Change the Title of the window
        this.setTitle("The Game of Life");

        // Default closing operation set to "Close program after closing window"
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Window isnt resizable
        this.setResizable(false);

        // Pack all components into the Frame
        this.pack();

        // Window appears in the middle of the screen
        this.setLocationRelativeTo(null);

        // Make it visible
        this.setVisible(true);

        // Set the application to fullscreen mode
        fullscreenMode();
    }

    private void fullscreenMode () {
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();

        device.setFullScreenWindow(this);
    }

}
