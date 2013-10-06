package net.babybaby.agijagi.cook_facility_search;

import android.util.Log;

import net.babybaby.agijagi.etc.HttpGetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by FlaShilver on 2013. 10. 6..
 */
public class CFSFThread extends Thread {
    public void run() {

        HttpGetRequest hgr = new HttpGetRequest();
        String arg = "서울";
        String result = hgr.getHTML("http://babyhoney.kr/api/GetNutritionist/?username=jong327&password=ac619ef29c44938cbf0a619f5029ff47&page=0&offset=2&location=" + URLEncoder.encode(arg));

        Log.d("cfsf", result);

        try {
            JSONObject response = new JSONObject(result);
            JSONObject channel = response.getJSONObject("channel");
            JSONArray item = channel.getJSONArray("item");

            for(int i=0; i<item.length();i++){
                JSONObject obj = item.getJSONObject(i);
                CookFacilitySearchModel cfsModel = new CookFacilitySearchModel();
                cfsModel.setName(obj.getString("name"));
                Log.d("cfsModel",obj.getString("name"));
                cfsModel.setCertification_no(obj.getString("certiciation_no"));
                Log.d("cfsModel",""+obj.getString("certiciation_no"));
                cfsModel.setLocation(obj.getString("location"));
                Log.d("cfsModel",obj.getString("location"));
                cfsModel.setTelephone(obj.getString("telephone"));
                Log.d("cfsModel",obj.getString("telephone"));

                CookFacilitySearchFragment.lists.add(cfsModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
