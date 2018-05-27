package com.klzan.p2p.model;

import com.klzan.core.util.SpringUtils;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.model.base.BaseSortModel;
import com.klzan.p2p.service.content.AreaService;
import com.klzan.p2p.service.content.impl.AreaServiceImpl;
import com.klzan.p2p.service.goods.GoodsFollowService;
import com.klzan.p2p.service.goods.impl.GoodsFollowServiceImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 用户收货地址
 */
@Entity
@Table(name = "karazam_user_shipping_address")
public class UserShippingAddress extends BaseSortModel {

	/** 用户 */
	@Column(nullable = false)
	private Integer userId;

	/** 收货人 */
    @Column(nullable = false)
	private String consignee;

	/** 地区 */
	@Column(nullable = false)
	private Integer area;

	/** 详细地址 */
	@Column(nullable = false)
	private String address;

	/** 邮编 */
	@Column
	private String zipCode;

	/** 手机 */
	@Column(nullable = false)
	private String mobile;

	/** 电话 */
	@Column
	private String telephone;

	/** 备注 */
	@Column
	private String memo;

    /** 是否默认 */
    @Column
    private Boolean preferred = Boolean.FALSE;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Boolean getPreferred() {
        return preferred;
    }

    public void setPreferred(Boolean preferred) {
        this.preferred = preferred;
    }

    @Transient
    public String getAreaTreePath() {
        AreaService areaService = SpringUtils.getBean(AreaServiceImpl.class);
        if(areaService!=null){
            Area area = areaService.get(getArea());
            if(area!=null){
                return area.getTreePath();
            }
        }
        return "";
    }

    /** 地区  */
    @Transient
    public Area getAreaObj() {
        AreaService areaService = SpringUtils.getBean(AreaServiceImpl.class);
        if(areaService!=null){
            Area area = areaService.get(getArea());
            if(area!=null){
                return area;
            }
        }
        return null;
    }

}
