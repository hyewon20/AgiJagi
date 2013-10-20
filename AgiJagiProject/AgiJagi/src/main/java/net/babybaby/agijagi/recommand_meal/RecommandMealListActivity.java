package net.babybaby.agijagi.recommand_meal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;


import net.babybaby.agijagi.R;
import net.babybaby.agijagi.etc.HttpGetRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by FlaShilver on 2013. 10. 19..
 */
public class RecommandMealListActivity extends Activity {

    ListView listview;
    public ArrayList<RecommandMealModel> lists= new ArrayList<RecommandMealModel>();
    private RecommandMealAdapter recommandMealAdapter;
    private boolean loading;
    private boolean end_bool;
    private int currentPage;
    private int previousTotal;
    private Handler mHandler = new Handler();
    private String arg;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recommandmeallist);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        RecommandMealModel.selectid = id;

        listview = (ListView) findViewById(R.id.recommand_meal_list);
        recommandMealAdapter = new RecommandMealAdapter(RecommandMealListActivity.this, R.layout.row_recommandmeallist, lists);
        listview.setAdapter(recommandMealAdapter);

        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        updateList();
    }

    private void updateList() {
        final ProgressDialog dialog = ProgressDialog.show(RecommandMealListActivity.this, "", "불러오는 중입니다. 잠시 기다려 주세요", true);

        new Thread() {
            public void run() {
                try {
                    HttpGetRequest hgr = new HttpGetRequest();

                    arg = RecommandMealModel.selectid;

                    String result = hgr.getHTML("http://babyhoney.kr/api/getNutritionRecommend/?user_id=" + URLEncoder.encode(String.valueOf(arg)));

                    JSONObject response = new JSONObject(result);
                    JSONObject channel = response.getJSONObject("channel");
                    JSONArray item = channel.getJSONArray("item");

                    for (int i = 0; i < item.length(); i++) {
                        JSONObject obj = item.getJSONObject(i);
                        RecommandMealModel recommandMealModel = new RecommandMealModel();
                        recommandMealModel.setDate(obj.getString("date"));
                        recommandMealModel.setRecommandHeader("추천"+(i+1));

                        JSONArray list = obj.getJSONArray("list");
                        for (int j = 0; j < list.length(); j++) {
                            JSONObject list_obj = list.getJSONObject(j);
                            recommandMealModel.setIdnNames(list_obj.getString("id"), list_obj.getString("name"));

                        }
                        lists.add(recommandMealModel);

                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    public void run() {
                        listview.post(new Runnable() {
                            public void run() {
                                recommandMealAdapter.notifyDataSetChanged();
                                loading = true;
                            }
                        });

                        dialog.hide();

                    }
                });

            }
        }.start();
    }

    private void clearupdate() {
        loading = true;
        end_bool = false;
        currentPage = 1;
        previousTotal = 0;
        recommandMealAdapter.clear();
        lists = new ArrayList<RecommandMealModel>();
        listview = (ListView) findViewById(R.id.recommand_meal_list);
        recommandMealAdapter = new RecommandMealAdapter(RecommandMealListActivity.this, R.layout.row_recommandmeallist, lists);
        listview.setAdapter(recommandMealAdapter);

    }

    class EndlessScrollListener implements OnScrollListener {

        public EndlessScrollListener() {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (loading && !end_bool) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                    currentPage++;
                }
            }
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if (!loading && lastInScreen == totalItemCount && !end_bool) {
                updateList();
                loading = true;
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
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

            Log.d("getviewcall","call");

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_recommandmeallist, null);
            }

            RecommandMealModel areaInfo = items.get(position);

            if (areaInfo != null) {
                TextView recommandhead = (TextView) v.findViewById(R.id.recommand_head);
                TextView meallist = (TextView) v.findViewById(R.id.meallist);
                TextView writedate = (TextView) v.findViewById(R.id.date);

                String mealtext="";

                for(int i=0;i<areaInfo.getIdnNames().size();i++){
                    mealtext += areaInfo.getIdnNames().get(i).name +",";
                }
                meallist.setText(mealtext);
                recommandhead.setText(areaInfo.getRecommandHeader());
                writedate.setText(areaInfo.getDate());
            }

            return v;
        }
    }
}
