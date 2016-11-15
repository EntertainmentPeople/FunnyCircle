package com.klj.funnycircle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.klj.funnycircle.R;
import com.klj.funnycircle.entity.CommentInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 评论的适配器
 */
public class MyCommentAdapter extends BaseAdapter {
    List<CommentInfo> list;
    Context context;
    public MyCommentAdapter(Context context,List<CommentInfo> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_list_comment,parent,false);
            vh.ivPortrait= (ImageView) convertView.findViewById(R.id.iv_list_comment_portrait);
            vh.tvName= (TextView) convertView.findViewById(R.id.tv_list_comment_name);
            vh.tvContent= (TextView) convertView.findViewById(R.id.tv_list_comment_content);
            vh.rbNice= (RadioButton) convertView.findViewById(R.id.rb_list_comment_nice);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        CommentInfo commentInfo = list.get(position);
        Picasso.with(context).load(commentInfo.getAvatar()).into(vh.ivPortrait);
        vh.tvName.setText(commentInfo.getUserName());
        vh.tvContent.setText(commentInfo.getContent());
        vh.rbNice.setText(commentInfo.getPraise()+"");
        return convertView;
    }

    /**
     * 模板
     */
    private class ViewHolder{
        public ImageView ivPortrait;
        public TextView tvName,tvContent;
        public RadioButton rbNice;
    }
}
