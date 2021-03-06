package com.example.beatbox;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.beatbox.databinding.FragmentBeatBoxBinding;
import com.example.beatbox.databinding.ListItemSoundBinding;

import java.util.List;


public class BeatBoxFragment extends Fragment {

    private BeatBox mBeatBox;
    private TextView mPlayBackSpeedView;

    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // haelt BeatBox-Instanz auch bei Rotationen am Leben (wird also nicht zerstoert und neu aufgebaut)

        mBeatBox=new BeatBox(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentBeatBoxBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_beat_box,container,false);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3)); // gridlayout mit jeweils 3 knoepfen in einer zeile
        binding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));
        mPlayBackSpeedView=binding.playbackSpeedView;
        // binding.playbackSpeedView.setText("Playback Speed: "+ 100*mBeatBox.getPlayBackRate() + "%");
        binding.playbackSpeedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mBeatBox.setPlayBackRate(0.5f+((float)progress/100));
                    mPlayBackSpeedView.setText("Playback Speed: "+ 100*mBeatBox.getPlayBackRate() + "%");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mBeatBox.release();
    }


    private class SoundHolder extends RecyclerView.ViewHolder{
        private ListItemSoundBinding mBinding;

        private SoundHolder(ListItemSoundBinding binding){
            super(binding.getRoot());
            mBinding=binding;
            mBinding.setViewModel(new SoundViewModel(mBeatBox)); // bindet SoundViewModel an einen ViewHolder
        }

        public void bind(Sound sound){ // aktualisiere daten, mit denen das ViewModel arbeitet
            mBinding.getViewModel().setSound(sound);
            mBinding.executePendingBindings(); // zwinge layout sich SOFORT zu aktualisieren
        }
    }


    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder>{
        private List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds){
            mSounds=sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            ListItemSoundBinding binding = DataBindingUtil.inflate(inflater,R.layout.list_item_sound,parent,false);
            return new SoundHolder(binding);
        }

        @Override
        public void onBindViewHolder(SoundHolder holder, int position){
            Sound sound=mSounds.get(position);
            holder.bind(sound);
        }

        @Override
        public int getItemCount(){
            return mSounds.size();
        }
    }
}
