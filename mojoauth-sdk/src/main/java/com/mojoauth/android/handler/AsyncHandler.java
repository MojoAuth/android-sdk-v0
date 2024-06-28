package com.mojoauth.android.handler;


import com.mojoauth.android.helper.ErrorResponse;

import java.io.Serializable;

public interface AsyncHandler<T> extends Serializable{
	void onFailure(ErrorResponse error);
	void onSuccess(T data);

}
