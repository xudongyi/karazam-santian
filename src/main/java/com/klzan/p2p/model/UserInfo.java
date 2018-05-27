package com.klzan.p2p.model;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.Child;
import com.klzan.p2p.enums.Marriage;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 用户信息
 */
@Entity
@Table(name = "karazam_user_info")
public class UserInfo extends BaseModel {
    /**
     * 关联用户
     */
    private Integer userId;

    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证号码
     */
    @Pattern(regexp = "^([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3})|([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}(x|X)))$")
    @Column(length = 20)
    private String idNo;
    /**
     * 身份证签发日期
     */
    private Date idIssueDate;
    /**
     * 身份证到期日期
     */
    private Date idExpiryDate;
    /**
     * QQ号码
     */
    @Column(length = 20)
    private String qq;
    /**
     * 最高学历
     */
    private String educ;
    /**
     * 毕业院校
     */
    private String univ;
    /**
     * 婚姻状况
     */
    @Enumerated(EnumType.STRING)
    private Marriage marriage;
    /**
     * 子女情况
     */
    @Enumerated(EnumType.STRING)
    private Child child;

	/**
     * 籍贯
     */
    private Integer birthplace;
    /**
     * 户籍
     */
    private Integer domicilePlace;
    /**
     * 居住地
     */
    private Integer abodePlace;
    /**
     * 地址
     */
    private String addr;
    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 职业状态
     */
    private String occup;
    /**
     * 工作邮箱
     */
    private String workEmail;
    /**
     * 工作手机
     */
    @Column(length = 20)
    private String workMobile;
    /**
     * 工作电话/公司电话
     */
    @Column(length = 20)
    private String workPhone;
    /**
     * 工作QQ
     */
    @Column(length = 20)
    private String workQq;
    /**
     * 有无房产
     */
    private Boolean ownHouse;
    /**
     * 有无房贷
     */
    private Boolean withHouseLoan;
    /**
     * 有无车产
     */
    private Boolean ownCar;
    /**
     * 有无车贷
     */
    private Boolean withCarLoan;
    /**
     * 每月信用卡账单
     */
    @Column(length = 50)
    private String monthlyCreditCardStatement;
    /**
     * 学历编号
     */
    private String educId;
    /**
     * 职位
     */
    private String post;
    /**
     * 工作年限
     */
    private String workYears;
    /**
     * 月收入
     */
    private String income;
    /**
     * 简介（个人简介、会员简介）
     */
    private String intro;

    public UserInfo() {
    	super();
	}

	public UserInfo(Integer userId, String realName, String idNo) {
		super();
		this.userId = userId;
		this.realName = realName;
		this.idNo = idNo;
	}
    
    public void update(String realName, String idNo){
		this.realName = realName;
		this.idNo = idNo;
    }

	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public void setIdIssueDate(Date idIssueDate) {
		this.idIssueDate = idIssueDate;
	}

	public void setIdExpiryDate(Date idExpiryDate) {
		this.idExpiryDate = idExpiryDate;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public void setEduc(String educ) {
		this.educ = educ;
	}

	public void setUniv(String univ) {
		this.univ = univ;
	}

	public void setMarriage(Marriage marriage) {
		this.marriage = marriage;
	}

	public void setChild(Child child) {
		this.child = child;
	}

	public void setBirthplace(Integer birthplace) {
		this.birthplace = birthplace;
	}

	public void setDomicilePlace(Integer domicilePlace) {
		this.domicilePlace = domicilePlace;
	}

	public void setAbodePlace(Integer abodePlace) {
		this.abodePlace = abodePlace;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public void setOccup(String occup) {
		this.occup = occup;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	public void setWorkMobile(String workMobile) {
		this.workMobile = workMobile;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public void setWorkQq(String workQq) {
		this.workQq = workQq;
	}

	public void setOwnHouse(Boolean ownHouse) {
		this.ownHouse = ownHouse;
	}

	public void setWithHouseLoan(Boolean withHouseLoan) {
		this.withHouseLoan = withHouseLoan;
	}

	public void setOwnCar(Boolean ownCar) {
		this.ownCar = ownCar;
	}

	public void setWithCarLoan(Boolean withCarLoan) {
		this.withCarLoan = withCarLoan;
	}

	public void setMonthlyCreditCardStatement(String monthlyCreditCardStatement) {
		this.monthlyCreditCardStatement = monthlyCreditCardStatement;
	}
	public void setEducId(String educId) {
		this.educId = educId;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public void setWorkYears(String workYears) {
		this.workYears = workYears;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

    public Integer getAbodePlace() {
        return abodePlace;
    }

    public String getAddr() {
        return addr;
    }

    public Integer getBirthplace() {
        return birthplace;
    }

    public Child getChild() {
        return child;
    }

    public Integer getDomicilePlace() {
        return domicilePlace;
    }

    public String getEduc() {
        return educ;
    }

    public String getEducId() {
        return educId;
    }

    public Date getIdExpiryDate() {
        return idExpiryDate;
    }

    public Date getIdIssueDate() {
        return idIssueDate;
    }

    public String getIdNo() {
        return idNo;
    }

    public String getIncome() {
        return income;
    }

    public String getIntro() {
        return intro;
    }

    public Marriage getMarriage() {
        return marriage;
    }

    public String getMonthlyCreditCardStatement() {
        return monthlyCreditCardStatement;
    }

    public String getOccup() {
        return occup;
    }

    public Boolean getOwnCar() {
        return ownCar;
    }

    public Boolean getOwnHouse() {
        return ownHouse;
    }
//
//    public String getPhone() {
//        return phone;
//    }

    public String getPost() {
        return post;
    }

    public String getQq() {
        return qq;
    }


    public String getRealName() {
        return realName;
    }

    public String getUniv() {
        return univ;
    }

    public Integer getUserId() {
        return userId;
    }

    public Boolean getWithCarLoan() {
        return withCarLoan;
    }

    public Boolean getWithHouseLoan() {
        return withHouseLoan;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public String getWorkMobile() {
        return workMobile;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public String getWorkQq() {
        return workQq;
    }

    public String getWorkYears() {
        return workYears;
    }

    public String getZipcode() {
        return zipcode;
    }

    public boolean hasIdentity() {
        if (StringUtils.isNotBlank(getIdNo())) {
            return true;
        }
        return false;
    }
}
