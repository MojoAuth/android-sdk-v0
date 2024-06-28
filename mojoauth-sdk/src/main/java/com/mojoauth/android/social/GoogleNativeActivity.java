package com.mojoauth.android.social;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mojoauth.android.R;
import com.mojoauth.android.handler.AsyncHandler;
import com.mojoauth.android.helper.ErrorResponse;
import com.mojoauth.android.helper.MojoAuthManager;
import com.mojoauth.android.helper.MojoAuthSDK;
import com.mojoauth.android.helper.ProviderPermissions;
import com.mojoauth.android.models.responsemodels.AccessTokenResponse;
import com.mojoauth.android.models.responsemodels.Provider;
import com.mojoauth.android.models.responsemodels.UserResponse;


public class GoogleNativeActivity extends AppCompatActivity {
    Context context;
    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;

    private UserResponse accessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_native);
        if(!MojoAuthSDK.validate()){
            throw new MojoAuthSDK.InitializeException();
        }

        MojoAuthManager.nativeLogin = true;

        context = GoogleNativeActivity.this;

        ProviderPermissions.resetPermissions();
        MojoAuthManager.setCallbackManager(MojoAuthSDK.getApiKey(),null);
        showdialog();
    }


    @Override
    protected void onDestroy() {
        Log.v(LOG_TAG, "onDestory");
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }


    }


    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            Log.v(LOG_TAG, "Receieved notification about network status");
            isNetworkAvailable(context);

        }


        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            if (!isConnected) {
                                Log.v(LOG_TAG, "Now you are connected to Internet!");
                                //  Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                                isConnected = true;
                                //do your processing here ---
                                //if you need to post any data to the server or get status
                                //update from the server
                            }
                            return true;
                        }
                    }
                }
            }
            Log.v(LOG_TAG, "You are not connected to Internet!");
            Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

            isConnected = false;
            return false;
        }
    }


    public void showdialog() {
        Provider provider = new Provider();
        provider.setName("google");
        MojoAuthManager.performLogin(GoogleNativeActivity.this, provider, new AsyncHandler<UserResponse>() {

            @Override
            public void onFailure(ErrorResponse error) {
                if (error.getMessage().equals("GoogleSSO cancelled"));{
                finish();
                }
            }

            @Override
            public void onSuccess(UserResponse data) {
                accessToken = data;
                    sendAccessToken(data.getOauth().getAccessToken(),data.getOauth().getRefreshToken());
            }

        });

    }


    public void sendAccessToken(String accessToken,String refreshToken) {

        AccessTokenResponse accesstoken = new AccessTokenResponse();
        accesstoken.access_token = accessToken;
        accesstoken.refresh_token=refreshToken;
        accesstoken.provider = "google";
        Intent intent = new Intent();
        intent.putExtra("accesstoken", accessToken);
        intent.putExtra("refreshtoken",refreshToken);
        intent.putExtra("provider", "google");
        setResult(2, intent);
        finish();//finishing activity
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (data != null) {
                String token = data.getStringExtra("accesstoken");
                String provider = data.getStringExtra("provider");
                String refreshtoken=data.getStringExtra("refreshtoken");
                Intent intent = new Intent();
                intent.putExtra("accesstoken", token);
                intent.putExtra("refreshtoken",refreshtoken);
                intent.putExtra("provider", "google");
                setResult(2, intent);
                finish();//finishing activity
            }
        }
    }


}


