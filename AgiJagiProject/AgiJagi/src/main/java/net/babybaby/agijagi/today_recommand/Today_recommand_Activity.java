package net.babybaby.agijagi.today_recommand;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import net.babybaby.agijagi.R;

/**
 * Created by FlaShilver on 2013. 10. 4..
 */
public class Today_recommand_Activity extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_today_recommand, container, false);

        ImageView imageView1 = (ImageView) rootView.findViewById(R.id.imageview1);
        ImageView imageView2 = (ImageView) rootView.findViewById(R.id.imageview2);
        ImageView imageView3 = (ImageView) rootView.findViewById(R.id.imageview3);

        TextView textview1 = (TextView) rootView.findViewById(R.id.textview1);
        TextView textview2 = (TextView) rootView.findViewById(R.id.textview2);
        TextView textview3 = (TextView) rootView.findViewById(R.id.textview3);






        return rootView;
    }
}
