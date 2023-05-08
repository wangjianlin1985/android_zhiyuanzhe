package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.News;
import com.mobileclient.service.NewsService;
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

public class NewsEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明新闻idTextView
	private TextView TV_newsId;
	// 声明标题输入框
	private EditText ET_title;
	// 出版日期控件
	private DatePicker dp_newsDate;
	// 声明来源输入框
	private EditText ET_comeFrom;
	// 声明内容输入框
	private EditText ET_newsContent;
	protected String carmera_path;
	/*要保存的活动新闻信息*/
	News news = new News();
	/*活动新闻管理业务逻辑层*/
	private NewsService newsService = new NewsService();

	private int newsId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.news_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑活动新闻信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_newsId = (TextView) findViewById(R.id.TV_newsId);
		ET_title = (EditText) findViewById(R.id.ET_title);
		dp_newsDate = (DatePicker)this.findViewById(R.id.dp_newsDate);
		ET_comeFrom = (EditText) findViewById(R.id.ET_comeFrom);
		ET_newsContent = (EditText) findViewById(R.id.ET_newsContent);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		newsId = extras.getInt("newsId");
		/*单击修改活动新闻按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(NewsEditActivity.this, "标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					news.setTitle(ET_title.getText().toString());
					/*获取出版日期*/
					Date newsDate = new Date(dp_newsDate.getYear()-1900,dp_newsDate.getMonth(),dp_newsDate.getDayOfMonth());
					news.setNewsDate(new Timestamp(newsDate.getTime()));
					/*验证获取来源*/ 
					if(ET_comeFrom.getText().toString().equals("")) {
						Toast.makeText(NewsEditActivity.this, "来源输入不能为空!", Toast.LENGTH_LONG).show();
						ET_comeFrom.setFocusable(true);
						ET_comeFrom.requestFocus();
						return;	
					}
					news.setComeFrom(ET_comeFrom.getText().toString());
					/*验证获取内容*/ 
					if(ET_newsContent.getText().toString().equals("")) {
						Toast.makeText(NewsEditActivity.this, "内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_newsContent.setFocusable(true);
						ET_newsContent.requestFocus();
						return;	
					}
					news.setNewsContent(ET_newsContent.getText().toString());
					/*调用业务逻辑层上传活动新闻信息*/
					NewsEditActivity.this.setTitle("正在更新活动新闻信息，稍等...");
					String result = newsService.UpdateNews(news);
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
	    news = newsService.GetNews(newsId);
		this.TV_newsId.setText(newsId+"");
		this.ET_title.setText(news.getTitle());
		Date newsDate = new Date(news.getNewsDate().getTime());
		this.dp_newsDate.init(newsDate.getYear() + 1900,newsDate.getMonth(), newsDate.getDate(), null);
		this.ET_comeFrom.setText(news.getComeFrom());
		this.ET_newsContent.setText(news.getNewsContent());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
