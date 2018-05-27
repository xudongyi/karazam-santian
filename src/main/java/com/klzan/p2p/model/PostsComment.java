package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 评论表
 * 用于保存content内容的回复、分享、推荐等信息
 * Created by Sue on 2017/5/28.
 */
@Entity
@Table(name = "karazam_posts_comment")
public class PostsComment extends BaseModel {
    /**
     * 回复的评论ID
     */
    private Integer parentId;
    /**
     * 评论的内容ID
     */
    private Integer contentId;
    /**
     * 评论的内容模型
     */
    private String contentModule;
    /**
     * 评论的回复数量
     */
    private Integer commentCount;
    /**
     * 排序编号，常用语置顶等
     */
    private Integer orderNumber;
    /**
     * 评论的作者
     */
    private String author;
    /**
     * 评论的用户ID
     */
    private Integer userId;
    /**
     * 评论的IP地址
     */
    private String ip;
    /**
     * 评论的类型，默认是comment
     */
    private String type;
    /**
     * 评论的内容
     */
    private String text;
    /**
     * 提交评论的浏览器信息
     */
    private String agent;
    /**
     * 评论用户的email
     */
    private String email;
    /**
     * 评论的状态
     */
    private String status;
    /**
     * "顶"的数量
     */
    private Integer voteOp;
    /**
     * "踩"的数量
     */
    private Integer voteDown;
    /**
     * 标识
     */
    private String flag;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 经度
     */
    private String lng;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getContentModule() {
        return contentModule;
    }

    public void setContentModule(String contentModule) {
        this.contentModule = contentModule;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVoteOp() {
        return voteOp;
    }

    public void setVoteOp(Integer voteOp) {
        this.voteOp = voteOp;
    }

    public Integer getVoteDown() {
        return voteDown;
    }

    public void setVoteDown(Integer voteDown) {
        this.voteDown = voteDown;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
