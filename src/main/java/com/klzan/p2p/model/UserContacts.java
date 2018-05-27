package com.klzan.p2p.model;

import com.klzan.p2p.enums.RelationType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;

/**
 * 用户联系人
 */
@Entity
@Table(name = "karazam_user_contacts")
public class UserContacts extends BaseModel {
    /**
     * 关联用户
     */
    private Integer userId;
    /**
     * 联系人姓名
     */
    @Column(length = 50)
    private String name;
    /**
     * 联系人手机
     */
    @Column(length = 20)
    private String mobile;
    /**
     * 联系人关系
     */
    @Enumerated(EnumType.STRING)
    private RelationType relation;

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public RelationType getRelation() {
        return relation;
    }
}
