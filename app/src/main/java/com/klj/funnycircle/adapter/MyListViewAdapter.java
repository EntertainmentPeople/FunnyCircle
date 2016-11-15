package com.klj.funnycircle.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.klj.funnycircle.R;
import com.klj.funnycircle.entity.ItemInfo;
import com.klj.funnycircle.utils.Utils;

import java.util.List;

import io.vov.vitamio.widget.VideoView;

/**
 * 自定义一个ListView适配器
 */
public class MyListViewAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;   //上下文
    List<ItemInfo> lists;   //数据源
    MediaPlayer mediaPlayer; //视频播放控件
    ViewHolder mViewHolder;  //模板文件
    String currentUrl;       //当前应该播放的Url地址
    int currentPosition;     //当前播放的位置
    public MyListViewAdapter(Context context,List<ItemInfo> lists){
        this.lists=lists;
        this.context=context;
        if (context instanceof MyListViewAdapterCallBack){
            myListViewAdapterCallBack= (MyListViewAdapterCallBack) context;
        }
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            vh=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_list_item,parent,false);
            findListItemView(vh,convertView);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        ItemInfo itemInfo = lists.get(position);
        setValue(vh,itemInfo);

        vh.vVideo.setOnClickListener(this);
        vh.vVideo.setTag(position);

        return convertView;
    }

    /**
     * 设置值
     * @param vh
     * @param itemInfo
     */
    private void setValue(ViewHolder vh, ItemInfo itemInfo) {
        vh.tvTime.setText(Utils.toTransferTime(itemInfo.getCreateTime()));
        vh.tvDescribe.setText(itemInfo.getContent());
        Log.e("--url--",itemInfo.getVideos().get(0).getUrl()+".mp4");
        vh.vVideo.setVideoPath(itemInfo.getVideos().get(0).getUrl()+".mp4");
    }

    /**
     * 找到控件
     * @param vh
     * @param convertView
     */
    private void findListItemView(ViewHolder vh, View convertView) {
        vh.tvTime= (TextView) convertView.findViewById(R.id.tv_list_time);
        vh.tvDescribe= (TextView) convertView.findViewById(R.id.tv_list_describe);
        vh.tvHot= (TextView) convertView.findViewById(R.id.tv_list_hot);
        vh.tvComment= (TextView) convertView.findViewById(R.id.tv_list_comment);
        vh.tvNice= (TextView) convertView.findViewById(R.id.tv_list_nice);
        vh.btnShare= (Button) convertView.findViewById(R.id.btn_list_share);
        vh.vVideo= (VideoView) convertView.findViewById(R.id.vv_list_video);
        vh.ivPic= (ImageView) convertView.findViewById(R.id.iv_list_pic);
        vh.video= (TextView) convertView.findViewById(R.id.tv_list_time);
        vh.picture= (TextView) convertView.findViewById(R.id.tv_list_time);
        vh.godReview= (TextView) convertView.findViewById(R.id.tv_list_time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vv_list_video:
                myListViewAdapterCallBack.click(v,(Integer) v.getTag());
                break;
        }
    }

    /**
     * 模板
     */
    private class ViewHolder{
        public TextView tvTime,tvDescribe,tvHot,tvComment,tvNice;
        public Button btnShare;
        public VideoView vVideo;
        public ImageView ivPic;
        public View video,picture,godReview;
    }

    /**
     * 设置回调接口
     */
    public interface MyListViewAdapterCallBack{
        public void click(View view,int position);
    }

    MyListViewAdapterCallBack myListViewAdapterCallBack=null;

    /**
     * 设置点击监听
     * @param myListViewAdapterCallBack
     */
    public void setOnClickCallBackListner(MyListViewAdapterCallBack myListViewAdapterCallBack){
        this.myListViewAdapterCallBack=myListViewAdapterCallBack;
    }
}
