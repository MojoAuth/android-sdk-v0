/* 
 * 
 * Created by MojoAuth Development Team
   Copyright 2022 MojoAuth All rights reserved.
   
 */

package com.mojoauth.android.models.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// <summary>
//	Response containing Definition of Complete Profile data
// </summary>
public class VerifyTokenResponse {

	@SerializedName("isValid")
	@Expose
	private Boolean isValid;
	@SerializedName("access_token")
	@Expose
	private String accessToken;

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
}