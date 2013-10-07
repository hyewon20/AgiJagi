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
import android.widget.TextView;

import net.babybaby.agijagi.R;


public class Cook_facility_search extends Activity {
    String name;
    String certiciation_no;
    String location;
    String telephone;
    String description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_facility_detail);

        TextView first = (TextView) findViewById(R.id.first_contents);
        TextView second = (TextView) findViewById(R.id.second_contents);
        TextView thrid = (TextView) findViewById(R.id.third_contents);
        TextView fourth = (TextView) findViewById(R.id.fourth_contents);


        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        certiciation_no = intent.getStringExtra("Certification_no");
        location = intent.getStringExtra("location");
        telephone = intent.getStringExtra("telephone");
        description = intent.getStringExtra("description");

        if(CFSFThread.IsCook = true){
            first.setText(name);
            second.setText(certiciation_no);
            thrid.setText(location);
            fourth.setText(telephone);
        }
        else if(CFSFThread.IsCook = false){
            first.setText(name);
            second.setText(description);
            thrid.setText(location);
            fourth.setText(telephone);
        }


    }
}