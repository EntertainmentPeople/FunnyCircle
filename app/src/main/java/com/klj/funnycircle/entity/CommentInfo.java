package com.klj.funnycircle.entity;

/**
 *评论信息
 */
public class CommentInfo {

    /**
     * isNice : true
     * postTime : 1479190869000
     * id : 46094711
     * avatar : http://p3.pstatp.com/thumb/8746/935469569
     * userName : 我的老家就住在那個屯
     * content : 厉害了我的大爷！
     * praise : 12
     */
    private int id;             //评论ID
    private String userName;    //用户名
    private String content;     //内容
    private String avatar;      //头像
    private int praise;         //点赞
    private boolean isNice;     //是否是神评
    private String postTime;     //发送时间

    public CommentInfo() {
    }

    public CommentInfo(int id, String userName, String content, String avatar, int praise, boolean isNice, String postTime) {
        this.id = id;
        this.userName = userName;
        this.content = content;
        this.avatar = avatar;
        this.praise = praise;
        this.isNice = isNice;
        this.postTime = postTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public boolean isNice() {
        return isNice;
    }

    public void setNice(boolean nice) {
        isNice = nice;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", praise=" + praise +
                ", isNice=" + isNice +
                ", postTime='" + postTime + '\'' +
                '}';
    }
}
