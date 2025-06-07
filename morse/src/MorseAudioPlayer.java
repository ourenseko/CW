import javax.sound.sampled.*;

public class MorseAudioPlayer {

    public static void reproducirMorse(String morse) {
        for (char c : morse.toCharArray()) {
            switch (c) {
                case '.':
                    beep(800, 100);  // Frecuencia 800 Hz, duración 100 ms
                    pausa(100);
                    break;
                case '-':
                    beep(800, 300);  // Frecuencia 800 Hz, duración 300 ms
                    pausa(100);
                    break;
                case ' ':
                    pausa(300);  // Pausa más larga entre letras/palabras
                    break;
            }
        }
    }

    private static void beep(int freq, int duration) {
        try {
            float sampleRate = 44100;
            byte[] buf = new byte[1];
            AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
            SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);
            sdl.start();
            for (int i = 0; i < duration * (int) sampleRate / 1000; i++) {
                double angle = i / (sampleRate / freq) * 2.0 * Math.PI;
                buf[0] = (byte) (Math.sin(angle) * 127);
                sdl.write(buf, 0, 1);
            }
            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void pausa(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
