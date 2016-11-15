package com.klj.funnycircle.utils;

import android.content.Context;

import com.klj.funnycircle.R;
import com.klj.funnycircle.cn.sharesdk.onekeyshare.OnekeyShare;

import cn.sharesdk.framework.ShareSDK;

/**
 * 常用方法工具类
 */
public class Utils {

    /**
     * 时间格式化
     * @param timestampString
     * @return
     */
    public static String toTransferTime(String timestampString) {
        Long timestamp = Long.parseLong(timestampString);
        String date = new java.text.SimpleDateFormat("MM/dd HH:mm").format(new java.util.Date(timestamp));
        return date;
    }

    /**
     * 分享
     * @param context
     */
    public static void showShare(Context context) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(context);
    }
}
