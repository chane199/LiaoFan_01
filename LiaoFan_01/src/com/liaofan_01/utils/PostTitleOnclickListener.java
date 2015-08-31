package com.liaofan_01.utils;

import com.liaofan_01.activities.PostDetailActivity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class PostTitleOnclickListener implements OnClickListener {

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(v.getContext(), PostDetailActivity.class);
		intent.putExtra("ID", v.getTag().toString());
		v.getContext().startActivity(intent);
	}
	
}
