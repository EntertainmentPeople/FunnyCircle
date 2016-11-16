package com.klj.funnycircle.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.klj.funnycircle.R;
import com.klj.funnycircle.entity.ImageInfo;
import com.klj.funnycircle.entity.ItemInfo;
import com.klj.funnycircle.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

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

    public MyListViewAdapter(Context context, List<ItemInfo> lists) {
        this.lists = lists;
        this.context = context;
        if (context instanceof MyListViewAdapterCallBack) {
            myListViewAdapterCallBack = (MyListViewAdapterCallBack) context;
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
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false);
            findListItemView(vh, convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        ItemInfo itemInfo = lists.get(position);
        setValue(vh, itemInfo);

        setClickListener(vh, position);
        return convertView;
    }

    /**
     * 设置点击事件
     *
     * @param vh
     * @param position
     */
    private void setClickListener(ViewHolder vh, final int position) {
        vh.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListViewAdapterCallBack.click(v, position);
            }
        });
    }

    /**
     * 设置值
     *
     * @param vh
     * @param itemInfo
     */
    private void setValue(ViewHolder vh, ItemInfo itemInfo) {
        vh.tvTime.setText(Utils.toTransferTime(itemInfo.getCreateTime()));
        vh.tvDescribe.setText(itemInfo.getContent());
        if (isVideo(vh, itemInfo)) {
            playVideo(vh, itemInfo);
        } else if (isPicture(vh, itemInfo)) {
            showPicture(vh, itemInfo);
        }
        if (isNiceComment(vh, itemInfo)) {
            showNiceComment(vh, itemInfo);
        }
        vh.tvHot.setText(itemInfo.getHotDegree() + "");
        vh.tvComment.setText(itemInfo.getComment() + "");
        vh.tvNice.setText(itemInfo.getPraise() + "");
    }

    /**
     * 显示神评
     *
     * @param vh
     * @param itemInfo
     */
    private void showNiceComment(ViewHolder vh, ItemInfo itemInfo) {
        vh.lvNiceComment.setAdapter(new MyCommentAdapter(context, itemInfo.getNiceComments()));
    }

    /**
     * 判断是否是神评
     *
     * @param vh
     * @param itemInfo
     * @return
     */
    private boolean isNiceComment(ViewHolder vh, ItemInfo itemInfo) {
        if (null != itemInfo.getNiceComments()) {
            vh.godReview.setVisibility(View.VISIBLE);
            return true;
        } else {
            vh.godReview.setVisibility(View.GONE);
        }
        return false;
    }

    /**
     * 显示图片
     *
     * @param vh
     * @param itemInfo
     */
    private void showPicture(ViewHolder vh, ItemInfo itemInfo) {
        List<ImageInfo> images = itemInfo.getImages();
        for (int i = 0; i < images.size(); i++) {
            Picasso.with(context).load(images.get(i).getThumbUrl()).into(vh.ivPic);
        }
    }

    /**
     * 判断是否是图片
     *
     * @param vh
     * @param itemInfo
     * @return
     */
    private boolean isPicture(ViewHolder vh, ItemInfo itemInfo) {
        if (null != itemInfo.getImages()) {
            vh.picture.setVisibility(View.VISIBLE);
            return true;
        } else {
            vh.picture.setVisibility(View.GONE);
        }
        return false;
    }

    /**
     * 视频播放
     *
     * @param vh
     * @param itemInfo
     */
    private void playVideo(ViewHolder vh, ItemInfo itemInfo) {
        String path = itemInfo.getVideos().get(0).getUrl();
        if (!path.endsWith(".mp4")) {
            path = path + ".mp4";
        }
        vh.vVideo.setUp(path, itemInfo.getContent());
        vh.vVideo.thumbImageView.setImageURI(Uri.parse(itemInfo.getVideos().get(0).getThumbUrl()));
        Picasso.with(context).load(itemInfo.getVideos().get(0).getThumbUrl()).into(vh.vVideo.thumbImageView);
    }

    /**
     * 判断是否是视频
     *
     * @param itemInfo
     * @return
     */
    private boolean isVideo(ViewHolder vh, ItemInfo itemInfo) {
        if (null != itemInfo.getVideos()) {
            vh.video.setVisibility(View.VISIBLE);
            return true;
        } else {
            vh.video.setVisibility(View.GONE);
        }
        return false;
    }

    /**
     * 找到控件
     *
     * @param vh
     * @param convertView
     */
    private void findListItemView(ViewHolder vh, View convertView) {
        vh.tvTime = (TextView) convertView.findViewById(R.id.tv_list_time);
        vh.tvDescribe = (TextView) convertView.findViewById(R.id.tv_list_describe);
        vh.tvHot = (TextView) convertView.findViewById(R.id.tv_list_hot);
        vh.tvComment = (TextView) convertView.findViewById(R.id.tv_list_comment);
        vh.tvNice = (TextView) convertView.findViewById(R.id.tv_list_nice);
        vh.btnShare = (Button) convertView.findViewById(R.id.btn_list_share);
        vh.vVideo = (JCVideoPlayerStandard) convertView.findViewById(R.id.vv_list_video);
        vh.ivPic = (ImageView) convertView.findViewById(R.id.iv_list_pic);
        vh.video = convertView.findViewById(R.id.video);
        vh.picture = convertView.findViewById(R.id.picture);
        vh.godReview = convertView.findViewById(R.id.godreview);
        vh.lvNiceComment = (ListView) convertView.findViewById(R.id.lv_list_godreview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_list_share:
                myListViewAdapterCallBack.click(v, (Integer) v.getTag());
                break;
        }
    }

    /**
     * 模板
     */
    private class ViewHolder {
        public TextView tvTime, tvDescribe, tvHot, tvComment, tvNice;
        public Button btnShare;
        public JCVideoPlayerStandard vVideo;
        public ImageView ivPic;
        public View video, picture, godReview;
        public ListView lvNiceComment;
    }

    /**
     * 设置回调接口
     */
    public interface MyListViewAdapterCallBack {
        public void click(View view, int position);
    }

    MyListViewAdapterCallBack myListViewAdapterCallBack = null;

    /**
     * 设置点击监听
     *
     * @param myListViewAdapterCallBack
     */
    public void setOnClickCallBackListner(MyListViewAdapterCallBack myListViewAdapterCallBack) {
        this.myListViewAdapterCallBack = myListViewAdapterCallBack;
    }
}
