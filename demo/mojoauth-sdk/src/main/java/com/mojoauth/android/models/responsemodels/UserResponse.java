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
public class UserResponse {

	@SerializedName("authenticated")
	@Expose
	private Boolean authenticated;
	@SerializedName("oauth")
	@Expose
	private Oauth oauth;
	@SerializedName("user")
	@Expose
	private User user;

	public Boolean getAuthenticated() {
	return authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
	this.authenticated = authenticated;
	}

	public Oauth getOauth() {
	return oauth;
	}

	public void setOauth(Oauth oauth) {
	this.oauth = oauth;
	}

	public User getUser() {
	return user;
	}

	public void setUser(User user) {
	this.user = user;
	}

	
}