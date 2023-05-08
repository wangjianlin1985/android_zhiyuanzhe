package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Exercise;
import com.mobileclient.service.ExerciseService;
import com.mobileclient.domain.Team;
import com.mobileclient.service.TeamService;
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
public class ExerciseDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明活动id控件
	private TextView TV_exerciseId;
	// 声明活动名称控件
	private TextView TV_exerciseName;
	// 声明活动日期控件
	private TextView TV_exerciseDate;
	// 声明服务时长控件
	private TextView TV_serviceTime;
	// 声明活动地点控件
	private TextView TV_address;
	// 声明参与人数控件
	private TextView TV_personNum;
	// 声明活动内容控件
	private TextView TV_content;
	// 声明活动团队控件
	private TextView TV_teamObj;
	/* 要保存的活动信息 */
	Exercise exercise = new Exercise(); 
	/* 活动管理业务逻辑层 */
	private ExerciseService exerciseService = new ExerciseService();
	private TeamService teamService = new TeamService();
	private int exerciseId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.exercise_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看活动详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_exerciseId = (TextView) findViewById(R.id.TV_exerciseId);
		TV_exerciseName = (TextView) findViewById(R.id.TV_exerciseName);
		TV_exerciseDate = (TextView) findViewById(R.id.TV_exerciseDate);
		TV_serviceTime = (TextView) findViewById(R.id.TV_serviceTime);
		TV_address = (TextView) findViewById(R.id.TV_address);
		TV_personNum = (TextView) findViewById(R.id.TV_personNum);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_teamObj = (TextView) findViewById(R.id.TV_teamObj);
		Bundle extras = this.getIntent().getExtras();
		exerciseId = extras.getInt("exerciseId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ExerciseDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    exercise = exerciseService.GetExercise(exerciseId); 
		this.TV_exerciseId.setText(exercise.getExerciseId() + "");
		this.TV_exerciseName.setText(exercise.getExerciseName());
		Date exerciseDate = new Date(exercise.getExerciseDate().getTime());
		String exerciseDateStr = (exerciseDate.getYear() + 1900) + "-" + (exerciseDate.getMonth()+1) + "-" + exerciseDate.getDate();
		this.TV_exerciseDate.setText(exerciseDateStr);
		this.TV_serviceTime.setText(exercise.getServiceTime());
		this.TV_address.setText(exercise.getAddress());
		this.TV_personNum.setText(exercise.getPersonNum() + "");
		this.TV_content.setText(exercise.getContent());
		Team teamObj = teamService.GetTeam(exercise.getTeamObj());
		this.TV_teamObj.setText(teamObj.getTeamName());
	} 
}
