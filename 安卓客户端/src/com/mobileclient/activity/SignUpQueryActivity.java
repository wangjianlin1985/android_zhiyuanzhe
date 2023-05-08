package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.SignUp;
import com.mobileclient.domain.Exercise;
import com.mobileclient.service.ExerciseService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.SignUpSate;
import com.mobileclient.service.SignUpSateService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class SignUpQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明团队活动下拉框
	private Spinner spinner_exerciseObj;
	private ArrayAdapter<String> exerciseObj_adapter;
	private static  String[] exerciseObj_ShowText  = null;
	private List<Exercise> exerciseList = null; 
	/*活动管理业务逻辑层*/
	private ExerciseService exerciseService = new ExerciseService();
	// 声明报名用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明审核状态下拉框
	private Spinner spinner_signUpState;
	private ArrayAdapter<String> signUpState_adapter;
	private static  String[] signUpState_ShowText  = null;
	private List<SignUpSate> signUpSateList = null; 
	/*审核状态管理业务逻辑层*/
	private SignUpSateService signUpSateService = new SignUpSateService();
	/*查询过滤条件保存到这个对象中*/
	private SignUp queryConditionSignUp = new SignUp();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.signup_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置活动报名查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_exerciseObj = (Spinner) findViewById(R.id.Spinner_exerciseObj);
		// 获取所有的活动
		try {
			exerciseList = exerciseService.QueryExercise(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int exerciseCount = exerciseList.size();
		exerciseObj_ShowText = new String[exerciseCount+1];
		exerciseObj_ShowText[0] = "不限制";
		for(int i=1;i<=exerciseCount;i++) { 
			exerciseObj_ShowText[i] = exerciseList.get(i-1).getExerciseName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		exerciseObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, exerciseObj_ShowText);
		// 设置团队活动下拉列表的风格
		exerciseObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_exerciseObj.setAdapter(exerciseObj_adapter);
		// 添加事件Spinner事件监听
		spinner_exerciseObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionSignUp.setExerciseObj(exerciseList.get(arg2-1).getExerciseId()); 
				else
					queryConditionSignUp.setExerciseObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_exerciseObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "不限制";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置报名用户下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionSignUp.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionSignUp.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		spinner_signUpState = (Spinner) findViewById(R.id.Spinner_signUpState);
		// 获取所有的审核状态
		try {
			signUpSateList = signUpSateService.QuerySignUpSate(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int signUpSateCount = signUpSateList.size();
		signUpState_ShowText = new String[signUpSateCount+1];
		signUpState_ShowText[0] = "不限制";
		for(int i=1;i<=signUpSateCount;i++) { 
			signUpState_ShowText[i] = signUpSateList.get(i-1).getStateName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		signUpState_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, signUpState_ShowText);
		// 设置审核状态下拉列表的风格
		signUpState_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_signUpState.setAdapter(signUpState_adapter);
		// 添加事件Spinner事件监听
		spinner_signUpState.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionSignUp.setSignUpState(signUpSateList.get(arg2-1).getStateId()); 
				else
					queryConditionSignUp.setSignUpState(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_signUpState.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionSignUp", queryConditionSignUp);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
