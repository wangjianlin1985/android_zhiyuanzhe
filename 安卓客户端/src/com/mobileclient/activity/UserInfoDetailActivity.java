package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class UserInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明用户名控件
	private TextView TV_user_name;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明邮箱控件
	private TextView TV_email;
	// 声明姓名控件
	private TextView TV_name;
	// 声明性别控件
	private TextView TV_sex;
	// 声明用户照片图片框
	private ImageView iv_userPhoto;
	// 声明学校学院控件
	private TextView TV_schoolName;
	// 声明年级专业控件
	private TextView TV_specialInfo;
	// 声明民族控件
	private TextView TV_nation;
	// 声明政治面貌控件
	private TextView TV_politicalStatus;
	// 声明出生日期控件
	private TextView TV_birthday;
	// 声明证件号码控件
	private TextView TV_cardNumber;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明联系地址控件
	private TextView TV_address;
	// 声明有兴趣的项目控件
	private TextView TV_interest;
	// 声明个人介绍控件
	private TextView TV_introduce;
	/* 要保存的用户信息 */
	UserInfo userInfo = new UserInfo(); 
	/* 用户管理业务逻辑层 */
	private UserInfoService userInfoService = new UserInfoService();
	private String user_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.userinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看用户详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_user_name = (TextView) findViewById(R.id.TV_user_name);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_email = (TextView) findViewById(R.id.TV_email);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		iv_userPhoto = (ImageView) findViewById(R.id.iv_userPhoto); 
		TV_schoolName = (TextView) findViewById(R.id.TV_schoolName);
		TV_specialInfo = (TextView) findViewById(R.id.TV_specialInfo);
		TV_nation = (TextView) findViewById(R.id.TV_nation);
		TV_politicalStatus = (TextView) findViewById(R.id.TV_politicalStatus);
		TV_birthday = (TextView) findViewById(R.id.TV_birthday);
		TV_cardNumber = (TextView) findViewById(R.id.TV_cardNumber);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_address = (TextView) findViewById(R.id.TV_address);
		TV_interest = (TextView) findViewById(R.id.TV_interest);
		TV_introduce = (TextView) findViewById(R.id.TV_introduce);
		Bundle extras = this.getIntent().getExtras();
		user_name = extras.getString("user_name");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				UserInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    userInfo = userInfoService.GetUserInfo(user_name); 
		this.TV_user_name.setText(userInfo.getUser_name());
		this.TV_password.setText(userInfo.getPassword());
		this.TV_email.setText(userInfo.getEmail());
		this.TV_name.setText(userInfo.getName());
		this.TV_sex.setText(userInfo.getSex());
		byte[] userPhoto_data = null;
		try {
			// 获取图片数据
			userPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + userInfo.getUserPhoto());
			Bitmap userPhoto = BitmapFactory.decodeByteArray(userPhoto_data, 0,userPhoto_data.length);
			this.iv_userPhoto.setImageBitmap(userPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_schoolName.setText(userInfo.getSchoolName());
		this.TV_specialInfo.setText(userInfo.getSpecialInfo());
		this.TV_nation.setText(userInfo.getNation());
		this.TV_politicalStatus.setText(userInfo.getPoliticalStatus());
		Date birthday = new Date(userInfo.getBirthday().getTime());
		String birthdayStr = (birthday.getYear() + 1900) + "-" + (birthday.getMonth()+1) + "-" + birthday.getDate();
		this.TV_birthday.setText(birthdayStr);
		this.TV_cardNumber.setText(userInfo.getCardNumber());
		this.TV_telephone.setText(userInfo.getTelephone());
		this.TV_address.setText(userInfo.getAddress());
		this.TV_interest.setText(userInfo.getInterest());
		this.TV_introduce.setText(userInfo.getIntroduce());
	} 
}
