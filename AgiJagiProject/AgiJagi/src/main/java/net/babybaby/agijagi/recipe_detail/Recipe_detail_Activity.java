package net.babybaby.agijagi.recipe_detail;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.transform.Source;

import org.json.JSONArray;
import org.json.JSONObject;

import net.babybaby.agijagi.etc.HttpGetRequest;
import net.babybaby.agijagi.recipe_detail.RecipeListModel;

import net.babybaby.agijagi.R;


import android.app.Fragment;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

public class Recipe_detail_Activity extends Fragment {
    private boolean loading = true;

    private Boolean end_bool = false;
    private int currentPage = 1;
    private int previousTotal = 0;
    private ArrayList<RecipeListModel> lists;
    private Handler mHandler = new Handler();
    private RecipeListAdapter b1_adapter;
    private Source source;
    private ListView listView;
    View rootView = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.recipe_list, container, false);
        currentPage = 1;

        lists = new ArrayList<RecipeListModel>();
        listView = (ListView) rootView.findViewById(R.id.recipe_list);
     //   listView.setOnScrollListener(new EndlessScrollListener());
        b1_adapter = new RecipeListAdapter(getActivity(), R.layout.row_recipe, lists);
        listView.setAdapter(b1_adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecipeListModel aInfo = lists.get(position);
                Intent intent = new Intent(getActivity(), RecipeDetailContentsActivity.class);
                intent.putExtra("rc_title", aInfo.GetRcName());
                intent.putExtra("rc_calory", aInfo.GetRcCalory());
                intent.putExtra("rc_category", aInfo.GetRcCategory());
                intent.putExtra("rc_id", aInfo.GetRcId());
                intent.putExtra("rc_img_path", aInfo.GetRcImgPath());
                intent.putExtra("rc_name", aInfo.GetRcName());
                intent.putExtra("rc_carbohydrate", aInfo.GetRcCarbohydrate());
                intent.putExtra("rc_description", aInfo.GetRcDescription());
                intent.putExtra("rc_fat", aInfo.GetRcFat());
                intent.putExtra("rc_portein", aInfo.GetRcPortein());
                intent.putExtra("rc_material", aInfo.GetRcMaterial());
                startActivity(intent);
            }
        });

        Button btn = (Button) rootView.findViewById(R.id.recipe_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearupdate();
                updateList();
            }
        });
        updateList();

        return rootView;
    }

    private void clearupdate() {
        loading = true;
        end_bool = false;
        currentPage = 1;
        previousTotal = 0;
        b1_adapter.clear();
        lists = new ArrayList<RecipeListModel>();
        listView = (ListView) rootView.findViewById(R.id.recipe_list);
        b1_adapter = new RecipeListAdapter(getActivity(), R.layout.row_recipe, lists);
        listView.setAdapter(b1_adapter);
    }

    private void updateList() {
        final EditText s_txt = (EditText) rootView.findViewById(R.id.recipe_list_search_txt);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "불러오는 중입니다. 잠시 기다려 주세요", true);
        new Thread() {
            public void run() {
                try {
                    String q = s_txt.getText().toString();
                    q = URLEncoder.encode(q);

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(s_txt.getWindowToken(), 0);

                    HttpGetRequest hgr = new HttpGetRequest();
                    String dest = "http://winspec.sshel.com/api/listRecipe?page=" + currentPage;

                    if (!q.equals("")) {
                        dest += "&q=" + q;
                    }
                    String url = hgr.getHTML(dest);

                    JSONObject response = new JSONObject(url);
                    JSONObject channel = response.getJSONObject("channel");
                    JSONArray data = channel.getJSONArray("item");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);
                        RecipeListModel nModel = new RecipeListModel();
                        nModel.SetRcCalory(obj.getString("rc_calory"));
                        nModel.SetRcCategory(obj.getString("rc_category"));
                        nModel.SetRcId(obj.getString("rc_id"));
                        nModel.SetRcImgPath(obj.getString("rc_img_path"));
                        nModel.SetRcName(obj.getString("rc_name"));
                        nModel.SetRcCarbohydrate(obj.getString("rc_carbohydrate"));
                        nModel.SetRcDescription(obj.getString("rc_description"));
                        nModel.SetRcFat(obj.getString("rc_fat"));
                        nModel.SetRcPortein(obj.getString("rc_protein"));
                        nModel.SetRcMaterial(obj.getString("rc_material"));
                        lists.add(nModel);
                    }
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    public void run() {
                        listView.post(new Runnable() {
                            public void run() {
                                b1_adapter.notifyDataSetChanged();
                                loading = true;
                            }
                        });

                        dialog.hide();

                    }
                });

            }
        }.start();
    }

    private void setListView() {

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        return true;
        /**/
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

    public class RecipeListAdapter extends ArrayAdapter<RecipeListModel> {

        public ArrayList<RecipeListModel> items;
        private Context mcontext;

        public RecipeListAdapter(Context context, int textViewResourceId, ArrayList<RecipeListModel> items) {
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

            RecipeListModel areaInfo = items.get(position);

            if (areaInfo != null) {

                TextView title1 = (TextView) v.findViewById(R.id.row_recipe_title);
                ImageView img = (ImageView) v.findViewById(R.id.row_recipe_img);
                img.setScaleType(ScaleType.CENTER_CROP);
                TextView title3 = (TextView) v.findViewById(R.id.row_recipe_description);

                title1.setText(areaInfo.GetRcName());
                title3.setText(areaInfo.GetRcCategory() + " / " + areaInfo.GetRcCalory() + "kcal");

                ImageLoader imgLoader = new ImageLoader(getActivity());
                int loader = R.drawable.books;
                String img_path = "http://babyhoney.kr/assets/img/" + areaInfo.GetRcImgPath();
                imgLoader.DisplayImage(img_path, loader, img);
            }

            return v;
        }

    }
}
