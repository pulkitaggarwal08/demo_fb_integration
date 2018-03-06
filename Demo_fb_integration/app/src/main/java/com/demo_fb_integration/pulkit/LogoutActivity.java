package com.demo_fb_integration.pulkit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LogoutActivity extends AppCompatActivity {

    private TextView btnLogout;
    private ImageView profileImage;
    Bitmap bitmap;
    private String getfacebookData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        profileImage = (ImageView) findViewById(R.id.profileImage);
        btnLogout = (TextView) findViewById(R.id.btn_logout);

        getfacebookData = getIntent().getStringExtra("facebook_id");

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {
                URL imageURL = null;
                try {

                    imageURL = new URL("https://graph.facebook.com/" + getfacebookData + "/picture?type=large");

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                profileImage.setImageBitmap(bitmap);

//                Picasso.with(getApplicationContext())
//                        .load("https://graph.facebook.com/" + getfacebookData+ "/picture?type=large")
//                        .into(profileImage);

            }

        }.execute();


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginManager.getInstance().logOut();
                Intent i = new Intent(LogoutActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
