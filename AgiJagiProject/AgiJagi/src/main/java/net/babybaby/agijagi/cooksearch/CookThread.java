package net.babybaby.agijagi.cooksearch;


import android.text.Editable;
import android.util.Log;

import net.babybaby.agijagi.cooksearch.CookModel;
import net.babybaby.agijagi.etc.HttpGetRequest;
import net.babybaby.agijagi.login.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by FlaShilver on 2013. 10. 6..
 */
public class CookThread extends Thread {
    static Editable arg;

    public void run() {

        String result = null;

        HttpGetRequest hgr = new HttpGetRequest();

        result = hgr.getHTML("http://babyhoney.kr/api/GetNutritionist/?username=" + Login.id + "&password=" + Login.password + "&page=0&offset=10&location=" + URLEncoder.encode(String.valueOf(arg)));
        try {
            JSONObject response = new JSONObject(result);
            JSONObject channel = response.getJSONObject("channel");
            JSONArray item = channel.getJSONArray("item");

            for (int i = 0; i < item.length(); i++) {
                JSONObject obj = item.getJSONObject(i);
                CookModel cookModel = new CookModel();

                cookModel.setName(obj.getString("name"));
                cookModel.setId(obj.getString("id"));
                cookModel.setJoin_date(obj.getString("creation_date"));

                CookSearchFragment.lists.add(cookModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
