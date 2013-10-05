package net.babybaby.agijagi.borad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import net.babybaby.agijagi.R;

/**
 * Created by FlaShilver on 2013. 10. 5..
 */
public class BoardActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borad);

        TextView notice_title = (TextView) findViewById(R.id.notice_title);
        TextView notice_date = (TextView) findViewById(R.id.notice_date);
        TextView notice_description = (TextView) findViewById(R.id.notice_description);
        Intent intent = getIntent();

        notice_title.setText(intent.getStringExtra("title"));
        notice_date.setText(intent.getStringExtra("date"));
        notice_description.setText(intent.getStringExtra("description"));
    }


}
