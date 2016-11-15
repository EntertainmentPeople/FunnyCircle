package com.klj.funnycircle.entity;

/**
 * 图片信息
 */
public class ImageInfo {
    /**
     * isGif : false
     * width : 677
     * thumbUrl : http://p3.pstatp.com/w480/10f60004e5420d53b0fb
     * url : http://p3.pstatp.com/w677/10f60004e5420d53b0fb
     * height : 640
     */
    private String url;         //大图
    private String thumbUrl;    //缩略图
    private int width;          //宽
    private int height;         //高
    private boolean isGif;      //是否是动图

    public ImageInfo() {
    }

    public ImageInfo(String url, String thumbUrl, int width, int height, boolean isGif) {
        this.url = url;
        this.thumbUrl = thumbUrl;
        this.width = width;
        this.height = height;
        this.isGif = isGif;
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

    public boolean isGif() {
        return isGif;
    }

    public void setGif(boolean gif) {
        isGif = gif;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "url='" + url + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", isGif=" + isGif +
                '}';
    }
}
