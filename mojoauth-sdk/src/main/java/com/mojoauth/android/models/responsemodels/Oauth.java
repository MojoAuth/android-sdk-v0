/* 
 * 
 * Created by MojoAuth Development Team
   Copyright 2022 MojoAuth All rights reserved.
   
 */
package com.mojoauth.android.models.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Oauth {

@SerializedName("access_token")
@Expose
private String accessToken;
@SerializedName("id_token")
@Expose
private String idToken;
@SerializedName("refresh_token")
@Expose
private String refreshToken;
@SerializedName("expires_in")
@Expose
private String expiresIn;
@SerializedName("token_type")
@Expose
private String tokenType;

public String getAccessToken() {
return accessToken;
}

public void setAccessToken(String accessToken) {
this.accessToken = accessToken;
}

public String getIdToken() {
return idToken;
}

public void setIdToken(String idToken) {
this.idToken = idToken;
}

public String getRefreshToken() {
return refreshToken;
}

public void setRefreshToken(String refreshToken) {
this.refreshToken = refreshToken;
}

public String getExpiresIn() {
return expiresIn;
}

public void setExpiresIn(String expiresIn) {
this.expiresIn = expiresIn;
}

public String getTokenType() {
return tokenType;
}

public void setTokenType(String tokenType) {
this.tokenType = tokenType;
}

}