import javax.sound.sampled.*;
import java.io.*;


public class Attendance {

    // record duration, in milliseconds
    public static final long RECORD_TIME = 7000;  // 7 seconds
    // path of the wav file
    //store on local machine
    public File wavFile = new File("C:\\Users\\QaissKhanAlokozai\\Documents\\FinalSound\\checkRecord.wav");
    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // the line from which audio data is captured
    TargetDataLine line;
     /**
     * Defines an audio format
     */
    public AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,channels, signed, bigEndian);
        return format;
    }//end getAudioFormat()
    /**
     * Captures the sound and record into a WAV file
     */
    public void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
             // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing
             System.out.println("Start capturing...");
             AudioInputStream ais = new AudioInputStream(line);
             System.out.println("Start recording...");
             // start recording
            AudioSystem.write(ais, fileType, wavFile);
         } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }//end start()
     /**
     * Closes the target data line to finish capturing and recording
     */
    public void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }//end finish()
 
    /**
     * Entry to run the program
     */
   
    public void voiceRecord() 
      {
        final Attendance AttendanceRecorder = new Attendance();
         // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(RECORD_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                AttendanceRecorder.finish();
            }
        });
         stopper.start();
         // start recording
        AttendanceRecorder.start();
    }//end method voiceRecords()
    
}//end method Attendance()
