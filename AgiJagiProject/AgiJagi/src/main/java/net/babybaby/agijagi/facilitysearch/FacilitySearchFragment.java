package net.babybaby.agijagi.facilitysearch;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import net.babybaby.agijagi.R;
import net.babybaby.agijagi.weekly_meal.WeeklyMealMainActivity;

import java.util.ArrayList;

/**
 * Created by FlaShilver on 2013. 10. 18..
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FacilitySearchFragment extends Fragment {

    ListView listview;
    EditText edittext;
    static ArrayList<FacilityModel> lists;
    FacilitySearchAdapter facilitySearchAdapter;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_facility_list, container, false);

        edittext = (EditText) rootView.findViewById(R.id.facility_txt);

        listview = (ListView) rootView.findViewById(R.id.facility_list);

        lists = new ArrayList<FacilityModel>();

        facilitySearchAdapter = new FacilitySearchAdapter(getActivity(), R.layout.row_facility, lists);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                FacilityModel FacilityModel = lists.get(position);
                Intent intent = new Intent(getActivity(), Cook_facility_search.class);
                intent.putExtra("name", FacilityModel.getName());
                intent.putExtra("id", FacilityModel.getId());
                intent.putExtra("Certification_no", FacilityModel.getCertification_no());
                intent.putExtra("join_date", FacilityModel.getJoin_date());
                startActivity(intent);
                */
                FacilityModel FacilityModel = lists.get(position);
                Intent intent = new Intent(getActivity(), WeeklyMealMainActivity.class);
                intent.putExtra("name", FacilityModel.getName());
                intent.putExtra("id", FacilityModel.getID());
                intent.putExtra("address", FacilityModel.getAddress());
                intent.putExtra("telephone", FacilityModel.getTelephone());
                startActivity(intent);
            }
        });

        Button btn = (Button) rootView.findViewById(R.id.facility_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                facilitySearchAdapter.clear();

                try {
                    FacilityThread.arg = edittext.getText();
                    FacilityThread facilityThread = new FacilityThread();
                    facilityThread.start();
                    facilityThread.join();
                    listview.setAdapter(facilitySearchAdapter);

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
    }

    private class FacilitySearchAdapter extends ArrayAdapter<FacilityModel> {

        public ArrayList<FacilityModel> items;
        private Context mcontext;

        public FacilitySearchAdapter(Context context, int resource, ArrayList<FacilityModel> items) {
            super(context, resource, items);
            mcontext = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_facility, null);
            }

            FacilityModel areaInfo = items.get(position);

            if (areaInfo != null) {
                TextView facility_name = (TextView) v.findViewById(R.id.row_facility_name);
                TextView facility_type = (TextView) v.findViewById(R.id.row_facility_type);
                TextView facility_phone = (TextView) v.findViewById(R.id.row_facility_phone);
                TextView facility_address = (TextView) v.findViewById(R.id.row_facility_address);

                facility_name.setText(areaInfo.getName());
                if(areaInfo.getType()==0){
                    facility_type.setText("공립");
                }else{
                    facility_type.setText("사립");
                }
                facility_phone.setText(areaInfo.getTelephone());
                facility_address.setText(areaInfo.getAddress());
            }

            return v;
        }
    }
}

