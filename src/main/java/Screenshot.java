import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Screenshot {

    public static void captureScreen() throws AWTException, IOException {
        Calendar now = Calendar.getInstance();
        Robot robot = new Robot();

        BufferedImage screenShot = robot.createScreenCapture(
                new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(screenShot, "JPG", new File(
                "src/main/captures/image/"+now.getTime()+".jpg"));
    }

    public static void multipleScreenShots() {
        try {
            for (int i = 0; i <= 10; i++) {
                Screenshot.captureScreen();
                TimeUnit.SECONDS.sleep(9);
            }
        } catch (AWTException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
