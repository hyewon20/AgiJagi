package net.babybaby.agijagi.recommand_meal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.babybaby.agijagi.R;

import java.util.ArrayList;

/**
 * Created by FlaShilver on 2013. 10. 19..
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RecommandMealListFragment extends Fragment {

    ListView listview;
    public static ArrayList<RecommandMealModel> lists = null;
    private RecommandMealAdapter recommandMealAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recommandmeallist, container, false);

        Log.d("datadata",""+lists.get(0).getDate());

        listview = (ListView) rootView.findViewById(R.id.recommand_meal_list);

        lists = new ArrayList<RecommandMealModel>();

        recommandMealAdapter = new RecommandMealAdapter(getActivity(), R.layout.row_recommandmeallist, lists);

        recommandMealAdapter.clear();

        listview.setAdapter(recommandMealAdapter);


        return rootView;
    }

    private class RecommandMealAdapter extends ArrayAdapter<RecommandMealModel> {

        public ArrayList<RecommandMealModel> items;
        private Context mcontext;

        public RecommandMealAdapter(Context context, int resource, ArrayList<RecommandMealModel> items) {
            super(context, resource, items);
            mcontext = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_recommandmeallist, null);
            }



            RecommandMealModel areaInfo = items.get(position);


            if (areaInfo != null) {
                TextView recommandhead = (TextView) v.findViewById(R.id.recommand_head);
                TextView meallist = (TextView) v.findViewById(R.id.meallist);
                TextView writedate = (TextView) v.findViewById(R.id.date);

                meallist.setText((CharSequence) areaInfo.getIdnNames().toString());
                writedate.setText(areaInfo.getDate());
            }



            return v;
        }
    }
}
