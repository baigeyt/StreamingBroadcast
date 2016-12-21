package com.hyc.rec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

import com.hyc.bean.InterWeb;
import com.hyc.db.DBManagerCode;

public class RecBroadcast {

	InterWeb interWeb = new InterWeb();
	private String ic_String;
	DBManagerCode mDBManagerCode;

	public void receiveBroadcast(Context mContext) {
		URL five_url;
		mDBManagerCode = new DBManagerCode();
		mDBManagerCode.creatDB();
		try {
			System.out.println("进入获取播报编码的接口");
			five_url = new URL(interWeb.getURL_Broadcast());
			HttpURLConnection five_urlConnection = (HttpURLConnection) five_url
					.openConnection();
			five_urlConnection.setRequestMethod("GET");
			five_urlConnection.setReadTimeout(5000);
			five_urlConnection.setConnectTimeout(5000);

			System.out.println("分班播报接口链接 " + interWeb.getURL_Broadcast());

			if (five_urlConnection.getResponseCode() == 200) {
				
				System.out.println("获取播报编码接口返回200");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								five_urlConnection.getInputStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					ic_String += line;
				}
				System.out.println("ic_String" + ic_String);

				JSONObject jsonobject = new JSONObject(ic_String.substring(4));
				String errcode = jsonobject.getString("errcode");

				if (errcode.equals("0")) {
					Intent iSReflush = new Intent();
					iSReflush.setAction("com.baige.ui.service");
					iSReflush.putExtra("reflush2", "1");
					mContext.sendBroadcast(iSReflush);
					mDBManagerCode.deleteDB();
					JSONArray jsonArray = new JSONObject(ic_String.substring(4))
							.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {

						JSONObject object = jsonArray.getJSONObject(i);
						String code = object.getString("code");
						String name = object.getString("name");
						
						
						mDBManagerCode.insert(name,code);
						

						System.out.println("解析出来的：" + code + name);
					}
					ic_String = null;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}finally{
			Intent iSMiss = new Intent();
			iSMiss.setAction("com.baige.ui.service");
			iSMiss.putExtra("miss2", "2");
			mContext.sendBroadcast(iSMiss);
		}
		mDBManagerCode.closeDB();
	}

}
