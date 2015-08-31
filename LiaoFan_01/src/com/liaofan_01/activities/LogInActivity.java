package com.liaofan_01.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSONObject;
import com.example.liaofan_01.R;
import com.liaofan_01.utils.NetWorkThread;
import com.liaofan_01.utils.Config;

public class LogInActivity extends Activity {

	private EditText nameEditText;
	private EditText pwdEditText;
	private ImageButton submitButton;
	private ImageButton registerButton;
	private Handler handler;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		nameEditText = (EditText) findViewById(R.id.logInName);
		pwdEditText = (EditText) findViewById(R.id.logInPwd);
		submitButton = (ImageButton) findViewById(R.id.logInSubmit);
		registerButton = (ImageButton) findViewById(R.id.register);
		handler = new LogInHandler();

		submitButton.setOnClickListener(new submitButtonListener());
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LogInActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	class submitButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			System.out.println("Onclick");
			try {
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("nickname=" + nameEditText.getText().toString()+
						"&" + "password=" + pwdEditText.getText().toString());
				NetWorkThread ntThread = new NetWorkThread(stringBuffer.toString());
				ntThread.setUrl(Config.getInstance().getUrl() + "user/login_user.php");
				ntThread.setHandler(handler);
				ntThread.start();
				Message msg = handler.obtainMessage();
				handler.handleMessage(msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class LogInHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			JSONObject s = JSONObject.parseObject(msg.obj.toString());
			System.out.println(s.getIntValue("code"));
			if (s.getIntValue("code") == 0) {
				new AlertDialog.Builder(LogInActivity.this).setTitle("��¼	")
						.setMessage(s.getString("msg"))
						.setPositiveButton("ȷ��", null).show();
			} else {
				Config.getInstance().setUserName(s.getString("nickname"));
				Intent intent = new Intent(LogInActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
		}
	}
}
