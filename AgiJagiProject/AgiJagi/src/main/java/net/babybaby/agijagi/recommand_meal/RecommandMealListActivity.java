package net.babybaby.agijagi.recommand_meal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
public class RecommandMealListActivity extends Activity {

    ListView listview;
    public static ArrayList<RecommandMealModel> lists;
    private RecommandMealAdapter recommandMealAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recommandmeallist);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        RecommandMealModel.selectid = id;

        lists= new ArrayList<RecommandMealModel>();
        listview = (ListView) findViewById(R.id.recommand_meal_list);
        recommandMealAdapter = new RecommandMealAdapter(RecommandMealListActivity.this, R.layout.row_recommandmeallist, lists);

        try {
            RecommandMealThread recommandMealThread = new RecommandMealThread();

            recommandMealThread.start();
            recommandMealThread.run();
            recommandMealThread.join();
            listview.setAdapter(recommandMealAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_recommandmeallist, null);
            }


            RecommandMealModel areaInfo = items.get(position);


            if (areaInfo != null) {
                TextView recommandhead = (TextView) v.findViewById(R.id.recommand_head);
                TextView meallist = (TextView) v.findViewById(R.id.meallist);
                TextView writedate = (TextView) v.findViewById(R.id.date);
                writedate.setText(areaInfo.getDate());
            }


            return v;
        }
    }
}
