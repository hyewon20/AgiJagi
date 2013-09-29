package net.babybaby.agijagi.httprequest;

import android.content.Intent;
import android.util.Log;

import net.babybaby.agijagi.LoginActivity;
import net.babybaby.agijagi.MainActivity;
import net.babybaby.agijagi.model.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by FlaShilver on 13. 9. 5..
 */
public class ParseThread extends Thread {
    String result_str = null;
    String des_str;
    Runnable testRequestRunnable;
    int login_suc;

    public void run() {


        HttpGetRequest hgr = new HttpGetRequest();
        String url = hgr.getHTML("http://babybaby.azurewebsites.net/index.php/api/loginUser/?username=test&password=god");

        try {
            JSONObject response = new JSONObject(url);
            JSONObject channel = response.getJSONObject("channel");

            result_str = channel.getString("result").toString();
            des_str = channel.getString("description").toString();
            login_suc = Integer.parseInt(result_str);



        } catch (JSONException e) {
            Log.d("jsonexception", e.toString());
        }

        if(login_suc==400){
            Log.d("aa", "aa");

            Login.login_success = true;
            Log.d("bb", ""+Login.login_success);
        }

    }
}