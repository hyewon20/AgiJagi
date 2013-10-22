package net.babybaby.agijagi.login;

import android.util.Log;

import net.babybaby.agijagi.etc.HttpGetRequest;
import net.babybaby.agijagi.today_recommand.TodayRecommandModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by FlaShilver on 2013. 10. 22..
 */
public class MainViewThread extends Thread {

    ArrayList<TodayRecommandModel> al = new ArrayList<TodayRecommandModel>();

    public void run(){
        HttpGetRequest hgr = new HttpGetRequest();

        String result = hgr.getHTML("http://babyhoney.kr/api/getRecentRecommends");

        Log.d("resres",result);

        JSONObject response = null;
        try {
            response = new JSONObject(result);
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

                    Log.d("idid", ""+j_id.getInt("id"));
                    Log.d("namename", ""+j_id.getInt("name"));
                    Log.d("imageimg", ""+j_id.getInt("image"));
                    al.add(trm);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
