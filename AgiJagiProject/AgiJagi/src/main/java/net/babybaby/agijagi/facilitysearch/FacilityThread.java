package net.babybaby.agijagi.facilitysearch;


import android.text.Editable;

import net.babybaby.agijagi.etc.HttpGetRequest;
import net.babybaby.agijagi.login.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by FlaShilver on 2013. 10. 6..
 */
public class FacilityThread extends Thread {
    static Editable arg;

    public void run() {

        String result = null;

        HttpGetRequest hgr = new HttpGetRequest();

        result = hgr.getHTML("http://babyhoney.kr/api/getOrganization/?query=" + URLEncoder.encode(String.valueOf(arg)));

        try {
            JSONObject response = new JSONObject(result);
            JSONObject channel = response.getJSONObject("channel");
            JSONArray item = channel.getJSONArray("item");

            for (int i = 0; i < item.length(); i++) {
                JSONObject obj = item.getJSONObject(i);
                FacilityModel facilityModel = new FacilityModel();

                facilityModel.setID(obj.getInt("id"));
                facilityModel.setName(obj.getString("name"));
                facilityModel.setType(obj.getInt("type"));
                facilityModel.setTelephone(obj.getString("telephone"));
                facilityModel.setAddress(obj.getString("description"));
                FacilitySearchFragment.lists.add(facilityModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
