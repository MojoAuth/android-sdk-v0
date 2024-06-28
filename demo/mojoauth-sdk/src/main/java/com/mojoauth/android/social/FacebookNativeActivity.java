package com.mojoauth.android.social;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.mojoauth.android.R;
import com.mojoauth.android.handler.AsyncHandler;
import com.mojoauth.android.helper.ErrorResponse;
import com.mojoauth.android.helper.MojoAuthManager;
import com.mojoauth.android.helper.MojoAuthSDK;
import com.mojoauth.android.models.responsemodels.AccessTokenResponse;
import com.mojoauth.android.models.responsemodels.Provider;
import com.mojoauth.android.models.responsemodels.UserResponse;


public class FacebookNativeActivity extends AppCompatActivity {
    Context context;
    CallbackManager callManager;
    private UserResponse accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_native);

        if(!MojoAuthSDK.validate()){
            throw new MojoAuthSDK.InitializeException();
        }

        context = FacebookNativeActivity.this;

        FacebookSdk.fullyInitialize();
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();
        MojoAuthManager.nativeLogin = true;
        callManager = CallbackManager.Factory.create();

        MojoAuthManager.setCallbackManager(MojoAuthSDK.getApiKey(),callManager);
        showdialog();
    }
    public void sendAccessToken(String accessToken,String refreshToken) {
        AccessTokenResponse accesstoken = new AccessTokenResponse();
        accesstoken.access_token = accessToken;
        accesstoken.refresh_token=refreshToken;
        accesstoken.provider = "facebook";
        Intent intent = new Intent();
        intent.putExtra("accesstoken", accessToken);
        intent.putExtra("refreshtoken",refreshToken);
        intent.putExtra("provider", "facebook");
        setResult(2, intent);
        finish();//finishing activity
    }

    public void showdialog() {
        Provider provider = new Provider();
        provider.setName("facebook");
        MojoAuthManager.performLogin(FacebookNativeActivity.this, provider, new AsyncHandler<UserResponse>() {


            @Override
            public void onFailure(ErrorResponse error) {
                finish();
            }

            @Override
            public void onSuccess(UserResponse data) {
                accessToken = data;
                sendAccessToken(data.getOauth().getAccessToken(),data.getOauth().getRefreshToken());
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (data != null) {
                String token = data.getStringExtra("accesstoken");
                String refreshtoken=data.getStringExtra("refreshtoken");
                String provider = data.getStringExtra("provider");
                Intent intent = new Intent();
                intent.putExtra("accesstoken", token);
                intent.putExtra("refreshtoken",refreshtoken);
                intent.putExtra("provider", "facebook");
                setResult(2, intent);
                finish();//finishing activity
            }
        } else {
            callManager.onActivityResult(requestCode, resultCode, data);
        }

    }



}


