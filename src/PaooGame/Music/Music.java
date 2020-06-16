package PaooGame.Music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    private static AudioInputStream audioStream;
    private Clip audioClip;
    private String name;

    public Music(String Path){
        name = Path;
    }

    public void PlayMusic() {
        try {
            audioStream = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream(name));

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            audioClip = AudioSystem.getClip();
            Line line = AudioSystem.getLine(info);
            if (!line.isOpen()) {
                audioClip.open(audioStream);
                audioClip.loop(Clip.LOOP_CONTINUOUSLY);
                audioClip.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void StopMusic() throws IOException {
        audioClip.close();
        audioStream.close();
    }
}
