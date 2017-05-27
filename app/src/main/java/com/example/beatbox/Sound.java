package com.example.beatbox;

/**
 * Created by merz_konstantin on 5/27/17.
 */

public class Sound { // zum managen eines sound-objekts (dateiname, name, etc.)
    private String mAssetPath; // dateiname
    private String mName; // name des sounds

    public Sound(String assetPath){
        mAssetPath=assetPath;
        String[] components = assetPath.split("/");
        String filename=components[components.length-1];
        mName=filename.replace(".wav", ""); // name ist dateiname ohne .wav-endung
    }

    public String getAssetPath(){
        return mAssetPath;
    }

    public String getName(){
        return mName;
    }

}
