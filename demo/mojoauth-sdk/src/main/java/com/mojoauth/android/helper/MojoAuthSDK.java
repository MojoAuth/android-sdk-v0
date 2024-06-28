package com.mojoauth.android.helper;


import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.mojoauth.android.social.FacebookNativeActivity;
import com.mojoauth.android.social.GoogleNativeActivity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class MojoAuthSDK {

    private static String MOJOAUTH_API_ROOT = "https://api.mojoauth.com";
    private MojoAuthSDK() {


    }

    public static class Initialize{
        private static String apiKey,domain,modulus,exponent;

        public static void setApiKey(String apiKey) {
            Initialize.apiKey = apiKey;
        }
        public static void setModulus(String modulus) {
            Initialize.modulus = modulus;
        }
        public static void setExponent(String exponent) {
            Initialize.exponent = exponent;
        }



    }

    public static class NativeLogin{
        public static Intent intent;
        private static Collection<String> facebookPermissions;
        private static Scope[] googleScopes;
        private static  String googleServerClientID,socialAppName;

        public NativeLogin() {

            this.googleScopes = new Scope[]{new Scope(Scopes.PROFILE),new Scope(Scopes.EMAIL)};
            this.facebookPermissions = Arrays.asList("email");

            if(!MojoAuthSDK.validate()){
                throw new MojoAuthSDK.InitializeException();
            }
        }

        public void setGoogleServerClientID(String googleServerClientID) {
            NativeLogin.googleServerClientID = googleServerClientID;

        }

        public void setSocialAppName(String socialAppName){
            NativeLogin.socialAppName =socialAppName;
        }
        public void setPermissions(@NonNull Collection<String> permissions) {
            this.facebookPermissions = permissions;
        }

        private void startNativeLogin(Activity activity, int requestCode){
            activity.startActivityForResult(intent,requestCode);
        }
        public void startFacebookNativeLogin(Activity activity, int requestCode){
            intent = new Intent(activity, FacebookNativeActivity.class);
           activity.startActivityForResult(intent,requestCode);
           // activity.startActivity(intent);
        }

        public void startGoogleNativeLogin(Activity activity, int requestCode){
            intent = new Intent(activity, GoogleNativeActivity.class);
            activity.startActivityForResult(intent,requestCode);
        }


    }
    public static Collection<String> getFaceBookPermissions() {
        return NativeLogin.facebookPermissions;
    }


    public static String getGoogleServerClientID() {
        return NativeLogin.googleServerClientID;
    }

    public static Scope[] getGoogleScopes() {
        return NativeLogin.googleScopes;
    }

    public static String getSocialAppName() {
        return NativeLogin.socialAppName;
    }

    public static boolean validate(){
        return Initialize.apiKey != null && Initialize.apiKey.length() != 0;
    }

    public static boolean validateJwk() {
        return Initialize.exponent != null && Initialize.exponent.length() != 0 && Initialize.modulus != null
                && Initialize.modulus.length() != 0;
    }


    public static String getApiKey() {
        return Initialize.apiKey;
    }

    public static String getModulus() {
        return Initialize.modulus;
    }

    public static String getExponent() {
        return Initialize.exponent;
    }

    public static String getApiDomain() {
        if(Initialize.domain!=null && Initialize.domain.length() > 0){
            return Initialize.domain;
        }else{
            return MOJOAUTH_API_ROOT;
        }
    }






    public static class InitializeException extends RuntimeException{
        public InitializeException() {
            super("MojoAuth SDK not initialized properly");
        }
    }

    public static String GetRequestUrl(String url, Map<String, String> queryArgs) {
        String keyvalueString = createKeyValueString(queryArgs);
        if (url.contains("?"))
            return url + "&" + keyvalueString;

        return url + "?" + keyvalueString;
    }


    private static String createKeyValueString(Map<String, String> queryArgs) {
        if (queryArgs != null) {
            String[] sb = new String[queryArgs.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : queryArgs.entrySet()) {
                sb[i] = entry.getKey() + "=" + entry.getValue();
                i++;
            }
            return combine(sb, "&");
        } else
            return null;
    }


    private static String combine(String[] s, String glue) {
        int k = s.length;
        if (k == 0)
            return null;
        StringBuilder out = new StringBuilder();
        out.append(s[0]);
        for (int x = 1; x < k; ++x)
            out.append(glue).append(s[x]);
        return out.toString();
    }


    public static String getFinalPath(String path, Map<String, String> map) {
        String finalPath = path;
        Map<String, String> data = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        if (map != null && !map.isEmpty()) {
            data.putAll(map);
        }
        if (isPlaceholders(path)) {
            finalPath = replacePlaceholders(path, data);
        }
        return finalPath;
    }

    private static Boolean isPlaceholders(String path) {
        return path.contains("{{") && path.contains("}}");
    }


    private static String replacePlaceholders(String path, Map<String, String> data) {
        String res = path;
        String[] arr = res.split("/");
        for (int i = 0; i < arr.length; i++) {
            if (isPlaceholders(arr[i])) {
                String field = arr[i].substring(arr[i].indexOf("{{") + 2, arr[i].indexOf("}}"));
                arr[i] = data.get(field);
            }
        }
        return join(arr);
    }

    private static String join(String[] arr) {
        String res = "";
        for (int i = 0; i < arr.length; i++) {
            res += arr[i];
            if (i != arr.length - 1) {
                res += "/";
            }
        }
        return res;
    }
}
