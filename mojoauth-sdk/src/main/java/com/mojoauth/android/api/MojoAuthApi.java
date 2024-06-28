package com.mojoauth.android.api;
/*
 *
 * Created by MojoAuth Development Team
   Copyright 2022 MojoAuth All rights reserved.

 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mojoauth.android.handler.AsyncHandler;
import com.mojoauth.android.handler.JsonDeserializer;
import com.mojoauth.android.handler.MojoAuthRequest;
import com.mojoauth.android.helper.ErrorResponse;
import com.mojoauth.android.helper.MojoAuthSDK;
import com.mojoauth.android.helper.MojoAuthValidator;
import com.mojoauth.android.models.responsemodels.JwksResponse;
import com.mojoauth.android.models.responsemodels.LoginResponse;
import com.mojoauth.android.models.responsemodels.UserResponse;
import com.mojoauth.android.models.responsemodels.ValidateTokenResponse;
import com.mojoauth.android.models.responsemodels.VerifyTokenResponse;

import java.util.HashMap;
import java.util.Map;


public class MojoAuthApi {

    Gson gson =new Gson();

    public MojoAuthApi(){
        if(!MojoAuthSDK.validate()){
            throw new MojoAuthSDK.InitializeException();
        }
    }

    /**
     *
     * @param email The email
     * @param handler The handler
     */
    public void loginByMagicLink(String email,String language,String redirect_url, final AsyncHandler<LoginResponse> handler) {

        Map<String, String> queryParameters = new HashMap<String, String>();
        JsonObject bodyParameters = new JsonObject(); //Required
        String resourcePath = "users/magiclink";

        if (!MojoAuthValidator.isNullOrWhiteSpace(email)) {

            bodyParameters.addProperty("email", email);
        }
        queryParameters.put("language", language);
        queryParameters.put("redirect_url", redirect_url);

        MojoAuthRequest.execute("POST", resourcePath, queryParameters, gson.toJson(bodyParameters), new AsyncHandler<String>() {

            @Override
            public void onSuccess(String response) {
                TypeToken<LoginResponse> typeToken = new TypeToken<LoginResponse>() {
                };
                LoginResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
                handler.onSuccess(successResponse);
            }

            @Override
            public void onFailure(ErrorResponse errorResponse) {
                handler.onFailure(errorResponse);
            }
        });
    }
    /**
     *
     * @param email The email
     * @param handler The handler
     */
    public void loginByEmailOTP(String email,String language, final AsyncHandler<LoginResponse> handler) {

        Map<String, String> queryParameters = new HashMap<String, String>();
        JsonObject bodyParameters = new JsonObject(); //Required
        String resourcePath = "users/emailotp";

        if (!MojoAuthValidator.isNullOrWhiteSpace(email)) {

            bodyParameters.addProperty("email", email);

        }
        queryParameters.put("language", language);

        MojoAuthRequest.execute("POST", resourcePath, queryParameters, gson.toJson(bodyParameters), new AsyncHandler<String>() {

            @Override
            public void onSuccess(String response) {
                TypeToken<LoginResponse> typeToken = new TypeToken<LoginResponse>() {
                };
                LoginResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
                handler.onSuccess(successResponse);
            }

            @Override
            public void onFailure(ErrorResponse errorResponse) {
                handler.onFailure(errorResponse);
            }
        });
    }

    /**
     *
     * @param otp The otp
     * @param stateId The id
     * @param handler the handler
     */
    public void verifyEmailOTP(String otp,String stateId,final AsyncHandler<UserResponse> handler) {

        Map<String, String> queryParameters = new HashMap<String, String>();
        JsonObject bodyParameters = new JsonObject(); //Required
        String resourcePath = "users/emailotp/verify";

        if (!MojoAuthValidator.isNullOrWhiteSpace(otp)) {

            bodyParameters.addProperty("otp", otp);
        }

        if (!MojoAuthValidator.isNullOrWhiteSpace(stateId)) {

            bodyParameters.addProperty("state_id", stateId);
        }

        MojoAuthRequest.execute("POST", resourcePath, queryParameters, gson.toJson(bodyParameters), new AsyncHandler<String>() {

            @Override
            public void onSuccess(String response) {
                TypeToken<UserResponse> typeToken = new TypeToken<UserResponse>() {
                };
                UserResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
                handler.onSuccess(successResponse);
            }

            @Override
            public void onFailure(ErrorResponse errorResponse) {
                handler.onFailure(errorResponse);
            }
        });
    }

    /**
     *
     * @param stateId The id
     * @param handler The handler
     */
    public void pingStatus(String stateId, final AsyncHandler<UserResponse> handler) {

        Map<String, String> queryParameters = new HashMap<String, String>();
        String resourcePath = "users/status";

        if (!MojoAuthValidator.isNullOrWhiteSpace(stateId)) {

            queryParameters.put("state_id", stateId);
        }

        MojoAuthRequest.execute("GET", resourcePath, queryParameters,null, new AsyncHandler<String>() {

            @Override
            public void onSuccess(String response) {
                TypeToken<UserResponse> typeToken = new TypeToken<UserResponse>() {
                };
                UserResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
                handler.onSuccess(successResponse);
            }

            @Override
            public void onFailure(ErrorResponse errorResponse) {
                handler.onFailure(errorResponse);
            }
        });
    }


    /**
     *
     * @param handler The handler
     */
    public void getJWKS(final AsyncHandler<JwksResponse> handler) {

        Map<String, String> queryParameters = new HashMap<String, String>();
        String resourcePath = "token/jwks";


        MojoAuthRequest.execute("GET", resourcePath, queryParameters,null, new AsyncHandler<String>() {

            @Override
            public void onSuccess(String response) {
                TypeToken<JwksResponse> typeToken = new TypeToken<JwksResponse>() {
                };
                JwksResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
                handler.onSuccess(successResponse);
            }

            @Override
            public void onFailure(ErrorResponse errorResponse) {
                handler.onFailure(errorResponse);
            }
        });
    }

    /**
     *
     * @param phone The phone
     * @param handler The handler
     */
    public void loginByPhone(String phone, final AsyncHandler<LoginResponse> handler) {

        Map<String, String> queryParameters = new HashMap<String, String>();
        JsonObject bodyParameters = new JsonObject(); //Required
        String resourcePath = "users/phone";

        if (!MojoAuthValidator.isNullOrWhiteSpace(phone)) {

            bodyParameters.addProperty("phone", phone);
        }

        MojoAuthRequest.execute("POST", resourcePath, queryParameters, gson.toJson(bodyParameters), new AsyncHandler<String>() {

            @Override
            public void onSuccess(String response) {
                TypeToken<LoginResponse> typeToken = new TypeToken<LoginResponse>() {
                };
                LoginResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
                handler.onSuccess(successResponse);
            }

            @Override
            public void onFailure(ErrorResponse errorResponse) {
                handler.onFailure(errorResponse);
            }
        });
    }

    /**
     *
     * @param otp The otp
     * @param stateId The id
     * @param handler the handler
     */
    public void verifyPhoneOTP(String otp,String stateId,final AsyncHandler<UserResponse> handler) {

        Map<String, String> queryParameters = new HashMap<String, String>();
        JsonObject bodyParameters = new JsonObject(); //Required
        String resourcePath = "users/phone/verify";

        if (!MojoAuthValidator.isNullOrWhiteSpace(otp)) {

            bodyParameters.addProperty("otp", otp);
        }

        if (!MojoAuthValidator.isNullOrWhiteSpace(stateId)) {

            bodyParameters.addProperty("state_id", stateId);
        }

        MojoAuthRequest.execute("POST", resourcePath, queryParameters, gson.toJson(bodyParameters), new AsyncHandler<String>() {

            @Override
            public void onSuccess(String response) {
                TypeToken<UserResponse> typeToken = new TypeToken<UserResponse>() {
                };
                UserResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
                handler.onSuccess(successResponse);
            }

            @Override
            public void onFailure(ErrorResponse errorResponse) {
                handler.onFailure(errorResponse);
            }
        });
    }
    
    
    /**
     *
     * @param accessToken The access token
     * @param handler The handler
     */
    public void validateToken(String accessToken, final AsyncHandler<ValidateTokenResponse> handler) {

        Map<String, String> queryParameters = new HashMap<String, String>();
        String resourcePath = "token/verify";

        if (!MojoAuthValidator.isNullOrWhiteSpace(accessToken)) {
            queryParameters.put("access_token", accessToken);
        }
        MojoAuthRequest.execute("POST", resourcePath, queryParameters, null, new AsyncHandler<String>() {

            @Override
            public void onSuccess(String response) {
                TypeToken<ValidateTokenResponse> typeToken = new TypeToken<ValidateTokenResponse>() {
                };
                ValidateTokenResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
                handler.onSuccess(successResponse);
            }

            @Override
            public void onFailure(ErrorResponse errorResponse) {
                handler.onFailure(errorResponse);
            }
        });
    }

    /**
     *
     * @param refreshToken The refresh token
     * @param handler The handler
     */
    public void refreshToken(String refreshToken, final AsyncHandler<UserResponse> handler) {
        Map<String, String> queryParameters = new HashMap<String, String>();
        JsonObject bodyParameters = new JsonObject(); //Required
        String resourcePath = "token/refresh";

        if (!MojoAuthValidator.isNullOrWhiteSpace(refreshToken)) {
            bodyParameters.addProperty("refresh_token", refreshToken);
        }
        MojoAuthRequest.execute("POST", resourcePath, queryParameters, gson.toJson(bodyParameters), new AsyncHandler<String>() {

            @Override
            public void onSuccess(String response) {
                TypeToken<UserResponse> typeToken = new TypeToken<UserResponse>() {

                };
                UserResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
                handler.onSuccess(successResponse);
            }

            @Override
            public void onFailure(ErrorResponse errorResponse) {
                handler.onFailure(errorResponse);
            }
        });
    }

}
