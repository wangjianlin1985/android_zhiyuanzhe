package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Exercise;
import com.mobileclient.service.ExerciseService;
import com.mobileclient.domain.Team;
import com.mobileclient.service.TeamService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class ExerciseAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明活动名称输入框
	private EditText ET_exerciseName;
	// 出版活动日期控件
	private DatePicker dp_exerciseDate;
	// 声明服务时长输入框
	private EditText ET_serviceTime;
	// 声明活动地点输入框
	private EditText ET_address;
	// 声明参与人数输入框
	private EditText ET_personNum;
	// 声明活动内容输入框
	private EditText ET_content;
	// 声明活动团队下拉框
	private Spinner spinner_teamObj;
	private ArrayAdapter<String> teamObj_adapter;
	private static  String[] teamObj_ShowText  = null;
	private List<Team> teamList = null;
	/*活动团队管理业务逻辑层*/
	private TeamService teamService = new TeamService();
	protected String carmera_path;
	/*要保存的活动信息*/
	Exercise exercise = new Exercise();
	/*活动管理业务逻辑层*/
	private ExerciseService exerciseService = new ExerciseService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.exercise_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加活动");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_exerciseName = (EditText) findViewById(R.id.ET_exerciseName);
		dp_exerciseDate = (DatePicker)this.findViewById(R.id.dp_exerciseDate);
		ET_serviceTime = (EditText) findViewById(R.id.ET_serviceTime);
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_personNum = (EditText) findViewById(R.id.ET_personNum);
		ET_content = (EditText) findViewById(R.id.ET_content);
		spinner_teamObj = (Spinner) findViewById(R.id.Spinner_teamObj);
		// 获取所有的活动团队
		try {
			teamList = teamService.QueryTeam(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int teamCount = teamList.size();
		teamObj_ShowText = new String[teamCount];
		for(int i=0;i<teamCount;i++) { 
			teamObj_ShowText[i] = teamList.get(i).getTeamName();
		}
		// 将可选内容与ArrayAdapter连接起来
		teamObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, teamObj_ShowText);
		// 设置下拉列表的风格
		teamObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_teamObj.setAdapter(teamObj_adapter);
		// 添加事件Spinner事件监听
		spinner_teamObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				exercise.setTeamObj(teamList.get(arg2).getTeamUserName()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_teamObj.setVisibility(View.VISIBLE);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加活动按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取活动名称*/ 
					if(ET_exerciseName.getText().toString().equals("")) {
						Toast.makeText(ExerciseAddActivity.this, "活动名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_exerciseName.setFocusable(true);
						ET_exerciseName.requestFocus();
						return;	
					}
					exercise.setExerciseName(ET_exerciseName.getText().toString());
					/*获取活动日期*/
					Date exerciseDate = new Date(dp_exerciseDate.getYear()-1900,dp_exerciseDate.getMonth(),dp_exerciseDate.getDayOfMonth());
					exercise.setExerciseDate(new Timestamp(exerciseDate.getTime()));
					/*验证获取服务时长*/ 
					if(ET_serviceTime.getText().toString().equals("")) {
						Toast.makeText(ExerciseAddActivity.this, "服务时长输入不能为空!", Toast.LENGTH_LONG).show();
						ET_serviceTime.setFocusable(true);
						ET_serviceTime.requestFocus();
						return;	
					}
					exercise.setServiceTime(ET_serviceTime.getText().toString());
					/*验证获取活动地点*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(ExerciseAddActivity.this, "活动地点输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					exercise.setAddress(ET_address.getText().toString());
					/*验证获取参与人数*/ 
					if(ET_personNum.getText().toString().equals("")) {
						Toast.makeText(ExerciseAddActivity.this, "参与人数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_personNum.setFocusable(true);
						ET_personNum.requestFocus();
						return;	
					}
					exercise.setPersonNum(Integer.parseInt(ET_personNum.getText().toString()));
					/*验证获取活动内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(ExerciseAddActivity.this, "活动内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					exercise.setContent(ET_content.getText().toString());
					/*调用业务逻辑层上传活动信息*/
					ExerciseAddActivity.this.setTitle("正在上传活动信息，稍等...");
					String result = exerciseService.AddExercise(exercise);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
