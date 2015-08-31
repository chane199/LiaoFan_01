package com.liaofan_01.kindmanage;



import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;


import com.amap.api.maps2d.model.LatLng;
import com.example.liaofan_01.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.View.OnClickListener;


import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class EditPostActivity extends Fragment {
	LatLng mTarget=null;
	String country,province,city,street,locale,area;
	
//顶部5个选项
	String SKind=null,SPay=null,SSupport_need=null,SLocation=null,SAvail_time=null;


	
//交互界面各个视图控件
	PopupMenu popup = null;
	
	Button bnAdd, bnCancel;
	
	TextView post_kind;
	TextView payment;
	TextView support_need;
	TextView location;
	TextView avail_time;

//关于帖子 输入文本
	
	EditText itemName=null;//EditText
	EditText itemRemark=null;
	EditText contact=null;
	EditText itemDesc=null;	

	CheckBox CB1,CB2,CB3;
	
	//报酬一栏的显示控制布局
	LinearLayout payment1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		 final View rootView = inflater.inflate(R.layout.fragment_edit_post,
				container, false);
		//顶部5个选项
		post_kind=(TextView)rootView.findViewById(R.id.post_kind);
		payment=(TextView)rootView.findViewById(R.id.payment);
		support_need=(TextView)rootView.findViewById(R.id.support_need);
		location=(TextView)rootView.findViewById(R.id.location);
		avail_time=(TextView)rootView.findViewById(R.id.avail_time);
// 获取界面中的两个按钮
		bnAdd = (Button)rootView.findViewById(R.id.bnAdd);
		bnCancel = (Button)rootView.findViewById(R.id.bnCancel);
		
		//输入文本
		itemName=(EditText)rootView.findViewById(R.id.itemName);
		itemRemark=(EditText)rootView.findViewById(R.id.itemRemark);
		contact=(EditText)rootView.findViewById(R.id.contact);
		itemDesc=(EditText)rootView.findViewById(R.id.itemDesc);
		
		//复选框
		CB1=(CheckBox)rootView.findViewById(R.id.checkbox1);
		CB2=(CheckBox)rootView.findViewById(R.id.checkbox2);
		CB3=(CheckBox)rootView.findViewById(R.id.checkbox3);
		
		
		
		
     
		// 为取消按钮的单击事件绑定事件监听器
		bnCancel.setOnClickListener(new HomeListener(getActivity()));
		//添加按钮
		bnAdd.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 输入校验
				if (validate())
				{
					
					try
					{
						//Toast.makeText(getActivity(), "您点击"+SAvail_time+SPay+SKind+SSupport_need+SLocation, Toast.LENGTH_SHORT).show();
						// 添加物品种类
						String result =addPost(SKind, SPay);

						if( result.substring(9,10).equals("1") )
							// 使用对话框来显示添加结果
							DialogUtil.showDialog(getActivity(),"添加成功！", true);
						else
							DialogUtil.showDialog(getActivity(),"添加失败！", false);
					}
					catch (Exception e)
					{
						DialogUtil.showDialog(getActivity()
							, "服务器响应异常，请稍后再试！" , false);
						e.printStackTrace();
					}
				}
			}
		});
		
		
		//种类
		post_kind.setOnClickListener(new OnClickListener(){							
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 
				// 创建PopupMenu对象
				popup =new PopupMenu(getActivity(), v);	
				
				// 将R.menu.popup_menu菜单资源加载到popup菜单中
				getActivity().getMenuInflater().inflate(R.menu.kindlist,popup.getMenu());
				// 为popup菜单的菜单项单击事件绑定事件监听器
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClick(MenuItem item)
					{
						switch(item.getItemId())
						{
							default:
								SKind=item.getTitle().toString();
						}						
						return true;					
					}
				});
				popup.show();}

		});
		//报酬方式
		payment.setOnClickListener(new OnClickListener(){							
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 创建PopupMenu对象
				popup =new PopupMenu(getActivity(), v);	
				popup.getMenu().add(1, 1, 1, "有偿");//add(groupId, itemId, order, title)
				popup.getMenu().add(1, 2, 2, "无偿");
				// 将R.menu.popup_menu菜单资源加载到popup菜单中
				//getActivity().getMenuInflater().inflate(R.menu.kindlist,popup.getMenu());
				// 为popup菜单的菜单项单击事件绑定事件监听器
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClick(MenuItem item)
					{
						switch(item.getItemId())
						{
						case 1:
							//报酬一栏的显示控制布局
							payment1=(LinearLayout)rootView.findViewById(R.id.payment1);
							payment1.setVisibility(View.VISIBLE);
							SPay=item.getTitle().toString();
							break;
						case 2:
							//报酬一栏的显示控制布局
							payment1=(LinearLayout)rootView.findViewById(R.id.payment1);
							payment1.setVisibility(View.GONE);
							SPay=item.getTitle().toString();
							break;
						default:
							SPay=item.getTitle().toString();
								
						}						
						return true;					
					}
				});
				popup.show();
			}
			});
		//供？求
		support_need.setOnClickListener(new OnClickListener(){							
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 创建PopupMenu对象
				popup =new PopupMenu(getActivity(), v);								
				popup.getMenu().add(1, 1, 1, "供");
				popup.getMenu().add(1, 2, 2, "求");
				// 为popup菜单的菜单项单击事件绑定事件监听器
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClick(MenuItem item)
					{
						switch(item.getItemId())
						{
							default:
								SSupport_need=item.getTitle().toString();
						}						
						return true;					
					}
				});
				popup.show();
			}
			});
		location.setOnClickListener(new OnClickListener(){							
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 创建PopupMenu对象
				popup =new PopupMenu(getActivity(), v);								
		
			
				popup.getMenu().add(1, 1, 1, "全国");//add(groupId, itemId, order, title)
				popup.getMenu().add(1, 2, 2, "同城");
				popup.getMenu().add(1, 3, 3, "自定义");

				// 为popup菜单的菜单项单击事件绑定事件监听器
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClick(MenuItem item)
					{
						switch(item.getItemId())
						{
					
						case 3:
							//调用地图
							Intent intent=new Intent(getActivity(),LocationActivity.class);
							intent.putExtra("status", 1);
							startActivityForResult(intent,0);
//						
							break;
						default:
							SLocation=item.getTitle().toString();
						}						
						return true;					
					}

				
				});
				popup.show();
			}
			});
		avail_time.setOnClickListener(new OnClickListener(){							
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 创建PopupMenu对象
				popup =new PopupMenu(getActivity(), v);	
				
				// 将R.menu.popup_menu菜单资源加载到popup菜单中
				getActivity().getMenuInflater().inflate(R.menu.avail_time,popup.getMenu());
				// 为popup菜单的菜单项单击事件绑定事件监听器
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClick(MenuItem item)
					{
						switch(item.getItemId())
						{
							default:
								SAvail_time=item.getTitle().toString();
						}						
						return true;					
					}
				});
				popup.show();
			}
			});
		
		return rootView;
	}

	protected String addPost(String sKind2, String sPay2) throws Exception {
		// TODO Auto-generated method stub
		
		//顶部5个选项
		String SKind=null,SPay=null,SSupport_need=null,SAvail_time=null;
		//关于帖子 输入文本

		EditText itemRemark=null;


		
		// 使用Map封装请求参数
				Map<String , String> map = new HashMap<String, String>();
				map.put("author_id" , "2");//用户ID
				map.put("author_nickname" , "zzy");//用户ID
				map.put("category1" ,"test");
				map.put("title" , itemName.getText().toString());
				map.put("phone", contact.getText().toString());
				
				map.put("content" , itemDesc.getText().toString());
				map.put("publish_time",(new Timestamp(System.currentTimeMillis()).toString()));
				
				//定位信息country,province,city,street,locale,area;
				
				map.put("country",country);
				map.put("province",province);
				map.put("city", city);
				//map.put("street", street);
				map.put("locale", locale);
				map.put("area", area);
				map.put("lng_lat", SLocation);
				
				map.put("paymethod", "0");
				
				map.put("end_status", "0");
				map.put("require_count", "0");
				map.put("enroll_count", "0");
				map.put("sign_count", "0");
				map.put("hot_status", "0");
				map.put("push_top_status", "0");
						
				
				// 定义发送请求的URL
				String url = HttpUtil.BASE_URL + "job/publishJob.php";
				// 发送请求
				return HttpUtil.postRequest(url , map);
	}

	// 对用户输入的种类名称进行校验
	private boolean validate()
	{
	//顶部五个选项检查
		if(	SKind==null){
			DialogUtil.showDialog(getActivity() , "类别  未选！" , false);
			post_kind.callOnClick();
			return false;
		}
		if(	SPay==null){
			DialogUtil.showDialog(getActivity() , "报酬方式  未选！" , false);
			payment.callOnClick();
			return false;
		}
		if(	SSupport_need==null){
			DialogUtil.showDialog(getActivity() , "供？求  未选！" , false);
			support_need.callOnClick();
			return false;
		}
		if(	SLocation==null){
			DialogUtil.showDialog(getActivity() , "地理位置 未选！" , false);
			location.callOnClick();
			return false;
		}
		if(	SAvail_time==null){
			DialogUtil.showDialog(getActivity() , "有效时间 未选！" , false);
			avail_time.callOnClick();
			return false;
		}
		
		//帖子正文输入文本检查


		
		if(	itemName.getText().toString().trim().equals("")){
			DialogUtil.showDialog(getActivity() , "帖子名称必填！" , false);
			itemName.callOnClick();
			return false;
		}
		if(	itemRemark.getText().toString().trim().equals("")){
			DialogUtil.showDialog(getActivity() , "帖子标签必填！" , false);
			itemRemark.callOnClick();
			return false;
		}
		if(	contact.getText().toString().trim().equals("")){
			DialogUtil.showDialog(getActivity() , "您的联系方式必填！" , false);
			contact.callOnClick();
			return false;
		}
		if(	itemDesc.getText().toString().trim().equals("")){
			DialogUtil.showDialog(getActivity() , "帖子描述必填！" , false);
			itemDesc.callOnClick();
			return false;
		}
		
		
		String name1 = post_kind.getText().toString().trim();

		
		if (name1.equals(""))
		{
			DialogUtil.showDialog(getActivity() , "种类名称是必填项！" , false);
			return false;
		}
		
		return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==0&&resultCode==0){
			DialogUtil.showDialog(getActivity()
					, "success get coordinate!", false);
			 mTarget=new LatLng(data.getDoubleExtra("lat", 0),data.getDoubleExtra("lng", 0));
			 SLocation=mTarget.longitude+"#"+mTarget.latitude;
				country=data.getStringExtra("country");
				province=data.getStringExtra("province");
				city=data.getStringExtra("city");
				street=data.getStringExtra("street");
				locale=data.getStringExtra("locale");//区
				area=data.getStringExtra("area");
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void testmethod(){
		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		
	}
}
