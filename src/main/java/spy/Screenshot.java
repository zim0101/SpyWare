package spy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Screenshot {

    public static void captureScreen() {
        try {
            Calendar now = Calendar.getInstance();
            Robot robot = new Robot();

            BufferedImage screenShot = robot.createScreenCapture(
                    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(screenShot, "JPG", new File(
                    "src/main/captures/image/" + now.getTime() + ".jpg"));
        } catch (AWTException | IOException exception) {
            exception.printStackTrace();
        }
    }
}
