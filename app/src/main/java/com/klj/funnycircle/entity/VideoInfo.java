package com.klj.funnycircle.entity;

import java.io.Serializable;

/**
 * 视频信息
 */
public class VideoInfo implements Serializable {

    /**
     * length : 172
     * width : 640
     * thumbUrl : http://p1.pstatp.com/large/1139000328cfcb42df47
     * url : http://ic.snssdk.com/neihan/video/playback/?video_id=0a8c0a98753e4e6ea74268af4d598e9c&quality=480p&line=0&is_gif=0
     * height : 360
     */
    private String url;         //视频地址
    private String thumbUrl;    //视频图片
    private int length;         //视频长度
    private int width;          //宽
    private int height;         //高

    public VideoInfo() {
    }

    public VideoInfo(String url, String thumbUrl, int length, int width, int height) {
        this.url = url;
        this.thumbUrl = thumbUrl;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "url='" + url + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
