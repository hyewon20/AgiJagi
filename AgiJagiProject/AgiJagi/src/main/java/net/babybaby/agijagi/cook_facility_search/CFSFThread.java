package net.babybaby.agijagi.cook_facility_search;

import android.content.SharedPreferences;
import android.text.Editable;
import android.util.Log;
import net.babybaby.agijagi.etc.HttpGetRequest;
import net.babybaby.agijagi.login.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by FlaShilver on 2013. 10. 6..
 */
public class CFSFThread extends Thread {
    static boolean IsCook = true;
    static Editable arg;

    public void run() {

        String result = null;

        HttpGetRequest hgr = new HttpGetRequest();
        if(IsCook == true){
            result = hgr.getHTML("http://babyhoney.kr/api/GetNutritionist/?username="+ Login.id+"&password="+Login.password+"&page=0&offset=10&location=" + URLEncoder.encode(String.valueOf(arg)));
        } else if(IsCook == false){
            result = hgr.getHTML("http://babyhoney.kr/api/GetOrganization/?page=0&offset=10&location="+ URLEncoder.encode(String.valueOf(arg)));
        }

        Log.d("id",""+Login.id+Login.password);

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

                cfsModel.setLocation(obj.getString("location"));
                Log.d("cfsModel",obj.getString("location"));
                cfsModel.setTelephone(obj.getString("telephone"));
                Log.d("cfsModel",obj.getString("telephone"));
                if(IsCook == true){
                    cfsModel.setCertification_no(obj.getString("certiciation_no"));
                    Log.d("cfsModel",""+obj.getString("certiciation_no"));

                } else if(IsCook == false){
                    cfsModel.setDescription(obj.getString("description"));
                    Log.d("cfsModel",""+obj.getString("description"));

                }
                CookFacilitySearchFragment.lists.add(cfsModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
