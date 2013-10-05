package net.babybaby.agijagi.cook_facility_search;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.babybaby.agijagi.MainActivity;
import net.babybaby.agijagi.R;
import net.babybaby.agijagi.etc.HttpGetRequest;
import net.babybaby.agijagi.cook_facility_search.Cook_facility_search;
import net.babybaby.agijagi.cook_facility_search.Cook_facility_search_Model;
import net.babybaby.agijagi.recipe_detail.ImageLoader;
import net.babybaby.agijagi.recipe_detail.RecipeListModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.transform.Source;

public class Cook_facility_search_list extends Fragment {

    ArrayAdapter<CharSequence> adspin;
    Cook_facility_Adapter adapter;
    ArrayList<Cook_facility_search_Model> lists;
    ListView listview;
    View rootView = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.activity_cook_facility_list, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.cook_facility_spinner);
        spinner.setPrompt("시/도를 선택하세요.");

        adspin = ArrayAdapter.createFromResource(getActivity(), R.array.selected, android.R.layout.simple_spinner_item);

        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),
                        adspin.getItem(position) + "을/를 선택 했습니다.", 1).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        EditText edittext = (EditText) rootView.findViewById(R.id.cook_facility_txt);
        Button submit = (Button) rootView.findViewById(R.id.cook_facility_btn);

        listview = (ListView) rootView.findViewById(R.id.activity_cook_facility_list);

        adapter = new Cook_facility_Adapter(getActivity(), R.layout.row_cook_facility, lists);


        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ListView", ""+position);
            }
        });

        listview.setAdapter(adapter);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button","Button Click");
                adapter.add(new Cook_facility_search_Model("adsf","jkl"));
            }
        });


        return rootView;
    }

    private class Cook_facility_Adapter extends ArrayAdapter<Cook_facility_search_Model> {

        public ArrayList<Cook_facility_search_Model> items;
        private Context mcontext;

        public Cook_facility_Adapter(Context context, int textViewResourceId, ArrayList<Cook_facility_search_Model> items) {
            super(context, textViewResourceId, items);
            mcontext = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_recipe, null);
            }

            Cook_facility_search_Model areaInfo = items.get(position);

            if (areaInfo != null) {

                TextView title1 = (TextView) v.findViewById(R.id.row_cook_facility_title);
                TextView title3 = (TextView) v.findViewById(R.id.row_cook_facility_description);

                title1.setText(areaInfo.getCook_name());
                title3.setText(areaInfo.getCook_address());
            }

            return v;
        }
    }

    public class CFSThread extends Thread {

        String result_str;

        public void run() {
            HttpGetRequest hgr = new HttpGetRequest();
            String result = hgr.getHTML("http://babyhoney.kr/index.php/api/getNutritionist/?username=flashilver&password=ac619ef29c44938cbf0a619f5029ff47&page=0&offset=2&location=서울");
            try {
                JSONObject response = new JSONObject(result);
                JSONObject channel = response.getJSONObject("channel");
                JSONArray item = channel.getJSONArray("item");

                for (int i = 0; i < item.length(); i++) {
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
}
