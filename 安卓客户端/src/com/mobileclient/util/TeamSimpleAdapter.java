package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

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

public class TeamSimpleAdapter extends SimpleAdapter { 
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

    public TeamSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.team_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_teamUserName = (TextView)convertView.findViewById(R.id.tv_teamUserName);
	  holder.tv_email = (TextView)convertView.findViewById(R.id.tv_email);
	  holder.tv_teamName = (TextView)convertView.findViewById(R.id.tv_teamName);
	  holder.tv_shoolName = (TextView)convertView.findViewById(R.id.tv_shoolName);
	  holder.tv_contactGroup = (TextView)convertView.findViewById(R.id.tv_contactGroup);
	  holder.tv_mainUnit = (TextView)convertView.findViewById(R.id.tv_mainUnit);
	  holder.tv_area = (TextView)convertView.findViewById(R.id.tv_area);
	  holder.tv_postCode = (TextView)convertView.findViewById(R.id.tv_postCode);
	  holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
	  holder.tv_chargeMan = (TextView)convertView.findViewById(R.id.tv_chargeMan);
	  /*设置各个控件的展示内容*/
	  holder.tv_teamUserName.setText("用户名：" + mData.get(position).get("teamUserName").toString());
	  holder.tv_email.setText("电子邮箱：" + mData.get(position).get("email").toString());
	  holder.tv_teamName.setText("志愿团体名称：" + mData.get(position).get("teamName").toString());
	  holder.tv_shoolName.setText("所属院校：" + mData.get(position).get("shoolName").toString());
	  holder.tv_contactGroup.setText("联络团体：" + mData.get(position).get("contactGroup").toString());
	  holder.tv_mainUnit.setText("主管单位：" + mData.get(position).get("mainUnit").toString());
	  holder.tv_area.setText("区域：" + mData.get(position).get("area").toString());
	  holder.tv_postCode.setText("邮编：" + mData.get(position).get("postCode").toString());
	  holder.tv_telephone.setText("联系人电话：" + mData.get(position).get("telephone").toString());
	  holder.tv_chargeMan.setText("负责人姓名：" + mData.get(position).get("chargeMan").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_teamUserName;
    	TextView tv_email;
    	TextView tv_teamName;
    	TextView tv_shoolName;
    	TextView tv_contactGroup;
    	TextView tv_mainUnit;
    	TextView tv_area;
    	TextView tv_postCode;
    	TextView tv_telephone;
    	TextView tv_chargeMan;
    }
} 
