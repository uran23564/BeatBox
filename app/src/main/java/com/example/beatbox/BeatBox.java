package com.example.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by merz_konstantin on 5/27/17.
 */

public class BeatBox { // managed alle sound-objekte
    private static final String TAG="BeatBox";
    private static final String SOUNDS_FOLDER="sample_sounds";
    private static final int MAX_SOUNDS=5; // maximal 5sounds koennen gleichzeitig gespielt werden

    // auf assets wird mittels der assetmanager-klasse zugegriffen
    private AssetManager mAssets;
    private List<Sound> mSounds=new ArrayList<>();
    private SoundPool mSoundPool;
    private float mPlayBackRate=1.0f;

    public BeatBox(Context context){ // context meistens wurscht, da der assetmanaget des contexts mit der selben menge von assets verknuepft ist
        mAssets=context.getAssets();
        // alter konstruktor, den wir zwecks kompatibilitaetsgruenden dennoch brauchen -- ab lollipop Soundpool.Builder
        mSoundPool=new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC,0);
        loadSounds();
    }

    public List<Sound> getSounds(){
        return mSounds;
    }

    public float getPlayBackRate(){ return mPlayBackRate; }

    public void setPlayBackRate(float playBackRate){ mPlayBackRate=playBackRate; }


    private void loadSounds(){
        String[] soundNames;
        try{ // erstmal schauen, obs ueberhaupt sounds gibt
            soundNames=mAssets.list(SOUNDS_FOLDER); // listet dateinamen im entsprechenden asset-unterordner auf -> zeigt alle wav-dateien
            Log.i(TAG,"Found " + soundNames.length+" sounds");
        } catch(IOException ioe){
            Log.e(TAG, "Could not list assets", ioe);
            return;
        }

        for(String filename:soundNames){ // verfuegbare sounds zum array hinzufuegen
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException ioe){
                Log.e(TAG,"Could not load sound " + filename, ioe);
            }
        }
    }

    private void load(Sound sound) throws IOException{ // laedt einen sound in den soundpool
        AssetFileDescriptor afd=mAssets.openFd(sound.getAssetPath());
        int soundId=mSoundPool.load(afd,1); // file wird in soundpool geladen
        sound.setSoundId(soundId);
    }

    public void play(Sound sound){
        Integer soundId=sound.getSoundId();
        if(soundId==null){ return; }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, mPlayBackRate);
    }

    public void release(){ // soundpool aufraeumen
        mSoundPool.release();
    }

}
