package net.babybaby.agijagi.today_recommand;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.babybaby.agijagi.R;
import net.babybaby.agijagi.cooksearch.recommandmeal.mealnutrient.MealNutrientModel;
import net.babybaby.agijagi.etc.HttpGetRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by FlaShilver on 2013. 10. 4..
 */
public class Today_recommand_Activity extends Fragment {

    ArrayList<TodayRecommandModel> al = new ArrayList<TodayRecommandModel>();
    private Handler mHandler = new Handler();
    private boolean loading;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;

    TextView textview1;
    TextView textview2;
    TextView textview3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_today_recommand, container, false);


        imageView1 = (ImageView) rootView.findViewById(R.id.imageview1);
        imageView2 = (ImageView) rootView.findViewById(R.id.imageview2);
        imageView3 = (ImageView) rootView.findViewById(R.id.imageview3);

        textview1 = (TextView) rootView.findViewById(R.id.textview1);
        textview2 = (TextView) rootView.findViewById(R.id.textview2);
        textview3 = (TextView) rootView.findViewById(R.id.textview3);
         /*
        updatescreen();
           */
        return rootView;
    }
/*
    private void updatescreen() {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "불러오는 중입니다. 잠시 기다려 주세요", true);

        new Thread() {
            public void run() {
                try {
                    HttpGetRequest hgr = new HttpGetRequest();


                    String result = hgr.getHTML("http://babyhoney.kr/api/getRecentRecommends");

                    JSONObject response = new JSONObject(result);
                    JSONObject channel = response.getJSONObject("channel");
                    JSONArray item = channel.getJSONArray("item");
                    for (int i = 0; i < item.length(); i++) {
                        JSONArray list = item.getJSONArray(i);
                        for (int j = 0; j < 1; j++) {
                            TodayRecommandModel trm = new TodayRecommandModel();
                            JSONObject j_id = list.getJSONObject(j);
                            JSONObject j_name = list.getJSONObject(j);
                            JSONObject j_img_ = list.getJSONObject(j);

                            trm.setId(j_id.getInt("id"));
                            trm.setName(j_name.getString("name"));
                            trm.setImg(j_img_.getString("image"));

                            al.add(trm);
                        }
                    }

                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    public void run() {
                        loading = true;
                        textview1.setText(al.get(0).getName());
                        textview2.setText(al.get(1).getName());
                        textview3.setText(al.get(2).getName());
                        dialog.hide();
                    }
                });
            }
        }.start();
    }
    */
}
