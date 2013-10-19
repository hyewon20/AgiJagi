package net.babybaby.agijagi.cooksearch;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import net.babybaby.agijagi.R;
import net.babybaby.agijagi.recipe_detail.Recipe_detail_Activity;
import net.babybaby.agijagi.recommand_meal.RecommandMealListFragment;
import net.babybaby.agijagi.recommand_meal.RecommandMealThread;
import net.babybaby.agijagi.today_recommand.Today_recommand_Activity;

import java.util.ArrayList;

/**
 * Created by FlaShilver on 2013. 10. 18..
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CookSearchFragment extends Fragment {

    ListView listview;
    EditText edittext;
    static ArrayList<CookModel> lists;
    CookSearchAdapter cookSearchAdapter;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_cook_list, container, false);

        edittext = (EditText) rootView.findViewById(R.id.cook_txt);

        listview = (ListView) rootView.findViewById(R.id.cook_list);

        lists = new ArrayList<CookModel>();

        cookSearchAdapter = new CookSearchAdapter(getActivity(), R.layout.row_cook, lists);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Fragment fragment = new RecommandMealListFragment();

                CookModel cookModel = lists.get(position);

                    RecommandMealThread.arg = cookModel.getId();
                    RecommandMealThread recommandMealThread = new RecommandMealThread();
                    recommandMealThread.start();
                    recommandMealThread.run();
                try {
                    recommandMealThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });

        Button btn = (Button) rootView.findViewById(R.id.cook_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cookSearchAdapter.clear();

                try {
                    CookThread.arg = edittext.getText();
                    CookThread cookThread = new CookThread();
                    cookThread.start();
                    cookThread.join();
                    listview.setAdapter(cookSearchAdapter);

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

    private class CookSearchAdapter extends ArrayAdapter<CookModel> {

        public ArrayList<CookModel> items;
        private Context mcontext;

        public CookSearchAdapter(Context context, int resource, ArrayList<CookModel> items) {
            super(context, resource, items);
            mcontext = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_cook, null);
            }

            CookModel areaInfo = items.get(position);

            if (areaInfo != null) {
                TextView cook_name = (TextView) v.findViewById(R.id.row_cook_name);
                TextView cook_id = (TextView) v.findViewById(R.id.row_cook_id);
                TextView cook_joindate = (TextView) v.findViewById(R.id.row_cook_joindate);
                cook_name.setText(areaInfo.getName());
                cook_id.setText(areaInfo.getId());
                cook_joindate.setText(areaInfo.getJoin_date());

            }

            return v;
        }
    }
}

