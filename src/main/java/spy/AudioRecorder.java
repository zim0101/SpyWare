package spy;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Calendar;

public class AudioRecorder {

    private final AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private TargetDataLine line;

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

    private void saveRecording(AudioFormat format, DataLine.Info info)
            throws IOException, LineUnavailableException {
        Calendar now = Calendar.getInstance();
        File wavFile = new File(
                "src/main/captures/audio/"+now.getTime() + ".wav");
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

    public void stopRecordingAudio(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        finish();
    }
}