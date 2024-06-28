package com.mojoauth.android.models.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// <summary>
//	Response containing Definition of Validate Token Response
// </summary>

public class ValidateTokenResponse {

    @SerializedName("isValid")
    @Expose
    private Boolean isValid;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("user")
    @Expose
    private TokenUser user;

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public TokenUser getUser() {
        return user;
    }

    public void setUser(TokenUser user) {
        this.user = user;
    }
}

