 package com.liaofan_01.activities;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;




import com.example.liaofan_01.R;
import com.liaofan_01.fragments.*;
import com.liaofan_01.kindmanage.HomeActivity;
import com.liaofan_01.kindmanage.ManageKindFragment;
import com.liaofan_01.utils.Config;

public class MainActivity extends Activity implements OnClickListener {

	private LinearLayout homeMenu;
	private LinearLayout categoryMenu;
	private LinearLayout editMenu;
	private LinearLayout messageMenu;
	private LinearLayout myPageMenu;

	private int menuId = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager fm = getFragmentManager();  
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.mainContent, new HomeFragment());
        transaction.commit();
		this.menuId = R.id.menuHome;
		

		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				// TODO Auto-generated method stub
				System.out.println("dandy's uncaught: " + ex.toString());
			}
		});
		
		setContentView(R.layout.activity_main);

		homeMenu = (LinearLayout) findViewById(R.id.menuHome);
		categoryMenu = (LinearLayout) findViewById(R.id.menuCategory);
		editMenu = (LinearLayout) findViewById(R.id.menuEdit);
		messageMenu = (LinearLayout) findViewById(R.id.menuMessage);
		myPageMenu = (LinearLayout) findViewById(R.id.menuMyPage);
		
		homeMenu.setOnClickListener(this);
		categoryMenu.setOnClickListener(this);
		editMenu.setOnClickListener(this);
		messageMenu.setOnClickListener(this);
		myPageMenu.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
//		if (v.getId() == this.menuId)
//			return;
		Intent intent = null;
		FragmentManager fm = getFragmentManager();  
        FragmentTransaction transaction = fm.beginTransaction();
		switch (v.getId()) {
		case R.id.menuHome:
			transaction.replace(R.id.mainContent, new HomeFragment());
			//this.menuId = R.id.menuHome;
			//intent=new Intent(MainActivity.this,HomeActivity.class);
			break;
		case R.id.menuCategory:
			transaction.replace(R.id.mainContent, new CategoryFragment());
			//this.menuId = R.id.menuCategory;
			break;
		case R.id.menuEdit:
			intent = new Intent(MainActivity.this,EditActivity.class);
			startActivity(intent);
			break;
		case R.id.menuMyPage:
			transaction.replace(R.id.mainContent, new MyPageFragment());
			//this.menuId = R.id.menuMyPage;
			break;
		case R.id.menuMessage:
			transaction.replace(R.id.mainContent, new MessageFragment());
			//this.menuId = R.id.menuMessage;
			break;
		default:
			break;
		}
		transaction.commit();
	}
}
