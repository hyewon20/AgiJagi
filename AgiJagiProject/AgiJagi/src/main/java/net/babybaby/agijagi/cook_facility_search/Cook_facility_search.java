package net.babybaby.agijagi.cook_facility_search;


/**
 * Created by FlaShilver on 2013. 10. 4..
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import net.babybaby.agijagi.R;


public class Cook_facility_search extends Activity {
    String name;
    String certiciation_no;
    String location;
    String telephone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_facility_detail);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        certiciation_no = intent.getStringExtra("certiciation_no");
        location = intent.getStringExtra("location");
        telephone = intent.getStringExtra("telephone");

        Log.d("aaa",""+name+","+certiciation_no+","+location+","+telephone);

    }
}