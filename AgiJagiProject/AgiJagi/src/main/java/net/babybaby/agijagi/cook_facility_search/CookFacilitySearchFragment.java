package net.babybaby.agijagi.cook_facility_search;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.babybaby.agijagi.R;
import net.babybaby.agijagi.recipe_detail.ImageLoader;
import net.babybaby.agijagi.recipe_detail.RecipeListModel;

import java.util.ArrayList;

/**
 * Created by FlaShilver on 2013. 10. 6..
 */
public class CookFacilitySearchFragment extends Fragment {

    ListView listview;
    static ArrayList<CookFacilitySearchModel> lists;
    CookFacilitySearchAdapter cfsAdapter;
    ArrayAdapter<CharSequence> adspin;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_cook_facility_list, container, false);


        Spinner spinner = (Spinner) rootView.findViewById(R.id.cook_facility_spinner);
        spinner.setPrompt("aaaaa");

        adspin = ArrayAdapter.createFromResource(getActivity(), R.array.selected, android.R.layout.simple_spinner_item);

        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        listview = (ListView) rootView.findViewById(R.id.cook_facility_list);

        lists = new ArrayList<CookFacilitySearchModel>();

        cfsAdapter = new CookFacilitySearchAdapter(getActivity(), R.layout.row_cook_facility, lists);


        Toast.makeText(getActivity(), "End", Toast.LENGTH_SHORT).show();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        Button btn = (Button) rootView.findViewById(R.id.cook_facility_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    CFSFThread cfsfThread = new CFSFThread();
                    cfsfThread.start();
                    cfsfThread.join();
                    listview.setAdapter(cfsAdapter);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        return rootView;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        return true;
        /**/
    }

    private class CookFacilitySearchAdapter extends ArrayAdapter<CookFacilitySearchModel> {

        public ArrayList<CookFacilitySearchModel> items;
        private Context mcontext;

        public CookFacilitySearchAdapter(Context context, int resource, ArrayList<CookFacilitySearchModel> items) {
            super(context, resource, items);
            mcontext = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_cook_facility, null);
            }

            CookFacilitySearchModel areaInfo = items.get(position);

            if (areaInfo != null) {

                TextView title1 = (TextView) v.findViewById(R.id.row_cook_facility_title);
                TextView title3 = (TextView) v.findViewById(R.id.row_cook_facility_description);

                title1.setText(areaInfo.getName());
                title3.setText(areaInfo.getTelephone());
            }

            return v;
        }
    }


}
