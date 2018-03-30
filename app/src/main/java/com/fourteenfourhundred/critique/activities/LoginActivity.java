package com.fourteenfourhundred.critique.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fourteenfourhundred.critique.storage.Data;
import com.fourteenfourhundred.critique.util.Util;
import com.fourteenfourhundred.critique.activities.HomeScreen.HomeActivity;
import com.fourteenfourhundred.critique.critique.R;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    public void login(String username,String password){

        JSONObject loginInfo = Util.makeJson(
                new Object[]{"username",username},
                new Object[]{"password",password}
        );




        Util.postRequest(LoginActivity.this, Data.url+"login", loginInfo,new Response.Listener<JSONObject>(){
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("status").equals("error")) {
                                Util.showDialog(LoginActivity.this, response.getString("message"));
                            }else{
                                startApp(response.getString("apiKey"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Util.showDialog(LoginActivity.this, "error");
                    }
                }
        );
    }

    public void startApp(String apiKey){

        Data.apiKey=apiKey;

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);


        debug();




        final Button button = findViewById(R.id.loginButton);
        final EditText usernameBox = findViewById(R.id.usernameBox);
        final EditText passwordBox = findViewById(R.id.passwordBox);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                login(usernameBox.getText().toString(),passwordBox.getText().toString());


            }
        });


    }


    public void debug(){

        login("marc","nohash");

        /*
        Util.postRequest(LoginActivity.this,Data.url+"reset", new JSONObject(),new Response.Listener<JSONObject>(){
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Util.showDialog(LoginActivity.this, "errorccc");
                    }
                }
        );*/

    }

}