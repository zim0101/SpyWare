package spy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.*;

public class AudioRecorder {
    private static final Logger LOGGER = LoggerFactory.getLogger(AudioRecorder.class);

    /**
     * Audio file type
     */
    private static final AudioFileFormat.Type FILE_TYPE = AudioFileFormat.Type.WAVE;

    /**
     * Data line to read audio
     */
    private TargetDataLine line;

    /**
     * Root directory for saving audio files
     */
    private final String UPLOAD_PATH = "src/main/captures/audio/";

    /**
     * Defines an audio format
     */
    private AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;

        return new AudioFormat(sampleRate, sampleSizeInBits, channels,
                true, true);
    }

    /**
     * Captures the sound and record into a WAV file
     */
    public void startRecording() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                LOGGER.error("Line not supported, existing"); // use proper log message
                System.exit(0);
            }

            saveRecording(format, info);
        } catch (IOException | LineUnavailableException exception) {
            LOGGER.error("Unable to record audio", exception);
        }
    }

    /**
     * Save audio into wav files
     *
     * @param format AudioFormat
     * @param info   DataLine Info
     * @throws IOException              I/O exception
     * @throws LineUnavailableException line unavailable exception
     */
    private void saveRecording(AudioFormat format, DataLine.Info info)
            throws IOException, LineUnavailableException {

        File wavFile = new File(UPLOAD_PATH + System.currentTimeMillis() + ".wav");
        line = (TargetDataLine) AudioSystem.getLine(info);
        AudioInputStream audioInputStream = new AudioInputStream(line);

        line.open(format);
        line.start();

        AudioSystem.write(audioInputStream, FILE_TYPE, wavFile);
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    public void finish() {
        line.stop();
        line.close();
    }

    /**
     * A thread will remain sleeping until sleepTime, then the thread will
     * execute the method to finish recording
     *
     * @param sleepTime Duration of recording. After this time being
     *                  recording will finish
     */
    public void stopRecordingAudio(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new AssertionError(ex);
        }
        finish();
    }
}