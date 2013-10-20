package net.babybaby.agijagi.recommand_meal;

import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import net.babybaby.agijagi.cooksearch.CookModel;
import net.babybaby.agijagi.cooksearch.CookSearchFragment;
import net.babybaby.agijagi.etc.HttpGetRequest;
import net.babybaby.agijagi.login.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by FlaShilver on 2013. 10. 19..
 */
public class RecommandMealThread extends Thread {

    public static String arg;


    public void run() {
        HttpGetRequest hgr = new HttpGetRequest();

        arg = RecommandMealModel.selectid;

        String result = hgr.getHTML("http://babyhoney.kr/api/getNutritionRecommend/?user_id="+ URLEncoder.encode(String.valueOf(arg)));

        Log.d("resresres",""+result);

        try {
            JSONObject response = new JSONObject(result);
            JSONObject channel = response.getJSONObject("channel");
            JSONArray item = channel.getJSONArray("item");

            for (int i = 0; i < item.length(); i++) {
                JSONObject obj = item.getJSONObject(i);
                RecommandMealModel recommandMealModel = new RecommandMealModel();
                recommandMealModel.setDate(obj.getString("date"));
                Log.d("datedate",""+obj.getString("date"));

                JSONArray list = obj.getJSONArray("list");
                for(int j=0;j<list.length();j++){
                    JSONObject list_obj = list.getJSONObject(j);
                }

                RecommandMealListActivity.lists.add(recommandMealModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
