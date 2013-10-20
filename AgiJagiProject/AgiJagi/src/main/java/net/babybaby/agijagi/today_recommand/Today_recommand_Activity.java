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
    LinearLayout ll;
    private boolean loading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_today_recommand, container, false);

        //       ll = (LinearLayout) rootView.findViewById(R.id.idid);
/*
        ImageView imageView1 = (ImageView) rootView.findViewById(R.id.imageview1);
        ImageView imageView2 = (ImageView) rootView.findViewById(R.id.imageview2);
        ImageView imageView3 = (ImageView) rootView.findViewById(R.id.imageview3);

        TextView textview1 = (TextView) rootView.findViewById(R.id.textview1);
        TextView textview2 = (TextView) rootView.findViewById(R.id.textview2);
        TextView textview3 = (TextView) rootView.findViewById(R.id.textview3);
*/
        //updatescreen();


        return rootView;
    }
/*
    private void updatescreen() {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "불러오는 중입니다. 잠시 기다려 주세요", true);

        new Thread() {
            public void run() {
                try {
                    HttpGetRequest hgr = new HttpGetRequest();
                    ;

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

                            int id = j_id.getInt("id");
                            String name = j_name.getString("name");
                            String img = j_img_.getString("image");
                            trm.setId(id);
                            trm.setName(name);
                            trm.setImg(img);

                            al.add(trm);
                        }
                    }

                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    public void run() {

                        setAimage(al);
                        loading = true;
                        dialog.hide();
                    }
                });
            }
        }.start();
    }

    private void setAimage(ArrayList al) {

        View v = null;

        if (v == null) {

            LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.id.idid, null);
        }

        ImageView imageView1 = (ImageView) v.findViewById(R.id.imageview1);
        ImageView imageView2 = (ImageView) v.findViewById(R.id.imageview2);
        ImageView imageView3 = (ImageView) v.findViewById(R.id.imageview3);

        TextView textview1 = (TextView) v.findViewById(R.id.textview1);
        TextView textview2 = (TextView) v.findViewById(R.id.textview2);
        TextView textview3 = (TextView) v.findViewById(R.id.textview3);

        textview1.setText(al.get(0).toString());
        textview1.setText(al.get(1).toString());
        textview1.setText(al.get(2).toString());
    }  */

}
