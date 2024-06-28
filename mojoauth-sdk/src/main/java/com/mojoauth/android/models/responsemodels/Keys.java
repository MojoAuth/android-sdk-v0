/* 
 * 
 * Created by MojoAuth Development Team
   Copyright 2022 MojoAuth All rights reserved.
   
 */
package com.mojoauth.android.models.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Keys {

@SerializedName("kty")
@Expose
private String kty;
@SerializedName("kid")
@Expose
private String kid;
@SerializedName("use")
@Expose
private String use;
@SerializedName("alg")
@Expose
private String alg;
@SerializedName("n")
@Expose
private String n;
@SerializedName("e")
@Expose
private String e;

public String getKty() {
return kty;
}

public void setKty(String kty) {
this.kty = kty;
}

public String getKid() {
return kid;
}

public void setKid(String kid) {
this.kid = kid;
}

public String getUse() {
return use;
}

public void setUse(String use) {
this.use = use;
}

public String getAlg() {
return alg;
}

public void setAlg(String alg) {
this.alg = alg;
}

public String getN() {
return n;
}

public void setN(String n) {
this.n = n;
}

public String getE() {
return e;
}

public void setE(String e) {
this.e = e;
}

}