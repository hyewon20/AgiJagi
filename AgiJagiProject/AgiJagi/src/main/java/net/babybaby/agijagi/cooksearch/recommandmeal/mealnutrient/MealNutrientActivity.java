package net.babybaby.agijagi.cooksearch.recommandmeal.mealnutrient;

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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Toast;

import net.babybaby.agijagi.R;
import net.babybaby.agijagi.etc.HttpGetRequest;
import net.babybaby.agijagi.recipe_detail.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by FlaShilver on 2013. 10. 19..
 */
public class MealNutrientActivity extends Activity {

    ListView listview;
    public ArrayList<MealNutrientModel> lists= new ArrayList<MealNutrientModel>();
    private MealNutrientAdapter mealNutrientAdapter;
    private Handler mHandler = new Handler();
    private boolean loading;
    private boolean end_bool;
    private int currentPage;
    private int previousTotal;
    AllMealNutrientModel allMealNutrientModel = new AllMealNutrientModel();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mealnutrientlist);

        ArrayList<String> ids = new ArrayList<String>();

        Intent intent = getIntent();
        ids = intent.getStringArrayListExtra("id_array");

        Log.d("calcal",""+ids.toString());

        final TextView all_cal = (TextView)findViewById(R.id.all_cal);
        final TextView all_tan = (TextView)findViewById(R.id.all_tan);
        final TextView all_dan = (TextView)findViewById(R.id.all_dan);
        final TextView all_ji = (TextView)findViewById(R.id.all_ji);
        Button button = (Button)findViewById(R.id.button);


        listview = (ListView) findViewById(R.id.list);
        mealNutrientAdapter = new MealNutrientAdapter(MealNutrientActivity.this, R.layout.row_recipe, lists);
        listview.setAdapter(mealNutrientAdapter);

        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        for(int i=0;i<ids.size();i++){
            updateList(ids.get(i));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                all_cal.setText(""+allMealNutrientModel.getAll_cal());
                all_tan.setText("" + allMealNutrientModel.getAll_tan());
                all_dan.setText(""+allMealNutrientModel.getAll_dan());
                all_ji.setText(""+allMealNutrientModel.getAll_ji());

            }
        });

    }

    private void updateList(final String id) {
        final ProgressDialog dialog = ProgressDialog.show(MealNutrientActivity.this, "", "불러오는 중입니다. 잠시 기다려 주세요", true);

        new Thread() {
            public void run() {
                try {
                    HttpGetRequest hgr = new HttpGetRequest();;

                    String result = hgr.getHTML("http://babyhoney.kr/api/GetRecipe/?id=" + URLEncoder.encode(String.valueOf(id)));

                    JSONObject response = new JSONObject(result);
                    JSONObject channel = response.getJSONObject("channel");
                    JSONObject item = channel.getJSONObject("item");
                    MealNutrientModel MealNutrientModel = new MealNutrientModel();
                    MealNutrientModel.set_rc_img_path(item.getString("rc_img_path"));
                    MealNutrientModel.setName(item.getString("rc_name"));
                    MealNutrientModel.setCal(item.getInt("rc_calory"));
                    allMealNutrientModel.setAll_cal(item.getInt("rc_calory"));
                    allMealNutrientModel.setAll_tan(item.getInt("rc_carbohydrate"));
                    allMealNutrientModel.setAll_dan(item.getInt("rc_protein"));
                    allMealNutrientModel.setAll_ji(item.getInt("rc_fat"));

                    lists.add(MealNutrientModel);
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    public void run() {
                        listview.post(new Runnable() {
                            public void run() {
                                mealNutrientAdapter.notifyDataSetChanged();
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
        mealNutrientAdapter.clear();
        lists = new ArrayList<MealNutrientModel>();
        listview = (ListView) findViewById(R.id.list);
        mealNutrientAdapter = new MealNutrientAdapter(MealNutrientActivity.this, R.layout.row_recipe, lists);
        listview.setAdapter(mealNutrientAdapter);

    }
/*
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
    */


    private class MealNutrientAdapter extends ArrayAdapter<MealNutrientModel> {

        public ArrayList<MealNutrientModel> items;
        private Context mcontext;

        public MealNutrientAdapter(Context context, int resource, ArrayList<MealNutrientModel> items) {
            super(context, resource, items);
            mcontext = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_recipe, null);
            }

            MealNutrientModel areaInfo = items.get(position);

            if (areaInfo != null) {

                TextView name = (TextView) v.findViewById(R.id.row_recipe_title);
                TextView cal = (TextView) v.findViewById(R.id.row_recipe_description);
                ImageView img = (ImageView) v.findViewById(R.id.row_recipe_img);

                String mealtext="";

                name.setText(areaInfo.getName());
                cal.setText("총 칼로리 : "+areaInfo.getCal()+"cal");

                ImageLoader imgLoader = new ImageLoader(getApplicationContext());
                int loader = R.drawable.books;
                String img_path = "http://babyhoney.kr/assets/img/" + areaInfo.get_rc_img_path();
                imgLoader.DisplayImage(img_path, loader, img);
            }

            return v;
        }
    }
    
}
