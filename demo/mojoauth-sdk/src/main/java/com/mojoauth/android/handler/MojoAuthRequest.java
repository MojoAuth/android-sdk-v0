package com.mojoauth.android.handler;


import com.google.gson.Gson;

import com.mojoauth.android.helper.ErrorResponse;
import com.mojoauth.android.helper.MojoAuthSDK;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MojoAuthRequest {
    private static String authorization = "";
    private static RequestBody mbody;
    private static Request request;
    private static Gson gson = new Gson();

    public static void execute(String method, String resourcePath, Map<String, String> params, String payload, final AsyncHandler<String> asyncHandler) {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15,TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        String serviceUrl = MojoAuthSDK.getApiDomain() + "/" + resourcePath;

        if (params.containsKey("access_token") ) {
            authorization = params.get("access_token");
            params.remove("access_token");
        }
        Headers.Builder builder = new Headers.Builder();
        builder.add("X-API-Key",MojoAuthSDK.getApiKey());

        if (authorization != "") {
            builder.add("Authorization", "Bearer "+authorization);
        }

        Headers h = builder.build();

        String url = MojoAuthSDK.GetRequestUrl(serviceUrl, params);
        if (payload != null) {
            mbody = RequestBody.create(mediaType, payload);
        } else {
            mbody = RequestBody.create(mediaType, "{}");
        }
        if(method=="GET"){
            request = new Request.Builder()
                    .url(url)
                    .headers(h)
                    .build();
        }
        else if(method=="POST"){
            request = new Request.Builder()
                    .url(url)
                    .post(mbody)
                    .headers(h)
                    .build();
        }
        else if(method=="PUT"){
            request = new Request.Builder()
                    .url(url)
                    .headers(h)
                    .post(mbody)
                    .build();
        }
        else if(method=="DELETE"){
            request = new Request.Builder()
                    .url(url)
                    .delete(mbody)
                    .headers(h)
                    .build();
        }
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                ErrorResponse errorResponse=ExceptionResponse.exception(e);
                asyncHandler.onFailure(errorResponse);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    String responseString = response.body().string();
                    asyncHandler.onSuccess(responseString);

                }
                else {
                    Gson gson = new Gson();
                    String responseString = response.body().string();
                    ErrorResponse errorResponse = gson.fromJson(responseString, ErrorResponse.class);
                    asyncHandler.onFailure(errorResponse);

                }
            }
        });
    }

}


