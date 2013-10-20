package net.babybaby.agijagi;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.babybaby.agijagi.cooksearch.CookSearchFragment;
import net.babybaby.agijagi.facilitysearch.FacilitySearchFragment;
import net.babybaby.agijagi.notice.NoticeActivity;
import net.babybaby.agijagi.recipe_detail.Recipe_detail_Activity;
import net.babybaby.agijagi.today_recommand.Today_recommand_Activity;
import net.babybaby.agijagi.weekly_meal.Weekly_Meal_Activity;

public class MainActivity extends Activity {

    private String[] mDrawerTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;

    public static int usertype;
    public static String id;
    public static String password;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();

        fragment = new Today_recommand_Activity();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
        usertype = prefs.getInt("type", 2);
        id = prefs.getString("id","null");
        password = prefs.getString("password","null");

        if (usertype == 0) {
            mDrawerTitles = new String[]{"" + prefs.getString("id", "null")+"님 반갑습니다.", "메인화면", "공지사항", "레시피 검색", "영양사 검색","기관 검색", "로그아웃"};
        } else if (usertype == 1) {
            mDrawerTitles = new String[]{"" + prefs.getString("og_name", "null")+"님 반갑습니다.", "메인화면", "공지사항", "주간식단 조회", "레시피 검색", "영양사 검색","기관 검색", "로그아웃"};
        } else if (usertype == 2) {
            mDrawerTitles = new String[]{"비회원님 환영합니다.", "메인화면", "공지사항", "레시피 검색", "영양사 검색","기관 검색","로그아웃"};
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, mDrawerTitles);

        mTitle = mDrawerTitle = getTitle();
        //mDrawerTitles = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setAdapter(arrayAdapter);
        //mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mDrawerTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }

        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            //selectItem(1);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void selectItem(int position) {
        //Create a new fragment and specify the planet to show based on position
        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();

        if (usertype == 0) {
            switch (position) {
                case 0:
                    break;
                case 1:
                    fragment = new Today_recommand_Activity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 2:
                    fragment = new NoticeActivity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 3:
                    fragment = new Recipe_detail_Activity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;


                case 4:
                    fragment = new CookSearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 5:
                    fragment = new FacilitySearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 6:
                    finish();
                    break;

                default:
                    break;
            }

        } else if (usertype == 1) {
            switch (position) {
                case 0:
                    break;
                case 1:
                    fragment = new Today_recommand_Activity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 2:
                    fragment = new NoticeActivity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 3:
                    fragment = new Weekly_Meal_Activity(); //주간 식단 검색으로 교체
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 4:
                    fragment = new Recipe_detail_Activity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 5:
                    fragment = new CookSearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 6:
                    fragment = new FacilitySearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 7:
                    finish();
                    break;

                default:
                    break;
            }
        }
        else if (usertype == 2) {
            switch (position) {
                case 0:
                    break;
                case 1:
                    fragment = new Today_recommand_Activity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 2:
                    fragment = new NoticeActivity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 3:
                    fragment = new Recipe_detail_Activity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 4:
                    fragment = new CookSearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 5:
                    fragment = new FacilitySearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 6:
                    finish();
                    break;


                default:
                    break;
            }
        }
        mDrawerList.setItemChecked(position,true);
        //setTitle(mDrawerTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
}
