package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ExerciseService;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.service.SignUpSateService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class SignUpSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public SignUpSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.signup_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_exerciseObj = (TextView)convertView.findViewById(R.id.tv_exerciseObj);
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_signUpTime = (TextView)convertView.findViewById(R.id.tv_signUpTime);
	  holder.tv_signUpState = (TextView)convertView.findViewById(R.id.tv_signUpState);
	  /*设置各个控件的展示内容*/
	  holder.tv_exerciseObj.setText("团队活动：" + (new ExerciseService()).GetExercise(Integer.parseInt(mData.get(position).get("exerciseObj").toString())).getExerciseName());
	  holder.tv_userObj.setText("报名用户：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
	  holder.tv_signUpTime.setText("报名时间：" + mData.get(position).get("signUpTime").toString());
	  holder.tv_signUpState.setText("审核状态：" + (new SignUpSateService()).GetSignUpSate(Integer.parseInt(mData.get(position).get("signUpState").toString())).getStateName());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_exerciseObj;
    	TextView tv_userObj;
    	TextView tv_signUpTime;
    	TextView tv_signUpState;
    }
} 
