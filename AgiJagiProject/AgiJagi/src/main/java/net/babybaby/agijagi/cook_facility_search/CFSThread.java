package net.babybaby.agijagi.cook_facility_search;

import android.util.Log;
import net.babybaby.agijagi.etc.HttpGetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by FlaShilver on 2013. 10. 6..
 */
public class CFSThread extends Thread {

    String result_str;
    ArrayList<Cook_facility_search_Model> lists;

    public void run(){
        HttpGetRequest hgr = new HttpGetRequest();
        String result = hgr.getHTML("http://babyhoney.kr/index.php/api/GetNutritionist/?username=jong327&password=ac619ef29c44938cbf0a619f5029ff47&page=0&offset=2&location=서울");

        Log.d("result",""+result);

        try {
            JSONObject response = new JSONObject(result);
            JSONObject channel = response.getJSONObject("channel");
            JSONArray item = channel.getJSONArray("item");

            for(int i=0; i<item.length();i++){
                JSONObject obj = item.getJSONObject(i);
                Cook_facility_search_Model cfsm = new Cook_facility_search_Model();
                cfsm.setCook_name(obj.getString("name"));
                cfsm.setCook_license_num(obj.getString("certiciation_no"));
                cfsm.setCook_address(obj.getString("location"));
                cfsm.setCook_phone_num(obj.getString("telephone"));
                lists.add(cfsm);
            }
        } catch (Exception e) {
            Log.d("jsonexception", e.toString());
        }

    }
}
