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
public class User {

@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("issuer")
@Expose
private String issuer;
@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("identifier")
@Expose
private String identifier;

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public String getUpdatedAt() {
return updatedAt;
}

public void setUpdatedAt(String updatedAt) {
this.updatedAt = updatedAt;
}

public String getIssuer() {
return issuer;
}

public void setIssuer(String issuer) {
this.issuer = issuer;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getIdentifier() {
return identifier;
}

public void setIdentifier(String identifier) {
this.identifier = identifier;
}

}
