package com.example.beatbox;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by merz_konstantin on 5/28/17.
 */
public class SoundViewModelTest {
    private BeatBox mBeatBox;
    private Sound mSound;
    private SoundViewModel mSubject; // mSubject ist Testobjekt

    @Before
    public void setUp() throws Exception {
        mBeatBox=mock(BeatBox.class); // erstellt ein mock-objekt. dieses hat die gleichen eigenschaften und methoden, doch die methoden tun nix (brauchen wir, da soundviewmodel von beatbox abhaengt)
        mSound=new Sound("assetPath");
        mSubject=new SoundViewModel(mBeatBox);
        mSubject.setSound(mSound);
    }


    @Test
    public void exposeSoundNameAsTitle(){ // checkt, ob getTitle von SoundViewModel mit Namen des Sounds uebereinstimmt
        assertThat(mSubject.getTitle(), is(mSound.getName()));
    }

    @Test
    public void callsBeatBoxPlayOnButtonClicker(){
        mSubject.onButtonClicked();

        verify(mBeatBox).play(mSound); // verifiziere, dass beim aufruf der methode play(mSound) aufgerufen wurde
    }

}