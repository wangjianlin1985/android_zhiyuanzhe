package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.SignUp;
import com.mobileclient.service.SignUpService;
import com.mobileclient.domain.Exercise;
import com.mobileclient.service.ExerciseService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.SignUpSate;
import com.mobileclient.service.SignUpSateService;
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
public class SignUpDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明报名id控件
	private TextView TV_signUpId;
	// 声明团队活动控件
	private TextView TV_exerciseObj;
	// 声明报名用户控件
	private TextView TV_userObj;
	// 声明报名时间控件
	private TextView TV_signUpTime;
	// 声明审核状态控件
	private TextView TV_signUpState;
	/* 要保存的活动报名信息 */
	SignUp signUp = new SignUp(); 
	/* 活动报名管理业务逻辑层 */
	private SignUpService signUpService = new SignUpService();
	private ExerciseService exerciseService = new ExerciseService();
	private UserInfoService userInfoService = new UserInfoService();
	private SignUpSateService signUpSateService = new SignUpSateService();
	private int signUpId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.signup_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看活动报名详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_signUpId = (TextView) findViewById(R.id.TV_signUpId);
		TV_exerciseObj = (TextView) findViewById(R.id.TV_exerciseObj);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_signUpTime = (TextView) findViewById(R.id.TV_signUpTime);
		TV_signUpState = (TextView) findViewById(R.id.TV_signUpState);
		Bundle extras = this.getIntent().getExtras();
		signUpId = extras.getInt("signUpId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SignUpDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    signUp = signUpService.GetSignUp(signUpId); 
		this.TV_signUpId.setText(signUp.getSignUpId() + "");
		Exercise exerciseObj = exerciseService.GetExercise(signUp.getExerciseObj());
		this.TV_exerciseObj.setText(exerciseObj.getExerciseName());
		UserInfo userObj = userInfoService.GetUserInfo(signUp.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_signUpTime.setText(signUp.getSignUpTime());
		SignUpSate signUpState = signUpSateService.GetSignUpSate(signUp.getSignUpState());
		this.TV_signUpState.setText(signUpState.getStateName());
	} 
}
