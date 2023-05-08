package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Team;

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
public class TeamQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明用户名输入框
	private EditText ET_teamUserName;
	// 声明电子邮箱输入框
	private EditText ET_email;
	// 声明志愿团体名称输入框
	private EditText ET_teamName;
	// 声明所属院校输入框
	private EditText ET_shoolName;
	// 声明联络团体输入框
	private EditText ET_contactGroup;
	// 声明主管单位输入框
	private EditText ET_mainUnit;
	// 声明区域输入框
	private EditText ET_area;
	// 声明联系人电话输入框
	private EditText ET_telephone;
	// 声明负责人姓名输入框
	private EditText ET_chargeMan;
	/*查询过滤条件保存到这个对象中*/
	private Team queryConditionTeam = new Team();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.team_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置团队查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_teamUserName = (EditText) findViewById(R.id.ET_teamUserName);
		ET_email = (EditText) findViewById(R.id.ET_email);
		ET_teamName = (EditText) findViewById(R.id.ET_teamName);
		ET_shoolName = (EditText) findViewById(R.id.ET_shoolName);
		ET_contactGroup = (EditText) findViewById(R.id.ET_contactGroup);
		ET_mainUnit = (EditText) findViewById(R.id.ET_mainUnit);
		ET_area = (EditText) findViewById(R.id.ET_area);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_chargeMan = (EditText) findViewById(R.id.ET_chargeMan);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionTeam.setTeamUserName(ET_teamUserName.getText().toString());
					queryConditionTeam.setEmail(ET_email.getText().toString());
					queryConditionTeam.setTeamName(ET_teamName.getText().toString());
					queryConditionTeam.setShoolName(ET_shoolName.getText().toString());
					queryConditionTeam.setContactGroup(ET_contactGroup.getText().toString());
					queryConditionTeam.setMainUnit(ET_mainUnit.getText().toString());
					queryConditionTeam.setArea(ET_area.getText().toString());
					queryConditionTeam.setTelephone(ET_telephone.getText().toString());
					queryConditionTeam.setChargeMan(ET_chargeMan.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionTeam", queryConditionTeam);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
