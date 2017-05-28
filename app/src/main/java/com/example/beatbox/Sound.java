package com.example.beatbox;

/**
 * Created by merz_konstantin on 5/27/17.
 */

public class Sound { // zum managen eines sound-objekts (dateiname, name, etc.)
    private String mAssetPath; // dateiname
    private String mName; // name des sounds
    private Integer mSoundId; // jeder sound wird durch die id identifiziert; Integer: wenn ein sound kein value set hat, bekommt er den wert null

    public Sound(String assetPath){
        mAssetPath=assetPath;
        String[] components = assetPath.split("/");
        String filename=components[components.length-1];
        mName=filename.replace(".wav", ""); // name ist dateiname ohne .wav-endung
    }

    public String getAssetPath(){
        return mAssetPath;
    }

    public Integer getSoundId(){ return mSoundId; }

    public String getName(){
        return mName;
    }


    public void setSoundId(Integer soundId) { mSoundId=soundId; }

}
