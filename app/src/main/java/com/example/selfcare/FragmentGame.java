package com.example.selfcare;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGame extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentGame() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGame.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGame newInstance(String param1, String param2) {
        FragmentGame fragment = new FragmentGame();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_game, container, false);

        Button start = view.findViewById(R.id.start);
        CardView cardNumber= view.findViewById(R.id.cardNumber);
        TextView word =view.findViewById(R.id.word);
        TextView counter =view.findViewById(R.id.counter);
        GridLayout gridLayout =view.findViewById(R.id.grid);
        CardView cardNumber1=view.findViewById(R.id.cardNumber1);
        CardView cardNumber2=view.findViewById(R.id.cardNumber2);
        CardView cardNumber3=view.findViewById(R.id.cardNumber3);
        CardView cardNumber4=view.findViewById(R.id.cardNumber4);
        TextView word1 =view.findViewById(R.id.word1);
        TextView word2=view.findViewById(R.id.word2);
        TextView word3 =view.findViewById(R.id.word3);
        TextView word4 =view.findViewById(R.id.word4);
        LottieAnimationView animationView=view.findViewById(R.id.animation);

        cardNumber.setVisibility(View.INVISIBLE);
        gridLayout.setVisibility(View.INVISIBLE);
        animationView.setVisibility(View.INVISIBLE);
        counter.setVisibility(View.INVISIBLE);

        String s="seven world about again heart pizza water happy sixty board month Angel death green music fifty three party piano Kelly mouth woman sugar amber dream apple laugh tiger faith earth river money peace forty words smile abate house alone watch lemon South erica anime after santa women admin Jesus china";
        String[] words=s.split(" ");
        Random random = new Random();
        start.setVisibility(View.VISIBLE);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              start.setVisibility(View.INVISIBLE);
              cardNumber.setTranslationX(800f);
              int index=random.nextInt(words.length);
              word.setText(words[index]);
              cardNumber.setVisibility(View.VISIBLE);
                ObjectAnimator animation = ObjectAnimator.ofFloat(cardNumber, "translationX", -800f);
                animation.setDuration(3000);
                animation.start();


                long countdown=7000;
                new CountDownTimer(countdown, 1000) {

                    public void onTick(long millisUntilFinished) {
                        counter.setText(String.valueOf(millisUntilFinished/1000));
                        if(millisUntilFinished<=5000)
                            counter.setVisibility(View.VISIBLE);
                    }

                    public void onFinish() {
                        counter.setVisibility(View.INVISIBLE);
                        cardNumber.setVisibility(View.INVISIBLE);
                        String[] options=new String[4];
                        options[0]=words[index];


                        for(int i=1;i<4;i++)
                        {
                           int k= random.nextInt(words.length);
                            options[i]=words[k];
                        }

                        int mix=random.nextInt(4);
                        String temp=options[mix];
                        options[mix]=options[0];
                        options[0]=temp;

                       word1.setText(options[0]);
                        word2.setText(options[1]);
                        word3.setText(options[2]);
                        word4.setText(options[3]);

                        gridLayout.setVisibility(View.VISIBLE);

                        cardNumber1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(word1.getText().toString().equals(words[index]))
                                {
                                    gridLayout.setVisibility(View.INVISIBLE);
                                    animationView.setVisibility(View.VISIBLE);
                                    animationView.playAnimation();
                                    animationView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            animationView.setVisibility(View.INVISIBLE);
                                            start.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }
                                else
                                {
                                    word1.setText("WRONG");
                                }

                            }
                        });


                        cardNumber2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(word2.getText().toString().equals(words[index]))
                                {
                                    gridLayout.setVisibility(View.INVISIBLE);
                                    animationView.setVisibility(View.VISIBLE);
                                    animationView.playAnimation();
                                    animationView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            animationView.setVisibility(View.INVISIBLE);
                                            start.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }
                                else
                                {
                                    word2.setText("WRONG");
                                }

                            }
                        });

                        cardNumber3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(word3.getText().toString().equals(words[index]))
                                {
                                    gridLayout.setVisibility(View.INVISIBLE);
                                    animationView.setVisibility(View.VISIBLE);
                                    animationView.playAnimation();
                                    animationView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            animationView.setVisibility(View.INVISIBLE);
                                            start.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }
                                else
                                {
                                    word3.setText("WRONG");
                                }

                            }
                        });





                        cardNumber4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(word4.getText().toString().equals(words[index]))
                                {
                                    gridLayout.setVisibility(View.INVISIBLE);
                                    animationView.setVisibility(View.VISIBLE);
                                    animationView.playAnimation();
                                    animationView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            animationView.setVisibility(View.INVISIBLE);
                                            start.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }
                                else
                                {
                                    word4.setText("WRONG");
                                }

                            }
                        });






                    }

                }.start();



            }
        });


        return view;
    }
}