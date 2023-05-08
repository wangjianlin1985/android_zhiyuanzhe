package com.mobileclient.activity;

import java.util.Date;
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
public class TeamDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明用户名控件
	private TextView TV_teamUserName;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明电子邮箱控件
	private TextView TV_email;
	// 声明志愿团体名称控件
	private TextView TV_teamName;
	// 声明所属院校控件
	private TextView TV_shoolName;
	// 声明联络团体控件
	private TextView TV_contactGroup;
	// 声明主管单位控件
	private TextView TV_mainUnit;
	// 声明区域控件
	private TextView TV_area;
	// 声明详细地址控件
	private TextView TV_address;
	// 声明邮编控件
	private TextView TV_postCode;
	// 声明成立日期控件
	private TextView TV_birthDate;
	// 声明志愿者人数控件
	private TextView TV_personNum;
	// 声明联系人电话控件
	private TextView TV_telephone;
	// 声明负责人姓名控件
	private TextView TV_chargeMan;
	// 声明负责人身份证控件
	private TextView TV_cardNumber;
	/* 要保存的团队信息 */
	Team team = new Team(); 
	/* 团队管理业务逻辑层 */
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
		setContentView(R.layout.team_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看团队详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_teamUserName = (TextView) findViewById(R.id.TV_teamUserName);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_email = (TextView) findViewById(R.id.TV_email);
		TV_teamName = (TextView) findViewById(R.id.TV_teamName);
		TV_shoolName = (TextView) findViewById(R.id.TV_shoolName);
		TV_contactGroup = (TextView) findViewById(R.id.TV_contactGroup);
		TV_mainUnit = (TextView) findViewById(R.id.TV_mainUnit);
		TV_area = (TextView) findViewById(R.id.TV_area);
		TV_address = (TextView) findViewById(R.id.TV_address);
		TV_postCode = (TextView) findViewById(R.id.TV_postCode);
		TV_birthDate = (TextView) findViewById(R.id.TV_birthDate);
		TV_personNum = (TextView) findViewById(R.id.TV_personNum);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_chargeMan = (TextView) findViewById(R.id.TV_chargeMan);
		TV_cardNumber = (TextView) findViewById(R.id.TV_cardNumber);
		Bundle extras = this.getIntent().getExtras();
		teamUserName = extras.getString("teamUserName");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TeamDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    team = teamService.GetTeam(teamUserName); 
		this.TV_teamUserName.setText(team.getTeamUserName());
		this.TV_password.setText(team.getPassword());
		this.TV_email.setText(team.getEmail());
		this.TV_teamName.setText(team.getTeamName());
		this.TV_shoolName.setText(team.getShoolName());
		this.TV_contactGroup.setText(team.getContactGroup());
		this.TV_mainUnit.setText(team.getMainUnit());
		this.TV_area.setText(team.getArea());
		this.TV_address.setText(team.getAddress());
		this.TV_postCode.setText(team.getPostCode());
		Date birthDate = new Date(team.getBirthDate().getTime());
		String birthDateStr = (birthDate.getYear() + 1900) + "-" + (birthDate.getMonth()+1) + "-" + birthDate.getDate();
		this.TV_birthDate.setText(birthDateStr);
		this.TV_personNum.setText(team.getPersonNum() + "");
		this.TV_telephone.setText(team.getTelephone());
		this.TV_chargeMan.setText(team.getChargeMan());
		this.TV_cardNumber.setText(team.getCardNumber());
	} 
}
