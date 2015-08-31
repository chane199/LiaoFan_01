/**
 * 
 */
package com.liaofan_01.kindmanage;

import com.amap.api.maps2d.model.LatLng;

import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ToastUtil {


	public static void show(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
	}
	public static void show(Context context, int info) {
		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
	}

	public static void show(Context context,LatLng mTarget) {
		// TODO Auto-generated method stub
		Toast.makeText(context, mTarget.toString(), Toast.LENGTH_LONG).show();
	}
}
