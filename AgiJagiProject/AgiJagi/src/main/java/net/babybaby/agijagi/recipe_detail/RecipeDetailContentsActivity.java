package net.babybaby.agijagi.recipe_detail;

import java.util.ArrayList;

import net.babybaby.agijagi.recipe_detail.Recipe_detail_Activity.EndlessScrollListener;
import net.babybaby.agijagi.recipe_detail.Recipe_detail_Activity.RecipeListAdapter;
import net.babybaby.agijagi.recipe_detail.RecipeListModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import net.babybaby.agijagi.R;


public class RecipeDetailContentsActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);
        
        TextView title = (TextView) findViewById(R.id.recipe_detail_title);
        title.setText(getIntent().getStringExtra("rc_title"));
        
        TextView balance = (TextView) findViewById(R.id.recipe_detail_balance);
        balance.setText("칼로리 : "+getIntent().getStringExtra("rc_calory")+"kcal / 탄수화물 : "+getIntent().getStringExtra("rc_carbohydrate")+"g 단백질 : "+getIntent().getStringExtra("rc_portein")+"g 지방 : "+getIntent().getStringExtra("rc_fat")+"g");
        
        TextView material = (TextView) findViewById(R.id.recipe_detail_material);
        material.setText(getIntent().getStringExtra("rc_material").replace(";", ","));
        
        TextView description = (TextView) findViewById(R.id.recipe_detail_instruction);
        description.setText(getIntent().getStringExtra("rc_description").replace(";", "\n"));
        
        ImageView img = (ImageView) findViewById(R.id.recipe_detial_img);
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        int loader = R.drawable.books;
        String img_path = "http://babyhoney.kr/assets/img/"+getIntent().getStringExtra("rc_img_path");
        imgLoader.DisplayImage(img_path, loader, img);
    }
}
