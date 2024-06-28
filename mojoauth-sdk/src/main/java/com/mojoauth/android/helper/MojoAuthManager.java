


package com.mojoauth.android.helper;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mojoauth.android.handler.AsyncHandler;
import com.mojoauth.android.handler.JsonDeserializer;
import com.mojoauth.android.handler.MojoAuthRequest;
import com.mojoauth.android.models.responsemodels.AccessTokenResponse;
import com.mojoauth.android.models.responsemodels.LoginResponse;
import com.mojoauth.android.models.responsemodels.Provider;
import com.mojoauth.android.models.responsemodels.UserResponse;

import java.util.HashMap;

import java.util.Map;




/**
 * Performs user login and manages different native / web logins
 *
 */
public class MojoAuthManager {
	private static final String MOBILE_EXT = "&ismobile=1";
	/** Enable orDisable Native Login for Google and Facebook **/
	public static Boolean nativeLogin = false;
	/** Callback function. SDK handles this variable. No need to modify. **/ 
	public static AsyncHandler<UserResponse> asyncHandler;
	/** MojoAuth api key. SDK handles this variable. No need to modify. **/
	protected static String AKey;
	/** Image Url for getting the image icons of Providers **/
	public static String ImageUrl;
	/** File System version **/
	public static String ImgVersion;
	/**Callback handler for Face book Native Login **/
	protected static CallbackManager callbackManager;

	public static void setCallbackManager(String key,CallbackManager callback){
		MojoAuthManager.AKey = key;
		callbackManager = callback;
	}

	/**
	 * Base function to log in user. Ensure static 'asyncHandler' set first
	 * @param activity Activity where the user will land after login process
	 * @param provider    provider to be logged in with from providers list retrieved from MojoAuth server.
	 * @param asyncHandler    callback function
	 */
	public static void performLogin(final Activity activity, Provider provider, AsyncHandler<UserResponse> asyncHandler) {
		MojoAuthManager.asyncHandler = asyncHandler;
		if (provider.getName().equalsIgnoreCase("facebook") && MojoAuthManager.nativeLogin) {
			/** FACEBOOK SESSION **/
			OpenFacebookSession(activity);
			/**openActiveSession(activity, true);**/
		}
		else if (provider.getName().equalsIgnoreCase("google") && MojoAuthManager.nativeLogin) {
			/** Google Activity **/
			activity.startActivity(new Intent(activity, GoogleSSO.class));
		}
		else {
			performWebLogin(activity, provider);
		}
	}

	public static void getNativeAppConfiguration(String key, CallbackManager callback) {
		MojoAuthManager.AKey = key;
		callbackManager=callback;
	}

	/**
	 * Redirect user to a web view for provider-specific login
	 * @param activity Activity where the user will be returned
	 * @param provider provider by which the login process was started
    */
	public static void performWebLogin(final Activity activity, Provider provider) {
	//	Intent i = new Intent(activity, WebLogin.class);
	//	i.putExtra(WebLogin.KEY_URL, provider.Endpoint + MOBILE_EXT);
	//	i.putExtra(WebLogin.KEY_PROVIDER, provider.Name);
	//v	activity.startActivity(i);
	}

	private static void HandleFacebookToken()
	{
		try{
			String fbToken = AccessToken.getCurrentAccessToken().getToken().toString();
			MojoAuthManager.getResponseFb(fbToken, new AsyncHandler<UserResponse>() {
				@Override
				public void onFailure(ErrorResponse error) {

				}

				@Override
				public void onSuccess(UserResponse data) {

					MojoAuthManager.asyncHandler.onSuccess(data);
				}

			});
		}
		catch(FacebookOperationCanceledException f){
			Log.i("Cancel","facebook");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("Facebook Operation cancelled");
			errorResponse.setCode(400);
			errorResponse.setDescription("Facebook Operation cancelled");
			MojoAuthManager.asyncHandler.onFailure(errorResponse);
		}

	}









	private static void OpenFacebookSession(final Activity activity) {

		LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				HandleFacebookToken();
			}

			@Override
			public void onCancel() {
				Log.i("Cancel", "facebook");
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("Facebook Operation cancelled");
				errorResponse.setCode(400);
				errorResponse.setDescription("Facebook Operation cancelled");
				MojoAuthManager.asyncHandler.onFailure(errorResponse);

			}

			@Override
			public void onError(FacebookException error) {
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage(error.getMessage());
				errorResponse.setCode(400);
				errorResponse.setDescription(error.toString());
				MojoAuthManager.asyncHandler.onFailure(errorResponse);
			}

		});


		LoginManager.getInstance().logInWithReadPermissions(activity, MojoAuthSDK.getFaceBookPermissions());


	}


	/**
	 * Send Facebook token to MojoAuth server
	 * @param fbToken Token from facebook
	 * @param handler callback handler
	 */
	public static void getResponseFb(String fbToken, final AsyncHandler<UserResponse> handler)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("api_key",AKey);
		params.put("fb_access_token",fbToken);
		providerHandler("users/social/facebook", params, handler);
	}
	/**
	 * Send Google token to MojoAuth server
	 * @param googleToken Token from google
	 * @param handler callback handler
	 */
	public static void getResponseGoogle(String googleToken,boolean offline, final AsyncHandler<UserResponse> handler)
	{
		Map<String, String> params = new HashMap<String, String>();
		if (offline){
			params.put("api_key",AKey);
			params.put("google_access_token",googleToken);
		}else {
			params.put("api_key",AKey);
			params.put("google_access_token",googleToken);
		}

		providerHandler("users/social/google", params, handler);
	}




	/**
	 * Generic request
	 * @param uri Url for sending request
	 * @param params query parameters to be used
	 * @param handler callback handler
	 */
	public static void providerHandler(String uri, Map<String,String> params, final AsyncHandler<UserResponse> handler) {

		if(MojoAuthSDK.getSocialAppName()!="" && MojoAuthSDK.getSocialAppName()!=null){
			params.put("SocialAppName",MojoAuthSDK.getSocialAppName());
		}
		Map<String, String> queryParameters = params;
		String resourcePath = uri;

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
}
