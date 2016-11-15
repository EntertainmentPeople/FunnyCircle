package com.klj.funnycircle.entity;

import java.util.List;

/**
 * 条目信息
 */
public class ItemInfo {

    /**
     * postTime : 1479091150000
     * hotDegree : 273354
     * createTime : 1479192058000
     * comment : 511
     * share : 2190
     * videos : [{"length":78,"width":360,"thumbUrl":"http://p1.pstatp.com/large/f90000455e4327b1e40","url":"http://ic.snssdk.com/neihan/video/playback/?video_id=c13bcd74d465426e9c4802e3c2cda310&quality=480p&line=0&is_gif=0","height":640}]
     * niceComments : [{"isNice":true,"postTime":1479122548000,"id":46095368,"avatar":"http://p3.pstatp.com/thumb/14f000bdb0267cf5302","userName":"安宁人民发来贺电","content":"不要黑，至少他还有勇气大声的读出来，给他赞","praise":2585},{"isNice":true,"postTime":1479117091000,"id":46095389,"avatar":"http://p1.pstatp.com/thumb/d2a001251aa8accb712","userName":"我的名字12个子不信你数","content":"他很棒！如果是你，你不敢","praise":1109}]
     * id : 109202
     * type : 3
     * content : 我就想说卖货里还有谁
     * praise : 2597
     */
    private int id;
    private String content;
    private int type;
    private int praise;
    private int comment;
    private int share;
    private String postTime;
    private String createTime;
    private List<VideoInfo> videos;
    private List<ImageInfo> images;
    private List<CommentInfo> niceComments;
    private int hotDegree;

    public ItemInfo() {
    }

    public ItemInfo(int id, String content, int type, int praise, int comment, int share, String postTime, String createTime, List<VideoInfo> videos, List<ImageInfo> images, List<CommentInfo> niceComments, int hotDegree) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.praise = praise;
        this.comment = comment;
        this.share = share;
        this.postTime = postTime;
        this.createTime = createTime;
        this.videos = videos;
        this.images = images;
        this.niceComments = niceComments;
        this.hotDegree = hotDegree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<VideoInfo> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoInfo> videos) {
        this.videos = videos;
    }

    public List<ImageInfo> getImages() {
        return images;
    }

    public void setImages(List<ImageInfo> images) {
        this.images = images;
    }

    public List<CommentInfo> getNiceComments() {
        return niceComments;
    }

    public void setNiceComments(List<CommentInfo> niceComments) {
        this.niceComments = niceComments;
    }

    public int getHotDegree() {
        return hotDegree;
    }

    public void setHotDegree(int hotDegree) {
        this.hotDegree = hotDegree;
    }

    @Override
    public String toString() {
        return "ItemInfo{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", praise=" + praise +
                ", comment=" + comment +
                ", share=" + share +
                ", postTime='" + postTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", videos=" + videos +
                ", images=" + images +
                ", niceComments=" + niceComments +
                ", hotDegree=" + hotDegree +
                '}';
    }
}
