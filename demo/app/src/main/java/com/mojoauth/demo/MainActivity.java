package com.mojoauth.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.mojoauth.android.api.MojoAuthApi;
import com.mojoauth.android.handler.AsyncHandler;
import com.mojoauth.android.handler.Jwks;
import com.mojoauth.android.helper.ErrorResponse;
import com.mojoauth.android.helper.MojoAuthSDK;
import com.mojoauth.android.models.responsemodels.VerifyTokenResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MojoAuthSDK.Initialize initialize = new MojoAuthSDK.Initialize();
        initialize.setApiKey("<Enter_APIKey>");
        verifyAccessToken("<AccessToken>");

    }

    Gson gson =new Gson();
    public void verifyAccessToken(String token) {
        Jwks jwks = new Jwks();
        jwks.verifyAccessToken(token, new AsyncHandler<VerifyTokenResponse>() {
            @Override
            public void onFailure(ErrorResponse error) {
                error.getDescription();
            }

            @Override
            public void onSuccess(VerifyTokenResponse data) {
                data.getIsValid();
            }
        });
    }
}
