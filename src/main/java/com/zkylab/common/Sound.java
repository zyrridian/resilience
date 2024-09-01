package com.zkylab.common;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    Clip clip;
    Clip[] clips = new Clip[100];
    URL soundURL[] = new URL[100];
    FloatControl floatControl;
    int volumeScale = 3;
    float volume;

    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/WhiteSpace.wav");
        soundURL[GamePanel.SFX_COIN] = getClass().getResource("/sounds/coin.wav");
        soundURL[GamePanel.SFX_POWER_UP] = getClass().getResource("/sounds/powerup.wav");
        soundURL[GamePanel.SFX_UNLOCK] = getClass().getResource("/sounds/unlock.wav");
        soundURL[GamePanel.SFX_FANFARE] = getClass().getResource("/sounds/fanfare.wav");
        soundURL[GamePanel.SFX_CURSOR] = getClass().getResource("/sounds/cursor.wav");
        // soundURL[6] = getClass().getResource("/sounds/mainmenu.wav");
        soundURL[6] = getClass().getResource("/sounds/Merchant.wav");
        soundURL[7] = getClass().getResource("/sounds/RisingTensions.wav");
        // soundURL[6] = getClass().getResource("/sounds/LostAtASleepover.wav");
        // soundURL[7] = getClass().getResource("/sounds/Friends.wav");
        soundURL[GamePanel.SFX_HIT_MONSTER] = getClass().getResource("/sounds/hitmonster.wav");
        soundURL[GamePanel.SFX_RECEIVE_DAMAGE] = getClass().getResource("/sounds/receivedamage.wav");
        soundURL[GamePanel.SFX_SWORD_SLASH] = getClass().getResource("/sounds/swordslash.wav");
        soundURL[GamePanel.SFX_BURNING] = getClass().getResource("/sounds/burning.wav");
        soundURL[GamePanel.SFX_CUT_TREE] = getClass().getResource("/sounds/cuttree.wav");
        soundURL[GamePanel.SFX_GAME_OVER] = getClass().getResource("/sounds/gameover.wav");
        soundURL[GamePanel.SFX_STAIRS] = getClass().getResource("/sounds/stairs.wav");
        soundURL[GamePanel.SFX_SLEEP] = getClass().getResource("/sounds/sleep.wav");
        soundURL[GamePanel.SFX_BLOCKED] = getClass().getResource("/sounds/blocked.wav");
        soundURL[GamePanel.SFX_PARRY] = getClass().getResource("/sounds/parry.wav");
        soundURL[GamePanel.SFX_SPEAK] = getClass().getResource("/sounds/speak.wav");
        soundURL[19] = getClass().getResource("/sounds/chipwall.wav");
        soundURL[GamePanel.SFX_DOOR_OPEN] = getClass().getResource("/sounds/dooropen.wav");
        // soundURL[21] = getClass().getResource("/sounds/BossBattle.wav");
        soundURL[GamePanel.MUSIC_OUTDOOR] = getClass().getResource("/sounds/MainTheme.wav");
        soundURL[GamePanel.MUSIC_INDOOR] = getClass().getResource("/sounds/MainThemeGood.wav");
        soundURL[GamePanel.MUSIC_DUNGEON] = getClass().getResource("/sounds/MainThemeNeutral.wav");
        soundURL[GamePanel.SFX_DEAD] = getClass().getResource("/sounds/dead.wav");
        soundURL[GamePanel.SFX_EARTHQUAKE] = getClass().getResource("/sounds/earthquake.wav");
        // soundURL[23] = getClass().getResource("/sounds/Credit.wav");

        // Load all sound clips at initialization
        for (int i = 0; i < soundURL.length; i++) {
            if (soundURL[i] != null) {
                try {
                    AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL[i]);
                    clips[i] = AudioSystem.getClip();
                    clips[i].open(audio);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // public void setFile(int i) {
    // try {
    // if (soundURL[i].toString().endsWith(".wav")) {
    // AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL[i]);
    // clip = AudioSystem.getClip();
    // clip.open(audio);
    // floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    // checkVolume();
    // } else if (soundURL[i].toString().endsWith(".ogg")) {
    // // handling ogg music, but later. idk how to do it.
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    public void play(int i) {
        if (clips[i] != null) {
            clips[i].setFramePosition(0); // rewind to the beginning
            clips[i].start();
        }
    }

    public void loop(int i) {
        if (clips[i] != null) {
            clips[i].loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop(int i) {
        if (clips[i] != null) {
            clips[i].stop();
        }
    }

    public void checkVolume() {
        switch (volumeScale) {
            case 0:
                volume = -80F;
                break;
            case 1:
                volume = -20F;
                break;
            case 2:
                volume = -12F;
                break;
            case 3:
                volume = -5F;
                break;
            case 4:
                volume = 1F;
                break;
            case 5:
                volume = 6F;
                break;
        }
        floatControl.setValue(volume);
    }

}
