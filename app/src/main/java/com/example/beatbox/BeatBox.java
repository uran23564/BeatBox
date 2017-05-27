package com.example.beatbox;

import android.content.Context;
import android.content.res.AssetManager;
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

    // auf assets wird mittels der assetmanager-klasse zugegriffen
    private AssetManager mAssets;
    private List<Sound> mSounds=new ArrayList<>();

    public BeatBox(Context context){ // context meistens wurscht, da der assetmanaget des contexts mit der selben menge von assets verknuepft ist
        mAssets=context.getAssets();
        loadSounds();
    }

    public List<Sound> getSounds(){
        return mSounds;
    }


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
            String assetPath=SOUNDS_FOLDER+"/"+filename;
            Sound sound = new Sound(assetPath);
            mSounds.add(sound);
        }
    }


}
