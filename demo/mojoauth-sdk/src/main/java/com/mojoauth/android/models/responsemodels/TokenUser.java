package com.mojoauth.android.models.responsemodels;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// <summary>
//	Response containing Definition of Token User Profile
// </summary>

public class TokenUser {

    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("auth_type")
    @Expose
    private String authType;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("aud")
    @Expose
    private String aud;
    @SerializedName("exp")
    @Expose
    private Integer exp;
    @SerializedName("jti")
    @Expose
    private String jti;
    @SerializedName("iat")
    @Expose
    private Integer iat;
    @SerializedName("iss")
    @Expose
    private String iss;
    @SerializedName("nbf")
    @Expose
    private Integer nbf;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public Integer getIat() {
        return iat;
    }

    public void setIat(Integer iat) {
        this.iat = iat;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public Integer getNbf() {
        return nbf;
    }

    public void setNbf(Integer nbf) {
        this.nbf = nbf;
    }

}
