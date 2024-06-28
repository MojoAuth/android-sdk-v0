/*
 *
 * Created by MojoAuth Development Team
   Copyright 2022 MojoAuth All rights reserved.
*/

package com.mojoauth.android.handler;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;


import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.mojoauth.android.api.MojoAuthApi;
import com.mojoauth.android.helper.ErrorResponse;
import com.mojoauth.android.helper.MojoAuthSDK;
import com.mojoauth.android.models.responsemodels.JwksResponse;
import com.mojoauth.android.models.responsemodels.VerifyTokenResponse;
import com.mojoauth.android.helper.MojoAuthSDK.Initialize;

public class Jwks {

	public Jwks() {
		if (!MojoAuthSDK.validateJwk()) {
		boolean b=	this.setValues();

		}
	}
	private static Boolean verify;
	private static int code;
	static String error;

	static ErrorResponse errorResponse;

	/**
	 *
	 * @param accessToken The accessToken
	 * @param handler The handler
	 */
	public void verifyAccessToken(String accessToken, final AsyncHandler<VerifyTokenResponse> handler) {

		execute(accessToken, new AsyncHandler<VerifyTokenResponse>() {

			@Override
			public void onSuccess(VerifyTokenResponse response) {
				handler.onSuccess(response);
			}

			@Override
			public void onFailure(ErrorResponse errorResponse) {
				handler.onFailure(errorResponse);
			}
		});
	}


	private static void execute(String accessToken,final AsyncHandler<VerifyTokenResponse> asyncHandler) {
		verify=verifyJWT(accessToken);

		if (code==0) {

			VerifyTokenResponse verifyAccessToken = new VerifyTokenResponse();
			verifyAccessToken.setIsValid(verify);
			verifyAccessToken.setAccessToken(accessToken);
			asyncHandler.onSuccess(verifyAccessToken);
		}else {
			ErrorResponse errorResponse =new ErrorResponse();
			errorResponse.setCode(code);
			errorResponse.setMessage("Exception");
			errorResponse.setDescription(error);

			asyncHandler.onFailure(errorResponse);
		}
	}

	private static Boolean verifyJWT(String jwt) {
		if (!MojoAuthSDK.validateJwk()) {
			boolean b=setValues();
		}

		try {

			String modulus = MojoAuthSDK.getModulus();
			String exponent = MojoAuthSDK.getExponent();

			PublicKey publicKey;
			publicKey=getPublicKey(modulus,exponent);

			//get signed data and signature from JWT
			String signedData = jwt.substring(0, jwt.lastIndexOf("."));
			String signatureB64u = jwt.substring(jwt.lastIndexOf(".")+1,jwt.length());
			byte[] signature = Base64.decode(signatureB64u.getBytes("UTF-8"), Base64.URL_SAFE);


			//verify Signature
			Signature sig = Signature.getInstance("SHA256withRSA");
			sig.initVerify(publicKey);
			sig.update(signedData.getBytes());
			boolean isVerify = sig.verify(signature);

			//System.out.println(publicKey);
			verify =isVerify;
			return isVerify;

		}catch(Exception e) {
			if (code == 0){
				code = 101;
				error = e.toString();

				return false;
			}else {return false;}
		}
	}

	private static boolean setValues(){
		MojoAuthApi mojoAuthApi=new MojoAuthApi();
		mojoAuthApi.getJWKS(new AsyncHandler<JwksResponse>() {

			@Override
			public void onSuccess(JwksResponse data) {
				// TODO Auto-generated method stub


				Initialize.setExponent(data.getKeys().get(0).getE());
				Initialize.setModulus(data.getKeys().get(0).getN());
				Log.d("Initialize","Done");
			}

			@Override
			public void onFailure(ErrorResponse errorcode) {
				// TODO Auto-generated method stub
				code = errorcode.getCode();
				error = errorcode.getDescription();


			}
		});
		try {
			Thread.sleep(1000);
			return true;
		} catch (Exception e) {
			return true;
		}
	}

	private static PublicKey getPublicKey(String modulus, String exponent)  {
		try {
			byte[] modulusBytes = Base64.decode(modulus.getBytes("UTF-8"),Base64.URL_SAFE);
			byte[] exponentBytes = Base64.decode(exponent.getBytes("UTF-8"), Base64.URL_SAFE);

			BigInteger e = new BigInteger(1, exponentBytes);
			BigInteger m = new BigInteger(1, modulusBytes);

			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
			KeyFactory fact = KeyFactory.getInstance("RSA");

			PublicKey pubKeyn = fact.generatePublic(keySpec);

			return pubKeyn;
		} catch (InvalidKeySpecException | NoSuchAlgorithmException  e) {
			code = 101;
			error = e.toString();


		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;

	}




}
