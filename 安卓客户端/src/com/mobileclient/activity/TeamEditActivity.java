package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class TeamEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明用户名TextView
	private TextView TV_teamUserName;
	// 声明登录密码输入框
	private EditText ET_password;
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
	// 声明详细地址输入框
	private EditText ET_address;
	// 声明邮编输入框
	private EditText ET_postCode;
	// 出版成立日期控件
	private DatePicker dp_birthDate;
	// 声明志愿者人数输入框
	private EditText ET_personNum;
	// 声明联系人电话输入框
	private EditText ET_telephone;
	// 声明负责人姓名输入框
	private EditText ET_chargeMan;
	// 声明负责人身份证输入框
	private EditText ET_cardNumber;
	protected String carmera_path;
	/*要保存的团队信息*/
	Team team = new Team();
	/*团队管理业务逻辑层*/
	private TeamService teamService = new TeamService();

	private String teamUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.team_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑团队信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_teamUserName = (TextView) findViewById(R.id.TV_teamUserName);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_email = (EditText) findViewById(R.id.ET_email);
		ET_teamName = (EditText) findViewById(R.id.ET_teamName);
		ET_shoolName = (EditText) findViewById(R.id.ET_shoolName);
		ET_contactGroup = (EditText) findViewById(R.id.ET_contactGroup);
		ET_mainUnit = (EditText) findViewById(R.id.ET_mainUnit);
		ET_area = (EditText) findViewById(R.id.ET_area);
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_postCode = (EditText) findViewById(R.id.ET_postCode);
		dp_birthDate = (DatePicker)this.findViewById(R.id.dp_birthDate);
		ET_personNum = (EditText) findViewById(R.id.ET_personNum);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_chargeMan = (EditText) findViewById(R.id.ET_chargeMan);
		ET_cardNumber = (EditText) findViewById(R.id.ET_cardNumber);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		teamUserName = extras.getString("teamUserName");
		/*单击修改团队按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(TeamEditActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					team.setPassword(ET_password.getText().toString());
					/*验证获取电子邮箱*/ 
					if(ET_email.getText().toString().equals("")) {
						Toast.makeText(TeamEditActivity.this, "电子邮箱输入不能为空!", Toast.LENGTH_LONG).show();
						ET_email.setFocusable(true);
						ET_email.requestFocus();
						return;	
					}
					team.setEmail(ET_email.getText().toString());
					/*验证获取志愿团体名称*/ 
					if(ET_teamName.getText().toString().equals("")) {
						Toast.makeText(TeamEditActivity.this, "志愿团体名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_teamName.setFocusable(true);
						ET_teamName.requestFocus();
						return;	
					}
					team.setTeamName(ET_teamName.getText().toString());
					/*验证获取所属院校*/ 
					if(ET_shoolName.getText().toString().equals("")) {
						Toast.makeText(TeamEditActivity.this, "所属院校输入不能为空!", Toast.LENGTH_LONG).show();
						ET_shoolName.setFocusable(true);
						ET_shoolName.requestFocus();
						return;	
					}
					team.setShoolName(ET_shoolName.getText().toString());
					/*验证获取联络团体*/ 
					if(ET_contactGroup.getText().toString().equals("")) {
						Toast.makeText(TeamEditActivity.this, "联络团体输入不能为空!", Toast.LENGTH_LONG).show();
						ET_contactGroup.setFocusable(true);
						ET_contactGroup.requestFocus();
						return;	
					}
					team.setContactGroup(ET_contactGroup.getText().toString());
					/*验证获取主管单位*/ 
					if(ET_mainUnit.getText().toString().equals("")) {
						Toast.makeText(TeamEditActivity.this, "主管单位输入不能为空!", Toast.LENGTH_LONG).show();
						ET_mainUnit.setFocusable(true);
						ET_mainUnit.requestFocus();
						return;	
					}
					team.setMainUnit(ET_mainUnit.getText().toString());
					/*验证获取区域*/ 
					if(ET_area.getText().toString().equals("")) {
						Toast.makeText(TeamEditActivity.this, "区域输入不能为空!", Toast.LENGTH_LONG).show();
						ET_area.setFocusable(true);
						ET_area.requestFocus();
						return;	
					}
					team.setArea(ET_area.getText().toString());
					/*验证获取详细地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(TeamEditActivity.this, "详细地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					team.setAddress(ET_address.getText().toString());
					/*验证获取邮编*/ 
					if(ET_postCode.getText().toString().equals("")) {
						Toast.makeText(TeamEditActivity.this, "邮编输入不能为空!", Toast.LENGTH_LONG).show();
						ET_postCode.setFocusable(true);
						ET_postCode.requestFocus();
						return;	
					}
					team.setPostCode(ET_postCode.getText().toString());
					/*获取出版日期*/
					Date birthDate = new Date(dp_birthDate.getYear()-1900,dp_birthDate.getMonth(),dp_birthDate.getDayOfMonth());
					team.setBirthDate(new Timestamp(birthDate.getTime()));
					/*验证获取志愿者人数*/ 
					if(ET_personNum.getText().toString().equals("")) {
						Toast.makeText(TeamEditActivity.this, "志愿者人数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_personNum.setFocusable(true);
						ET_personNum.requestFocus();
						return;	
					}
					team.setPersonNum(Integer.parseInt(ET_personNum.getText().toString()));
					/*验证获取联系人电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(TeamEditActivity.this, "联系人电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					team.setTelephone(ET_telephone.getText().toString());
					/*验证获取负责人姓名*/ 
					if(ET_chargeMan.getText().toString().equals("")) {
						Toast.makeText(TeamEditActivity.this, "负责人姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_chargeMan.setFocusable(true);
						ET_chargeMan.requestFocus();
						return;	
					}
					team.setChargeMan(ET_chargeMan.getText().toString());
					/*验证获取负责人身份证*/ 
					if(ET_cardNumber.getText().toString().equals("")) {
						Toast.makeText(TeamEditActivity.this, "负责人身份证输入不能为空!", Toast.LENGTH_LONG).show();
						ET_cardNumber.setFocusable(true);
						ET_cardNumber.requestFocus();
						return;	
					}
					team.setCardNumber(ET_cardNumber.getText().toString());
					/*调用业务逻辑层上传团队信息*/
					TeamEditActivity.this.setTitle("正在更新团队信息，稍等...");
					String result = teamService.UpdateTeam(team);
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
	    team = teamService.GetTeam(teamUserName);
		this.TV_teamUserName.setText(teamUserName);
		this.ET_password.setText(team.getPassword());
		this.ET_email.setText(team.getEmail());
		this.ET_teamName.setText(team.getTeamName());
		this.ET_shoolName.setText(team.getShoolName());
		this.ET_contactGroup.setText(team.getContactGroup());
		this.ET_mainUnit.setText(team.getMainUnit());
		this.ET_area.setText(team.getArea());
		this.ET_address.setText(team.getAddress());
		this.ET_postCode.setText(team.getPostCode());
		Date birthDate = new Date(team.getBirthDate().getTime());
		this.dp_birthDate.init(birthDate.getYear() + 1900,birthDate.getMonth(), birthDate.getDate(), null);
		this.ET_personNum.setText(team.getPersonNum() + "");
		this.ET_telephone.setText(team.getTelephone());
		this.ET_chargeMan.setText(team.getChargeMan());
		this.ET_cardNumber.setText(team.getCardNumber());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
