package com.example.selfcare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arges.sepan.argmusicplayer.Models.ArgAudio;
import com.arges.sepan.argmusicplayer.Models.ArgAudioList;
import com.arges.sepan.argmusicplayer.PlayerViews.ArgPlayerFullScreenView;
import com.arges.sepan.argmusicplayer.PlayerViews.ArgPlayerSmallView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMusic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMusic extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentMusic() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMusic.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMusic newInstance(String param1, String param2) {
        FragmentMusic fragment = new FragmentMusic();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_music, container, false);
        String[] url =new String[5];
        for(int i=0;i<5;i++)
            url[i]="https://www.gotinenstranan.com/songs/joan-baez-north-country-blues.mp3";

        ArgAudio audio1 = ArgAudio.createFromURL("Joan Baez", "North Country Blues", url[0]);
        ArgAudio audio2 = ArgAudio.createFromURL("Ritik Rawat", "Stupid Lover", url[1]);
        ArgAudio audio3 = ArgAudio.createFromURL("Mary Angeline", "Will You Be My Wife", url[2]);
        ArgAudio audio4 = ArgAudio.createFromURL("Harry Potter", "She Loves It", url[3]);
        ArgAudio audio5 = ArgAudio.createFromURL("Josh Petterson", "I Don't Kow Him", url[4]);


        ArgAudioList playlist = new ArgAudioList(true).add(audio1)
                .add(audio2)
                .add(audio3)
                .add(audio4)
                .add(audio5);

        ArgPlayerFullScreenView argMusicPlayer = view.findViewById(R.id.musicPlayer);
        argMusicPlayer.playAudioAfterPercent(0);
        return view;
    }
}