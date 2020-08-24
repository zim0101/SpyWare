package spy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

public class Screenshot {
    private static final Logger LOGGER = LoggerFactory.getLogger(Screenshot.class);

    /**
     * Image upload directory
     */
    private final static String IMAGE_UPLOAD_PATH = "src/main/captures/image/";

    /**
     * Image format
     */
    private final static String IMAGE_FORMAT = "JPG";

    /**
     * Take screenshot and save into image directory
     */
    public void captureScreen() {
        try {
            Robot robot = new Robot();

            Dimension toolkit = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle rectangle = new Rectangle(toolkit);
            BufferedImage screenShot = robot.createScreenCapture(rectangle);
            String path = IMAGE_UPLOAD_PATH + System.currentTimeMillis() + ".jpg";

            ImageIO.write(screenShot, IMAGE_FORMAT, new File(path));
        } catch (AWTException | IOException exception) {
            LOGGER.error("Unable to capture image", exception);
        }
    }
}