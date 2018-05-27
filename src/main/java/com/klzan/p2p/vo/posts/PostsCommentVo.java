package com.klzan.p2p.vo.posts;

import com.klzan.p2p.common.vo.BaseVo;

/**
 * 评论表
 * 用于保存content内容的回复、分享、推荐等信息
 * Created by Sue on 2017/5/28.
 */
public class PostsCommentVo extends BaseVo {
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

}
