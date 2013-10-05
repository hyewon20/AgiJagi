package net.babybaby.agijagi.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.babybaby.agijagi.MainActivity;
import net.babybaby.agijagi.R;
import net.babybaby.agijagi.etc.MD5;

/**
 * Created by FlaShilver on 2013. 9. 27..
 */
public class LoginActivity extends Activity {

    EditText id_edittext;
    EditText pw_edittext;

    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_button = (Button) findViewById(R.id.login_button);
        id_edittext = (EditText) findViewById(R.id.login_email);
        pw_edittext = (EditText) findViewById(R.id.login_password);

        login_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    Login.id = id_edittext.getText().toString();
                    Login.password = pw_edittext.getText().toString();
                    Login.password = MD5.getMD5Hash(Login.password);
                    Log.d("MD5",""+Login.id+","+Login.password);

                    LoginThread worker = new LoginThread();
                    worker.start();

                    worker.join();

                }catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Toast.makeText(LoginActivity.this,""+Login.login_success,Toast.LENGTH_SHORT).show();
                if(Login.login_success==true)
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Invaild login",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}