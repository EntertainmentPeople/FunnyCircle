package com.klj.funnycircle.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.klj.funnycircle.DetailActivity;
import com.klj.funnycircle.entity.ItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView的item点击事件
 */
public class MyOnItemClickListener implements AdapterView.OnItemClickListener {
    private int itemId;                 //id
    private int itemType;               //类型
    private List<ItemInfo> itemInfos;     //要显示的数据
    Context context;

    public MyOnItemClickListener(Context context, List<ItemInfo> itemInfos, int itemType){
        this.context=context;
        this.itemInfos=itemInfos;
        this.itemType=itemType;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(context, DetailActivity.class);
        intent.putExtra("itemId",itemInfos.get(position).getId());
        intent.putExtra("itemType",itemType);
        intent.putExtra("itemInfo",itemInfos.get(position));
        context.startActivity(intent);
    }
}
