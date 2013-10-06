package net.babybaby.agijagi.weekly_meal;

import java.util.Calendar;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import net.babybaby.agijagi.etc.HttpGetRequest;
import net.babybaby.agijagi.recipe_detail.RecipeListModel;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.babybaby.agijagi.R;


public class Weekly_Meal_Activity extends Fragment {

    Handler mHandler = new Handler();
    private int[][] day;
    View rootView = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_weekly_meal, container, false);

        HttpGetRequest hgr = new HttpGetRequest();
        String dest = "http://babyhoney.kr/api/getMenu";
        String url = hgr.getHTML(dest);

        Log.d("url",""+url);

        day = new int[8][5];
        day[1][1] = R.id.day1_1;
        day[1][2] = R.id.day1_2;
        day[1][3] = R.id.day1_3;
        day[1][4] = R.id.day1_4;
        day[2][1] = R.id.day2_1;
        day[2][2] = R.id.day2_2;
        day[2][3] = R.id.day2_3;
        day[2][4] = R.id.day2_4;
        day[3][1] = R.id.day3_1;
        day[3][2] = R.id.day3_2;
        day[3][3] = R.id.day3_3;
        day[3][4] = R.id.day3_4;
        day[4][1] = R.id.day4_1;
        day[4][2] = R.id.day4_2;
        day[4][3] = R.id.day4_3;
        day[4][4] = R.id.day4_4;
        day[5][1] = R.id.day5_1;
        day[5][2] = R.id.day5_2;
        day[5][3] = R.id.day5_3;
        day[5][4] = R.id.day5_4;
        day[6][1] = R.id.day6_1;
        day[6][2] = R.id.day6_2;
        day[6][3] = R.id.day6_3;
        day[6][4] = R.id.day6_4;

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "정보를 읽어오는 중입니다. 잠시만 기다려주세요.", true);
        new Thread() {
            public void run() {
                try {
                    HttpGetRequest hgr = new HttpGetRequest();
                    String dest = "http://babyhoney.kr/api/getMenu";
                    String url = hgr.getHTML(dest);

                    Log.d("url",""+url);

                    JSONObject response = new JSONObject(url);
                    JSONObject channel = response.getJSONObject("channel");
                    JSONArray data = channel.getJSONArray("item");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);
                        String date = obj.getString("date");
                        String part = obj.getString("part");
                        JSONArray list = obj.getJSONArray("list");

                        String result_name = "";

                        for (int j = 0; j < list.length(); j++) {
                            JSONObject obj2 = list.getJSONObject(j);
                            String name = obj2.getString("name");

                            result_name += name;
                            if (j + 1 != list.length())
                                result_name += "\n";
                        }
                        mHandler.post(new Runnable() {
                            public void run() {

                                dialog.hide();

                            }
                        });

                        Calendar cal = Calendar.getInstance();
                        int year = Integer.parseInt(date.substring(0, 4));
                        int month = Integer.parseInt(date.substring(5, 7));
                        int dt = Integer.parseInt(date.substring(8, 10));
                        cal.set(year, month, dt);

                        int day_sw = 1;
                        if (cal.get(Calendar.DAY_OF_WEEK) == 7)
                            day_sw = 3;
                        else if (cal.get(Calendar.DAY_OF_WEEK) == 1)
                            day_sw = 4;
                        else if (cal.get(Calendar.DAY_OF_WEEK) == 2)
                            day_sw = 5;
                        else if (cal.get(Calendar.DAY_OF_WEEK) == 3)
                            day_sw = 6;
                        else if (cal.get(Calendar.DAY_OF_WEEK) == 5)
                            day_sw = 1;
                        else if (cal.get(Calendar.DAY_OF_WEEK) == 6)
                            day_sw = 2;


                        TextView tv = (TextView) rootView.findViewById(day[day_sw][Integer.parseInt(part) + 1]);
                        tv.setText(result_name);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return rootView;
    }


}
