package com.klj.funnycircle;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.klj.funnycircle.adapter.MyCommentAdapter;
import com.klj.funnycircle.entity.CommentInfo;
import com.klj.funnycircle.entity.ImageInfo;
import com.klj.funnycircle.entity.ItemInfo;
import com.klj.funnycircle.utils.ConstantUtils;
import com.klj.funnycircle.utils.Utils;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 详情页面
 */
public class DetailActivity extends AppCompatActivity {
    private Button btnBack;             //返回按钮
    private TextView tvTime;            //显示时间
    private TextView tvDescribe;        //显示内容
    private TextView tvHot;             //火热的
    private TextView tvComment;         //评论数
    private TextView tvNice;            //点赞数
    private Button btnShare;            //分享
    private JCVideoPlayerStandard vVideo;//视频
    private ImageView ivPic;            //图片
    private View video;                 //视频布局
    private View picture;               //图片布局
    private ListView lvComment;         //显示评论
    private int itemId;                 //id
    private int itemType;               //类型
    private int page = 1;               //页数
    private SwipyRefreshLayout srlRefresh;  //下拉刷新
    List<CommentInfo> commentInfos = new ArrayList<>();
    MyCommentAdapter myCommentAdapter;
    ItemInfo itemInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        findView();
        setValue(itemInfo);
        initData();
        setAdapter();
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        btnBack.setOnClickListener(new MyBtnClickListener());
        btnShare.setOnClickListener(new MyBtnClickListener());
        srlRefresh.setOnRefreshListener(new MyRefreshListener());
    }

    /**
     * 设置值
     *
     * @param itemInfo
     */
    private void setValue(ItemInfo itemInfo) {
        tvTime.setText(Utils.toTransferTime(itemInfo.getCreateTime()));
        tvDescribe.setText(itemInfo.getContent());
        if (isVideo(itemInfo)) {
            playVideo(itemInfo);
        } else if (isPicture(itemInfo)) {
            showPicture(itemInfo);
        }
        tvHot.setText(itemInfo.getHotDegree() + "");
        tvComment.setText(itemInfo.getComment() + "");
        tvNice.setText(itemInfo.getPraise() + "");
    }

    /**
     * 显示图片
     *
     * @param itemInfo
     */
    private void showPicture(ItemInfo itemInfo) {
        List<ImageInfo> images = itemInfo.getImages();
        if (images.get(0).isGif()) {
            Glide.with(this).load(images.get(0).getUrl()).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivPic);
        } else {
            Picasso.with(this).load(images.get(0).getThumbUrl()).into(ivPic);
        }

    }

    /**
     * 判断是否是图片
     *
     * @param itemInfo
     * @return
     */
    private boolean isPicture(ItemInfo itemInfo) {
        if (null != itemInfo.getImages()) {
            picture.setVisibility(View.VISIBLE);
            return true;
        } else {
            picture.setVisibility(View.GONE);
        }
        return false;
    }

    /**
     * 视频播放
     *
     * @param itemInfo
     */
    private void playVideo(ItemInfo itemInfo) {
        String path = itemInfo.getVideos().get(0).getUrl();
        if (!path.endsWith(".mp4")) {
            path = path + ".mp4";
        }
        vVideo.setUp(path, itemInfo.getContent());
        vVideo.thumbImageView.setImageURI(Uri.parse(itemInfo.getVideos().get(0).getThumbUrl()));
        Picasso.with(this).load(itemInfo.getVideos().get(0).getThumbUrl()).into(vVideo.thumbImageView);
    }

    /**
     * 判断是否是视频
     *
     * @param itemInfo
     * @return
     */
    private boolean isVideo(ItemInfo itemInfo) {
        if (null != itemInfo.getVideos()) {
            video.setVisibility(View.VISIBLE);
            return true;
        } else {
            video.setVisibility(View.GONE);
        }
        return false;
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        myCommentAdapter = new MyCommentAdapter(DetailActivity.this, commentInfos);
        lvComment.setAdapter(myCommentAdapter);
    }


    /**
     * 更新适配器
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myCommentAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 初始化数据
     */
    private void initData() {
        OkHttpUtils.get(ConstantUtils.ROOT_PATH + ConstantUtils.INTERFACE_PATH + ConstantUtils.COMMENT)
                .params("id", itemId)
                .params("type", itemType)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.optInt("rescode") == 0) {
                                parseJsonData(jsonObject, s);
                                handler.sendEmptyMessage(100);
                            }
                            srlRefresh.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 解析评论
     *
     * @param jsonObject
     * @param s
     */
    private void parseJsonData(JSONObject jsonObject, String s) {
        JSONArray comments = jsonObject.optJSONArray("comments");
        for (int j = 0; j < comments.length(); j++) {
            JSONObject comment = comments.optJSONObject(j);
            int id = comment.optInt("id");
            String userName = comment.optString("userName");
            String content = comment.optString("content");
            String avatar = comment.optString("avatar");
            int praise = comment.optInt("praise");
            boolean isNice = comment.optBoolean("isNice");
            String postTime = comment.optString("postTime");
            commentInfos.add(new CommentInfo(id, userName, content, avatar, praise, isNice, postTime));
        }
    }

    /**
     * 找到控件
     */
    private void findView() {
        srlRefresh = (SwipyRefreshLayout) findViewById(R.id.srl_detail_refresh);
        btnBack = (Button) findViewById(R.id.btn_detail_back);
        tvTime = (TextView) findViewById(R.id.tv_detail_time);
        tvDescribe = (TextView) findViewById(R.id.tv_detail_describe);
        tvHot = (TextView) findViewById(R.id.tv_detail_hot);
        tvComment = (TextView) findViewById(R.id.tv_detail_comment);
        tvNice = (TextView) findViewById(R.id.tv_detail_nice);
        btnShare = (Button) findViewById(R.id.btn_detail_share);
        vVideo = (JCVideoPlayerStandard) findViewById(R.id.vv_list_video);
        ivPic = (ImageView) findViewById(R.id.iv_list_pic);
        video = findViewById(R.id.video);
        picture = findViewById(R.id.picture);
        lvComment = (ListView) findViewById(R.id.lv_detial_comment);
        getData();
    }

    /**
     * 获取传过来的数据
     */
    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemId = bundle.getInt("itemId", 0);
        itemType = bundle.getInt("itemType", 0);
        itemInfo = (ItemInfo) bundle.getSerializable("itemInfo");
    }

    /**
     * 设置点击监听
     */
    private class MyBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_detail_back:
                    finish();
                    break;
                case R.id.btn_detail_share:
                    Utils.showShare(DetailActivity.this);
                    break;
            }
        }
    }

    /**
     * 下拉刷新
     */
    private class MyRefreshListener implements SwipyRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh(SwipyRefreshLayoutDirection direction) {
            page++;
            initData();
        }
    }
}
