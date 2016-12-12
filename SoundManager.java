package com.lindsaykroeger.mygame;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Lindsay on 11/28/2016.
 */

public class SoundManager {

    private static SoundManager instance = null;

    private SoundPool soundPool;
    private HashMap<String, Integer> soundMap;
    private MediaPlayer mediaPlayer;

    SoundManager() {

    }

    public void loadSounds(Context context) {

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundMap = new HashMap<String, Integer>();
        AssetManager assetManager = context.getAssets();

        try{
            String[] files = assetManager.list("sfx");

            for(String file : files) {
                AssetFileDescriptor afd = assetManager.openFd("sfx/" + file);
                int index = soundPool.load(afd, 0);
                soundMap.put(file, index);
            }

        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    public String[] getKeys() {

        String[] temp = soundMap.keySet().toArray(new String[0]);
        return temp;

    }

    public void playSound(String sound, int loop) {

        soundPool.play(soundMap.get(sound), 1.0f, 1.0f, 0, loop, 0.0f);

    }

    public void stop(){
        soundPool.autoPause();
    }

}
