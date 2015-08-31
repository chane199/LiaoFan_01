package com.liaofan_01.activities;

import com.liaofan_01.base.FragmentActivity;
import com.liaofan_01.kindmanage.EditPostActivity;

import android.app.Fragment;

public class EditActivity extends FragmentActivity {

	@Override
	protected Fragment getFragment() {
		// TODO Auto-generated method stub
		return new EditPostActivity();
	}
	

}
