package com.example.beatbox;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by merz_konstantin on 5/27/17.
 */

public class SoundViewModel extends BaseObservable{ // verknuepft den view (list_item_sound.xml) und das model Sound (MVVM)
    private Sound mSound;
    private BeatBox mBeatBox;

    public SoundViewModel(BeatBox beatBox){
        mBeatBox=beatBox;
    }

    public Sound getSound(){
        return mSound;
    }

    @Bindable
    public String getTitle(){ // zum darstellen des namens auf dem knopf
        return mSound.getName();
    }

    public void setSound(Sound sound){
        mSound=sound;
        notifyChange(); // sagt der Binding-Klasse, dass alle Bindable-Fields (hier der Titel) der Objekte aktualisiert wurden
    }
}
