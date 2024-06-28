package com.mojoauth.android.handler;


import com.mojoauth.android.helper.ErrorResponse;

import java.io.IOException;

public class ExceptionResponse {

    public static ErrorResponse exception(IOException error) {
        ErrorResponse obj = new ErrorResponse();
        if (error.toString().contains("UnknownHostException")) {
            obj.setDescription("Thrown to indicate that the IP address of a host could not be determined, Please Check your internet connection");
            obj.setCode(101);
            obj.setMessage("UnknownHostException");

        } else if (error.toString().contains("IllegalArgumentException")) {
            obj.setDescription(error.toString());
            obj.setCode(102);
            obj.setMessage("IllegalArgumentException");

        } else if (error.toString().contains("MalformedURLException")) {
            obj.setDescription(error.toString());
            obj.setCode(103);
            obj.setMessage("MalformedURLException");

        } else if (error.toString().contains("SocketTimeoutException")) {
            obj.setDescription(error.toString());
            obj.setCode(104);
            obj.setMessage("SocketTimeoutException");

        } else if (error.toString().contains("IOException")) {
            obj.setDescription(error.toString());
            obj.setCode(105);
            obj.setMessage("IOException");

        } else {
            obj.setDescription("Unknown Error");
            obj.setCode(106);
            obj.setMessage("UnknownError");

            }
return obj;
    }







}
