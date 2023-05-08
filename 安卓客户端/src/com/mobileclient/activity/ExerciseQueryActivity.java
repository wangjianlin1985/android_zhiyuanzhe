package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Exercise;
import com.mobileclient.domain.Team;
import com.mobileclient.service.TeamService;

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
public class ExerciseQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明活动名称输入框
	private EditText ET_exerciseName;
	// 活动日期控件
	private DatePicker dp_exerciseDate;
	private CheckBox cb_exerciseDate;
	// 声明活动团队下拉框
	private Spinner spinner_teamObj;
	private ArrayAdapter<String> teamObj_adapter;
	private static  String[] teamObj_ShowText  = null;
	private List<Team> teamList = null; 
	/*团队管理业务逻辑层*/
	private TeamService teamService = new TeamService();
	/*查询过滤条件保存到这个对象中*/
	private Exercise queryConditionExercise = new Exercise();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.exercise_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置活动查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_exerciseName = (EditText) findViewById(R.id.ET_exerciseName);
		dp_exerciseDate = (DatePicker) findViewById(R.id.dp_exerciseDate);
		cb_exerciseDate = (CheckBox) findViewById(R.id.cb_exerciseDate);
		spinner_teamObj = (Spinner) findViewById(R.id.Spinner_teamObj);
		// 获取所有的团队
		try {
			teamList = teamService.QueryTeam(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int teamCount = teamList.size();
		teamObj_ShowText = new String[teamCount+1];
		teamObj_ShowText[0] = "不限制";
		for(int i=1;i<=teamCount;i++) { 
			teamObj_ShowText[i] = teamList.get(i-1).getTeamName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		teamObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, teamObj_ShowText);
		// 设置活动团队下拉列表的风格
		teamObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_teamObj.setAdapter(teamObj_adapter);
		// 添加事件Spinner事件监听
		spinner_teamObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionExercise.setTeamObj(teamList.get(arg2-1).getTeamUserName()); 
				else
					queryConditionExercise.setTeamObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_teamObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionExercise.setExerciseName(ET_exerciseName.getText().toString());
					if(cb_exerciseDate.isChecked()) {
						/*获取活动日期*/
						Date exerciseDate = new Date(dp_exerciseDate.getYear()-1900,dp_exerciseDate.getMonth(),dp_exerciseDate.getDayOfMonth());
						queryConditionExercise.setExerciseDate(new Timestamp(exerciseDate.getTime()));
					} else {
						queryConditionExercise.setExerciseDate(null);
					} 
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionExercise", queryConditionExercise);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
