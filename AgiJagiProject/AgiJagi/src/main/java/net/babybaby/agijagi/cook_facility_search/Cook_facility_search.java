package net.babybaby.agijagi.cook_facility_search;


/**
 * Created by FlaShilver on 2013. 10. 4..
 */

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import net.babybaby.agijagi.MainActivity;
import net.babybaby.agijagi.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;


public class Cook_facility_search extends Fragment {

    ArrayAdapter<CharSequence> adspin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_cook_facility_search, container, false);

        Log.d("getActicity",""+getActivity().toString());

        Spinner spinner = (Spinner) rootView.findViewById(R.id.search_spinner);
        spinner.setPrompt("시/도를 선택하세요.");

        adspin = ArrayAdapter.createFromResource(getActivity(), R.array.selected,    android.R.layout.simple_spinner_item);

        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?>  parent, View view, int position, long id) {
                Toast.makeText(getActivity(),
                        adspin.getItem(position) + "을/를 선택 했습니다.", 1).show();
            }
            public void onNothingSelected(AdapterView<?>  parent) {
            }
        });

        try
        {
            HttpClient client = new DefaultHttpClient();
            String postURL = "http://babyhoney.kr/index.php/api/getNutritionist/?username=username="+ MainActivity.id+"&password="+MainActivity.password;
            HttpPost post = new HttpPost(postURL);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user", "kris"));
            params.add(new BasicNameValuePair("pass", "xyz"));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);
            HttpEntity resEntity = responsePOST.getEntity();

            if (resEntity != null)
            {
                Log.i("RESPONSE", EntityUtils.toString(resEntity));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return rootView;
    }
}