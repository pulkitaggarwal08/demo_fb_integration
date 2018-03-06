package com.demo_fb_integration.pulkit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private Button btnFacebookLogin;
    CallbackManager callbackManager;
    String facebook_id, first_name, last_name, name, email, gender, birthday, picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        btnFacebookLogin = (Button) findViewById(R.id.btn_login);
        callbackManager = CallbackManager.Factory.create();

        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getFacebookData(loginResult);
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                        Arrays.asList("public_profile", "email", "user_birthday"));
            }
        });

    }

    private void getFacebookData(LoginResult loginResult) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    facebook_id = object.getString("id");
                    first_name = object.getString("first_name");
                    last_name = object.getString("last_name");
                    name = object.getString("name");
                    email = object.getString("email");
                    gender = object.getString("gender");
                    birthday = object.getString("birthday");
                    picture = object.getString("picture");

                    Log.v("values", facebook_id + ", " + name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(LoginActivity.this, LogoutActivity.class);
                intent.putExtra("facebook_id",facebook_id);
                startActivity(intent);
                finish();
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "id, first_name, last_name, name ,email, gender, birthday, picture, locale");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        callbackManager = CallbackManager.Factory.create();
//        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                btnFacebookLogin.performClick();
//                btnFacebookLogin.setPressed(true);
//                btnFacebookLogin.invalidate();
//                btnFacebookLogin.setPressed(false);
//                btnFacebookLogin.invalidate();
//
//            }
//        });
//    }

}
