package net.babybaby.agijagi;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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

import net.babybaby.agijagi.cook_facility_search.Cook_facility_search;
import net.babybaby.agijagi.logout.LogoutActivity;
import net.babybaby.agijagi.notice.NoticeActivity;
import net.babybaby.agijagi.recipe_detail.Recipe_detail_Activity;
import net.babybaby.agijagi.recipe_search.Recipe_search_Activity;
import net.babybaby.agijagi.recommand_meal.Recommand_meal_Activity;
import net.babybaby.agijagi.today_recommand.Today_recommand_Activity;
import net.babybaby.agijagi.user.UserActivity;
import net.babybaby.agijagi.week_meal.Week_meal_Activity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("getActicity",""+this);

        SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
        usertype = prefs.getInt("type", 2);
        id = prefs.getString("id","null");
        password = prefs.getString("password","null");

        if (usertype == 0) {
            mDrawerTitles = new String[]{"" + prefs.getString("id", "null"), "메인화면", "공지사항", "레시피 조회", "레시피 검색", "영양사/기관 검색", "추천 식단", "로그아웃"};
        } else if (usertype == 1) {
            mDrawerTitles = new String[]{"" + prefs.getString("og_name", "null"), "메인화면", "공지사항", "주간식단 조회", "레시피 검색", "영양사/기관 검색", "추천 식단", "로그아웃"};
        } else if (usertype == 2) {
            mDrawerTitles = new String[]{ "메인화면", "공지사항", "레시피 검색", "영양사/기관 검색", "추천 식단"};
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
            selectItem(1);
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);

        return super.onPrepareOptionsMenu(menu);
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
            Log.d("position", "" + position);
        }
    }

    private void selectItem(int position) {
        //Create a new fragment and specify the planet to show based on position
        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();

        if (usertype == 0) {
            switch (position) {
                case 0:
                    fragment = new UserActivity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
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
                    fragment = new Recipe_search_Activity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 5:
                    fragment = new Cook_facility_search();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 6:
                    fragment = new Recommand_meal_Activity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 7:
                    fragment = new LogoutActivity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                default:
                    break;
            }

        } else if (usertype == 1) {
            switch (position) {
                case 0:
                    fragment = new UserActivity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
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
                    fragment = new Week_meal_Activity(); //주간 식단 검색으로 교체
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 4:
                    fragment = new Recipe_search_Activity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 5:
                    fragment = new Cook_facility_search();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 6:
                    fragment = new Recommand_meal_Activity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 7:
                    fragment = new LogoutActivity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                default:
                    break;
            }
        }
        else if (usertype == 2) {
            switch (position) {
                case 0:
                    fragment = new Today_recommand_Activity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 1:
                    fragment = new NoticeActivity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 2:
                    fragment = new Recipe_search_Activity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 3:
                    fragment = new Cook_facility_search();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 4:
                    fragment = new Recommand_meal_Activity();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                default:
                    break;
            }
        }
        mDrawerList.setItemChecked(position,true);
        //setTitle(mDrawerTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
}
