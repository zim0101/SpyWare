package spy;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Calendar;

public class AudioRecorder {

    /**
     * Audio file type
     */
    private final AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    /**
     * Data line to read audio
     */
    private TargetDataLine line;

    /**
     * Root directory for saving audio files
     */
    private final String uploadPath = "src/main/captures/audio/";

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
                System.out.println("Line not supported");
                System.exit(0);
            }

            saveRecording(format, info);
        } catch (IOException | LineUnavailableException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Save audio into wav files
     *
     * @param format AudioFormat
     * @param info DataLine Info
     * @throws IOException I/O exception
     * @throws LineUnavailableException line unavailable exception
     */
    private void saveRecording(AudioFormat format, DataLine.Info info)
            throws IOException, LineUnavailableException {

        Calendar now = Calendar.getInstance();
        File wavFile = new File(uploadPath + now.getTime() + ".wav");
        line = (TargetDataLine) AudioSystem.getLine(info);
        AudioInputStream audioInputStream = new AudioInputStream(line);

        line.open(format);
        line.start();

        AudioSystem.write(audioInputStream, fileType, wavFile);
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
            ex.printStackTrace();
        }
        finish();
    }
}