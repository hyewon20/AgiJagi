package net.babybaby.agijagi.today_recommand;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.babybaby.agijagi.R;
import net.babybaby.agijagi.cooksearch.recommandmeal.mealnutrient.ImageLoader;
import net.babybaby.agijagi.cooksearch.recommandmeal.mealnutrient.MealNutrientModel;
import net.babybaby.agijagi.etc.HttpGetRequest;
import net.babybaby.agijagi.recipe_detail.RecipeDetailContentsActivity;
import net.babybaby.agijagi.recipe_detail.RecipeListModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by FlaShilver on 2013. 10. 4..
 */
public class Today_recommand_Activity extends Fragment {

    public static ArrayList<TodayRecommandModel> al = new ArrayList<TodayRecommandModel>();
    public ArrayList<RecipeListModel> rlm = new ArrayList<RecipeListModel>();
    private Handler mHandler = new Handler();
    private boolean loading;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;

    TextView textview1;
    TextView textview2;
    TextView textview3;

    Intent intent = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_today_recommand, container, false);


        imageView1 = (ImageView) rootView.findViewById(R.id.imageview1);
        imageView2 = (ImageView) rootView.findViewById(R.id.imageview2);
        imageView3 = (ImageView) rootView.findViewById(R.id.imageview3);

        textview1 = (TextView) rootView.findViewById(R.id.textview1);
        textview2 = (TextView) rootView.findViewById(R.id.textview2);
        textview3 = (TextView) rootView.findViewById(R.id.textview3);

        updateList(al);

        textview1.setText(al.get(0).getName().toString());
        textview2.setText(al.get(1).getName().toString());
        textview3.setText(al.get(2).getName().toString());

        ImageLoader imgLoader = new ImageLoader(getActivity());
        int loader = R.drawable.books;
        String img_path = "http://babyhoney.kr/assets/img/" + al.get(0).getImg().toString();
        imgLoader.DisplayImage(img_path, loader, imageView1);

        img_path = "http://babyhoney.kr/assets/img/" + al.get(1).getImg().toString();
        imgLoader.DisplayImage(img_path, loader, imageView2);

        img_path = "http://babyhoney.kr/assets/img/" + al.get(2).getImg().toString();
        imgLoader.DisplayImage(img_path, loader, imageView3);

        intent = new Intent(getActivity(), RecipeDetailContentsActivity.class);


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(setPutExtra(intent, 0));
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(setPutExtra(intent, 1));
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(setPutExtra(intent, 2));
            }
        });

        return rootView;
    }

    private void updateList(final ArrayList<TodayRecommandModel> al) {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "불러오는 중입니다. 잠시 기다려 주세요", true);


        new Thread() {
            public void run() {
                for (int i = 0; i < 3; i++) {
                    try {
                        HttpGetRequest hgr = new HttpGetRequest();
                        String dest = "http://babyhoney.kr/api/getRecipe/?id=" + al.get(i).getId();

                        String url = hgr.getHTML(dest);

                        JSONObject response = new JSONObject(url);
                        JSONObject channel = response.getJSONObject("channel");
                        JSONObject data = channel.getJSONObject("item");

                        RecipeListModel aInfo = new RecipeListModel();

                        aInfo.SetRcCalory(data.getString("rc_calory"));
                        aInfo.SetRcCategory(data.getString("rc_category"));
                        aInfo.SetRcId(data.getString("rc_id"));
                        aInfo.SetRcImgPath(data.getString("rc_img_path"));
                        aInfo.SetRcName(data.getString("rc_name"));
                        aInfo.SetRcCarbohydrate(data.getString("rc_carbohydrate"));
                        aInfo.SetRcDescription(data.getString("rc_description"));
                        aInfo.SetRcFat(data.getString("rc_fat"));
                        aInfo.SetRcPortein(data.getString("rc_protein"));
                        aInfo.SetRcMaterial(data.getString("rc_material"));
                        rlm.add(aInfo);


                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mHandler.post(new Runnable() {
                    public void run() {
                        dialog.hide();

                    }
                });
            }
        }.start();
    }

    private Intent setPutExtra(Intent intent, int num) {

        intent.putExtra("rc_title", rlm.get(num).GetRcName());
        intent.putExtra("rc_calory", rlm.get(num).GetRcCalory());
        intent.putExtra("rc_category", rlm.get(num).GetRcCategory());
        intent.putExtra("rc_id", rlm.get(num).GetRcId());
        intent.putExtra("rc_img_path", rlm.get(num).GetRcImgPath());
        intent.putExtra("rc_name", rlm.get(num).GetRcName());
        intent.putExtra("rc_carbohydrate", rlm.get(num).GetRcCarbohydrate());
        intent.putExtra("rc_description", rlm.get(num).GetRcDescription());
        intent.putExtra("rc_fat", rlm.get(num).GetRcFat());
        intent.putExtra("rc_portein", rlm.get(num).GetRcPortein());
        intent.putExtra("rc_material", rlm.get(num).GetRcMaterial());

        return intent;

    }


}
