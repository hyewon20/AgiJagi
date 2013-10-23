package net.babybaby.agijagi.weekly_meal;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import net.babybaby.agijagi.R;
import net.babybaby.agijagi.etc.HttpGetRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Namyun
 * @since 13. 10. 19.
 */
public class WeeklyMealMainActivity extends FragmentActivity {

//  private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    Handler mHandler = new Handler();
    private ArrayList<ArrayList<MealData>> weeklyMeal = new ArrayList<ArrayList<MealData>>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_main);

        Intent intent = getIntent();
        final int id=intent.getExtras().getInt("id");
        String name=intent.getExtras().getString("name");
        String address=intent.getExtras().getString("address");
        String telephone=intent.getExtras().getString("telephone");

        for(int i=0; i<6; i++)
        {
            weeklyMeal.add(new ArrayList<MealData>());
        }

        final ProgressDialog dialog = ProgressDialog.show(this, "", "정보를 읽어오는 중입니다. 잠시만 기다려주세요.", true);
        new Thread() {
            public void run() {
                try {
                    HttpGetRequest hgr = new HttpGetRequest();
                    String dest = "http://babyhoney.kr/api/getMenu/?org_id="+id;
                    String url = hgr.getHTML(dest);

                    Log.d("url",""+url);

                    JSONObject response = new JSONObject(url);
                    JSONObject channel = response.getJSONObject("channel");
                    JSONArray data = channel.getJSONArray("item");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);
                        String date = obj.getString("date");
                        int part = obj.getInt("part");
                        JSONArray list = obj.getJSONArray("list");

                        String result_name = "";
                        MealData meal=new MealData();
                        meal.setDate(date);
                        meal.setPart(part);

                        for (int j = 0; j < list.length(); j++) {
                            JSONObject obj2 = list.getJSONObject(j);
                            String name = obj2.getString("name");

                            meal.addMeal(name);

                            result_name += name;
                            if (j + 1 != list.length())
                                result_name += "\n";
                        }

                        Calendar cal = Calendar.getInstance();
                        int year = Integer.parseInt(date.substring(0, 4));
                        int month = Integer.parseInt(date.substring(5, 7));
                        int dt = Integer.parseInt(date.substring(8, 10));
                        cal.set(year, month, dt);

                        int day_sw = 0;
                        if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
                            day_sw = 2;
                            meal.setDay("수요일");
                        }else if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                            day_sw = 3;
                            meal.setDay("목요일");
                        }else if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
                            day_sw = 4;
                            meal.setDay("금요일");
                        }else if (cal.get(Calendar.DAY_OF_WEEK) == 3) {
                            day_sw = 5;
                            meal.setDay("토요일");
                        }else if (cal.get(Calendar.DAY_OF_WEEK) == 5) {
                            day_sw = 0;
                            meal.setDay("월요일");
                        }else if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
                            day_sw = 1;
                            meal.setDay("화요일");
                        }
                        weeklyMeal.get(day_sw).add(meal);
                    }
                    mHandler.post(new Runnable() {
                        public void run() {

                            dialog.hide();
                            ActionBar actionBar=getActionBar();
                            for(ArrayList<MealData> element : weeklyMeal)
                            {
                                if(element.size()!=0)
                                {
                                    ActionBar.Tab newTab=actionBar.newTab().setText(element.get(0).getDay());
                                    WeeklyMealListFragment fragment=new WeeklyMealListFragment(element);
                                    newTab.setTabListener(new MyTabsListener(fragment));
                                    actionBar.addTab(newTab);
                                }
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        ActionBar actionBar=getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1=actionBar.newTab().setText("기관정보");
        WeeklyMealInfoFragment infoFragment=new WeeklyMealInfoFragment(id,name,address,telephone);
        tab1.setTabListener(new MyTabsListener(infoFragment));
        actionBar.addTab(tab1);

    }

    class MyTabsListener implements ActionBar.TabListener {
        public Fragment fragment;

        public MyTabsListener(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            //do what you want when tab is reselected, I do nothing
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.replace(R.id.fragment_placeholder, fragment);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.remove(fragment);
        }
    }
}