package com.klj.funnycircle.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.klj.funnycircle.R;
import com.klj.funnycircle.adapter.MyListViewAdapter;
import com.klj.funnycircle.entity.CommentInfo;
import com.klj.funnycircle.entity.ImageInfo;
import com.klj.funnycircle.entity.ItemInfo;
import com.klj.funnycircle.entity.VideoInfo;
import com.klj.funnycircle.utils.ConstantUtils;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 搞笑视频页面
 */
public class VideoFragment extends Fragment {
    private ListView lvShow;    //ListView控件
    private List<ItemInfo> itemInfos = new ArrayList<>();     //要显示的数据
    MyListViewAdapter myListViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        findView(view);
        return view;
    }

    /**
     * 找到控件
     *
     * @param view
     */
    private void findView(View view) {
        lvShow = (ListView) view.findViewById(R.id.lv_video);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        initData();
        setAdapter();
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        myListViewAdapter.setOnClickCallBackListner(new MyListItemClickListener());
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        myListViewAdapter=new MyListViewAdapter(getActivity(),itemInfos);
        lvShow.setAdapter(myListViewAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        OkHttpUtils.get(ConstantUtils.ROOT_PATH + ConstantUtils.INTERFACE_PATH + ConstantUtils.VIDEO)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.optInt("rescode") == 0) {
                                parseJsonData(s, jsonObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 解析json数据
     *
     * @param s
     * @param jsonObject
     */
    private void parseJsonData(String s, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.optJSONArray("items");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.optJSONObject(i);
            int id = object.optInt("id");
            String content = object.optString("content");
            int type = object.optInt("type");
            int praise = object.optInt("praise");
            int comment = object.optInt("comment");
            int share = object.optInt("share");
            String postTime = object.optString("postTime");
            String createTime = object.optString("createTime");
            JSONArray videos = object.optJSONArray("videos");
            List<VideoInfo> videoInfos = parseVideos(videos);
            JSONArray niceComments = object.optJSONArray("niceComments");
            List<CommentInfo> commentInfos = parseNiceComments(niceComments);
            int hotDegree = object.optInt("hotDegree");
            itemInfos.add(new ItemInfo(id, content, type, praise, comment, share, postTime, createTime, videoInfos, null, commentInfos, hotDegree));
        }
    }

    /**
     * 解析神评论
     *
     * @param niceComments
     */
    private List<CommentInfo> parseNiceComments(JSONArray niceComments) {
        List<CommentInfo> commentInfos = new ArrayList<>();
        for (int j = 0; j < niceComments.length(); j++) {
            JSONObject niceComment = niceComments.optJSONObject(j);
            int id = niceComment.optInt("id");
            String userName = niceComment.optString("userName");
            String content = niceComment.optString("content");
            String avatar = niceComment.optString("avatar");
            int praise = niceComment.optInt("praise");
            boolean isNice = niceComment.optBoolean("isNice");
            String postTime = niceComment.optString("postTime");
            commentInfos.add(new CommentInfo(id, userName, content, avatar, praise, isNice, postTime));
        }
        return commentInfos;
    }

    /**
     * 解析视频
     *
     * @param videos
     */
    private List<VideoInfo> parseVideos(JSONArray videos) {
        List<VideoInfo> videoInfos = new ArrayList<>();
        for (int j = 0; j < videos.length(); j++) {
            JSONObject video = videos.optJSONObject(j);
            String url = video.optString("url");
            String thumbUrl = video.optString("thumbUrl");
            int length = video.optInt("length");
            int width = video.optInt("width");
            int height = video.optInt("height");
            videoInfos.add(new VideoInfo(url, thumbUrl, length, width, height));
        }
        return videoInfos;
    }

    /**
     * 为ListView中的item设置点击监听
     */
    private class MyListItemClickListener implements MyListViewAdapter.MyListViewAdapterCallBack {
        @Override
        public void click(View v,int position) {
            switch (v.getId()) {
                case R.id.vv_list_video:
                    ((VideoView)v).start();
                    break;
            }
        }
    }
}
