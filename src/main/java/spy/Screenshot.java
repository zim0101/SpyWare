package spy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class Screenshot {

    /**
     * Image upload directory
     */
    private final static String uploadPath = "src/main/captures/image/";

    /**
     * Image format
     */
    private final static String format = "JPG";

    /**
     * Take screenshot and save into image directory
     */
    public static void captureScreen() {
        try {
            Calendar now = Calendar.getInstance();
            Robot robot = new Robot();

            Dimension toolkit = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle rectangle = new Rectangle(toolkit);
            BufferedImage screenShot = robot.createScreenCapture(rectangle);
            String path = uploadPath + now.getTime() + ".jpg";

            ImageIO.write(screenShot, format, new File(path));

        } catch (AWTException | IOException exception) {
            exception.printStackTrace();
        }
    }
}
