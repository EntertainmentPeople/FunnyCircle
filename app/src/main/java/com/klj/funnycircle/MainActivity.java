package com.klj.funnycircle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.klj.funnycircle.utils.FragmentUtils;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {
    private TextView tvTitle;       //标题
    private ViewPager vpShow;        //滑动界面
    private RadioGroup rgMenu;      //选项栏
    private RadioButton rbVideo;    //视频
    private RadioButton rbJoke;    //段子
    private RadioButton rbPicture;    //图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        findView();
        setAdapter();
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        rgMenu.setOnCheckedChangeListener(new MyRadioButtonChangeListener());
        vpShow.addOnPageChangeListener(new MyViewPagerChangeListener());
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        vpShow.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
    }

    /**
     * 找到控件
     */
    private void findView() {
        tvTitle= (TextView) findViewById(R.id.tv_title);
        vpShow= (ViewPager) findViewById(R.id.vp_show);
        rgMenu= (RadioGroup) findViewById(R.id.rg_menu);
        rbVideo= (RadioButton) findViewById(R.id.rb_video);
        rbJoke= (RadioButton) findViewById(R.id.rb_joke);
        rbPicture= (RadioButton) findViewById(R.id.rb_picture);
    }

    /**
     * 为ViewPager设置适配器
     */
    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentUtils.getFragments().get(position);
        }

        @Override
        public int getCount() {
            return FragmentUtils.getFragmentsSize();
        }
    }

    /**
     * 为RadioGroup设置item选择监听
     */
    private class MyRadioButtonChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_video:
                    vpShow.setCurrentItem(0);
                    break;
                case R.id.rb_joke:
                    vpShow.setCurrentItem(1);
                    break;
                case R.id.rb_picture:
                    vpShow.setCurrentItem(2);
                    break;
            }
        }
    }

    /**
     * 为ViewPager设置滑动监听
     */
    private class MyViewPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ((RadioButton)rgMenu.getChildAt(position)).setChecked(true);
            switch (position){
                case 0:
                    tvTitle.setText("搞笑视频");
                    break;
                case 1:
                    tvTitle.setText("段子");
                    break;
                case 2:
                    tvTitle.setText("趣图");
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
