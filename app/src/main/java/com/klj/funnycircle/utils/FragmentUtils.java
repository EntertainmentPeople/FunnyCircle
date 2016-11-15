package com.klj.funnycircle.utils;

import android.support.v4.app.Fragment;

import com.klj.funnycircle.fragments.JokeFragment;
import com.klj.funnycircle.fragments.PictureFragment;
import com.klj.funnycircle.fragments.VideoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment工具类
 */
public class FragmentUtils {
    private static List<Fragment> fragments=new ArrayList<>();  //存储fragment
    /**
     * 初始化fragment
     */
    static {
        fragments.add(new VideoFragment());     //添加搞笑视频
        fragments.add(new JokeFragment());      //添加段子
        fragments.add(new PictureFragment());   //添加搞笑图片
    }

    /**
     * 获取所有的Fragment
     * @return
     */
    public static List<Fragment> getFragments(){
        return fragments;
    }

    /**
     * 获取所有的Fragment的容量
     * @return
     */
    public static int getFragmentsSize(){
        return fragments.size();
    }
}
