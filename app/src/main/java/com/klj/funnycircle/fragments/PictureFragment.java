package com.klj.funnycircle.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.klj.funnycircle.R;
import com.klj.funnycircle.adapter.MyListViewAdapter;
import com.klj.funnycircle.entity.CommentInfo;
import com.klj.funnycircle.entity.ImageInfo;
import com.klj.funnycircle.entity.ItemInfo;
import com.klj.funnycircle.entity.VideoInfo;
import com.klj.funnycircle.listener.MyOnItemClickListener;
import com.klj.funnycircle.utils.ConstantUtils;
import com.klj.funnycircle.utils.Utils;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureFragment extends Fragment {
    private ListView lvShow;    //ListView控件
    private PtrClassicFrameLayout pcfRefresh;//下拉刷新
    private List<ItemInfo> itemInfos = new ArrayList<>();     //要显示的数据
    MyListViewAdapter myListViewAdapter;
    private long minTime;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        findView(view);
        return view;
    }

    /**
     * 找到控件
     *
     * @param view
     */
    private void findView(View view) {
        lvShow = (ListView) view.findViewById(R.id.lv_picture);
        pcfRefresh= (PtrClassicFrameLayout) view.findViewById(R.id.pcf_picture_refresh);
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
        setHeader();
        initData(ConstantUtils.ROOT_PATH + ConstantUtils.INTERFACE_PATH + ConstantUtils.PICTURE);
        setAdapter();
        setListener();
    }
    /**
     * 设置下拉刷新的头
     */
    private void setHeader() {
        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(getActivity());
        pcfRefresh.setHeaderView(defaultHeader);
        pcfRefresh.addPtrUIHandler(defaultHeader);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        myListViewAdapter.setOnClickCallBackListner(new MyListItemClickListener());
        lvShow.setOnItemClickListener(new MyOnItemClickListener(getActivity(),itemInfos,ConstantUtils.TYPE_PICTURE));
        lvShow.setOnScrollListener(new MyListViewScrollListener());
        pcfRefresh.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                itemInfos.clear();
                initData(ConstantUtils.ROOT_PATH + ConstantUtils.INTERFACE_PATH + ConstantUtils.PICTURE);
            }
        });
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        myListViewAdapter=new MyListViewAdapter(getActivity(),itemInfos);
        lvShow.setAdapter(myListViewAdapter);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pcfRefresh.refreshComplete();
            myListViewAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 初始化数据
     */
    private void initData(String path) {
        OkHttpUtils.get(path)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.optInt("rescode") == 0) {
                                parseJsonData(s, jsonObject);
                                handler.sendEmptyMessage(100);
                            }
                            minTime = jsonObject.optLong("minTime");
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
            JSONArray images = object.optJSONArray("images");
            List<ImageInfo> imageInfos = parseImages(images);
            JSONArray niceComments = object.optJSONArray("niceComments");
            List<CommentInfo> commentInfos = parseNiceComments(niceComments);
            int hotDegree = object.optInt("hotDegree");
            itemInfos.add(new ItemInfo(id, content, type, praise, comment, share, postTime, createTime, null, imageInfos, commentInfos, hotDegree));
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
     * 解析图片
     *
     * @param images
     */
    private List<ImageInfo> parseImages(JSONArray images) {
        List<ImageInfo> imageInfos = new ArrayList<>();
        for (int j = 0; j < images.length(); j++) {
            JSONObject image = images.optJSONObject(j);
            String url = image.optString("url");
            String thumbUrl = image.optString("thumbUrl");
            int length = image.optInt("length");
            int width = image.optInt("width");
            boolean isGof = image.optBoolean("isGof");
            imageInfos.add(new ImageInfo(url, thumbUrl, length, width, isGof));
        }
        return imageInfos;
    }

    /**
     * 为ListView中的item设置点击监听
     */
    private class MyListItemClickListener implements MyListViewAdapter.MyListViewAdapterCallBack {
        @Override
        public void click(View v,int position) {
            switch (v.getId()) {
                case R.id.btn_list_share:
                    Utils.showShare(getActivity());
                    break;
            }
        }
    }

    /**
     * 为ListView设置滑动监听事件
     */
    private class MyListViewScrollListener implements AbsListView.OnScrollListener {
        private boolean isFoot=false;
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if(isFoot&& AbsListView.OnScrollListener.SCROLL_STATE_IDLE==scrollState){
                String path= ConstantUtils.ROOT_PATH + ConstantUtils.INTERFACE_PATH + ConstantUtils.PICTURE+"?time="+minTime;
                initData(path);
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            isFoot=firstVisibleItem+visibleItemCount==totalItemCount;
        }
    }
}