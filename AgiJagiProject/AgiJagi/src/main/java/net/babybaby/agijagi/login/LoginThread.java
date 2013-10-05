package net.babybaby.agijagi.login;

import android.util.Log;
import net.babybaby.agijagi.etc.HttpGetRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by FlaShilver on 13. 9. 5..
 */
public class LoginThread extends Thread {
    String result_str;
    String des_str;
    int login_suc;
    String id;
    int type;
    int og_id;
    String og_name;

    public void run() {
        HttpGetRequest hgr = new HttpGetRequest();
        String url = hgr.getHTML("http://babyhoney.kr/index.php/api/loginUser/?username="+Login.id+"&password="+Login.password);

        try {
            JSONObject response = new JSONObject(url);
            JSONObject channel = response.getJSONObject("channel");
            result_str = channel.getString("result").toString();
            des_str = channel.getString("description").toString();
            JSONObject item = channel.getJSONObject("item");
            id = item.getString("id").toString();
            Login.get_id = id;
            type = item.getInt("type");
            Login.get_type = type;
            if(type==1){
                og_id = item.getInt("og_id");
                Login.og_id = og_id;
                og_name = item.getString("og_name");
                Login.og_name = og_name;
            }
            login_suc = Integer.parseInt(result_str);

        } catch (JSONException e) {
            Log.d("jsonexception", e.toString());
        }

        if(login_suc==400){
            Log.d("login_suc", ""+login_suc);
            Login.login_success = true;
            Log.d("login_suc2", ""+login_suc+","+id+","+type+","+og_id+","+og_name+"");
        }
        else if(login_suc==200){
            Log.d("login_suc", ""+login_suc);
            Login.login_success = false;
        }

    }
}