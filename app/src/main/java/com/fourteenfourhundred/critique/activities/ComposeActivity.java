package com.fourteenfourhundred.critique.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.volley.Response;
import com.fourteenfourhundred.critique.API.API;
import com.fourteenfourhundred.critique.API.ApiRequest;
import com.fourteenfourhundred.critique.activities.SelectMutualsActivity;
import com.fourteenfourhundred.critique.critique.R;
import com.fourteenfourhundred.critique.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

public class ComposeActivity extends AppCompatActivity {

    String title;
    String content;
    //rename to activity
    public API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        api=new API(this);
        /*
        SearchView s = findViewById(R.id.postTitle);
        s.setQueryHint("Post title");*/

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_TITLE);

        getSupportActionBar().setCustomView(R.layout.compose_menu);

        getSupportActionBar().setDisplayShowTitleEnabled(false);



        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText e = findViewById(R.id.postTitle);
        e.requestFocus();
    }

    protected void onResume(){
        super.onResume();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK){

            try {
                new ApiRequest.SendPostRequest(api,new JSONArray(data.getStringExtra("selected")), "text", title, content).execute( new Util.Callback() {
                    public void onResponse(JSONObject response) {
                        try {

                            String status = response.getString("status");
                            if (status.equals("error")) {
                                String msg = response.getString("message");
                                Util.showDialog(ComposeActivity.this, msg);
                            } else {
                                //setResult(2);
                                finish();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        //Util.showDialog(this,"here?");

        finish();

        return false;
    }

    public void createPost(){

        Intent intent = new Intent(getApplicationContext(), SelectMutualsActivity.class);
        //intent.putExtra("title", title);
        //intent.putExtra("content", content);
        content= ((EditText)findViewById(R.id.postContent)).getText().toString();
        title= ((EditText)findViewById(R.id.postTitle)).getText().toString();
        startActivityForResult(intent,0);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sendButton:
                createPost();
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.vote_menu,menu);

       // android.app.ActionBar actionBar = getActionBar();
        //actionBar.setCustomView(R.layout.compose_menu);



        return super.onCreateOptionsMenu(menu);
    }

}
