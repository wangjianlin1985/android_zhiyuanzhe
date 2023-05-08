package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.SignUp;
import com.mobileclient.service.SignUpService;
import com.mobileclient.domain.Exercise;
import com.mobileclient.service.ExerciseService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.SignUpSate;
import com.mobileclient.service.SignUpSateService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUpEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明报名idTextView
	private TextView TV_signUpId;
	// 声明团队活动下拉框
	private Spinner spinner_exerciseObj;
	private ArrayAdapter<String> exerciseObj_adapter;
	private static  String[] exerciseObj_ShowText  = null;
	private List<Exercise> exerciseList = null;
	/*团队活动管理业务逻辑层*/
	private ExerciseService exerciseService = new ExerciseService();
	// 声明报名用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*报名用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明报名时间输入框
	private EditText ET_signUpTime;
	// 声明审核状态下拉框
	private Spinner spinner_signUpState;
	private ArrayAdapter<String> signUpState_adapter;
	private static  String[] signUpState_ShowText  = null;
	private List<SignUpSate> signUpSateList = null;
	/*审核状态管理业务逻辑层*/
	private SignUpSateService signUpSateService = new SignUpSateService();
	protected String carmera_path;
	/*要保存的活动报名信息*/
	SignUp signUp = new SignUp();
	/*活动报名管理业务逻辑层*/
	private SignUpService signUpService = new SignUpService();

	private int signUpId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.signup_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑活动报名信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_signUpId = (TextView) findViewById(R.id.TV_signUpId);
		spinner_exerciseObj = (Spinner) findViewById(R.id.Spinner_exerciseObj);
		// 获取所有的团队活动
		try {
			exerciseList = exerciseService.QueryExercise(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int exerciseCount = exerciseList.size();
		exerciseObj_ShowText = new String[exerciseCount];
		for(int i=0;i<exerciseCount;i++) { 
			exerciseObj_ShowText[i] = exerciseList.get(i).getExerciseName();
		}
		// 将可选内容与ArrayAdapter连接起来
		exerciseObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, exerciseObj_ShowText);
		// 设置图书类别下拉列表的风格
		exerciseObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_exerciseObj.setAdapter(exerciseObj_adapter);
		// 添加事件Spinner事件监听
		spinner_exerciseObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				signUp.setExerciseObj(exerciseList.get(arg2).getExerciseId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_exerciseObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的报名用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置图书类别下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				signUp.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_signUpTime = (EditText) findViewById(R.id.ET_signUpTime);
		spinner_signUpState = (Spinner) findViewById(R.id.Spinner_signUpState);
		// 获取所有的审核状态
		try {
			signUpSateList = signUpSateService.QuerySignUpSate(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int signUpSateCount = signUpSateList.size();
		signUpState_ShowText = new String[signUpSateCount];
		for(int i=0;i<signUpSateCount;i++) { 
			signUpState_ShowText[i] = signUpSateList.get(i).getStateName();
		}
		// 将可选内容与ArrayAdapter连接起来
		signUpState_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, signUpState_ShowText);
		// 设置图书类别下拉列表的风格
		signUpState_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_signUpState.setAdapter(signUpState_adapter);
		// 添加事件Spinner事件监听
		spinner_signUpState.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				signUp.setSignUpState(signUpSateList.get(arg2).getStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_signUpState.setVisibility(View.VISIBLE);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		signUpId = extras.getInt("signUpId");
		/*单击修改活动报名按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取报名时间*/ 
					if(ET_signUpTime.getText().toString().equals("")) {
						Toast.makeText(SignUpEditActivity.this, "报名时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_signUpTime.setFocusable(true);
						ET_signUpTime.requestFocus();
						return;	
					}
					signUp.setSignUpTime(ET_signUpTime.getText().toString());
					/*调用业务逻辑层上传活动报名信息*/
					SignUpEditActivity.this.setTitle("正在更新活动报名信息，稍等...");
					String result = signUpService.UpdateSignUp(signUp);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    signUp = signUpService.GetSignUp(signUpId);
		this.TV_signUpId.setText(signUpId+"");
		for (int i = 0; i < exerciseList.size(); i++) {
			if (signUp.getExerciseObj() == exerciseList.get(i).getExerciseId()) {
				this.spinner_exerciseObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < userInfoList.size(); i++) {
			if (signUp.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_signUpTime.setText(signUp.getSignUpTime());
		for (int i = 0; i < signUpSateList.size(); i++) {
			if (signUp.getSignUpState() == signUpSateList.get(i).getStateId()) {
				this.spinner_signUpState.setSelection(i);
				break;
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
