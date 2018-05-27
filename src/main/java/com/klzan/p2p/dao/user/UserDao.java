/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.User;
import com.klzan.p2p.vo.user.UserVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao extends DaoSupport<User> {

    public User findByLoginName(String loginName, UserType userType) {
        return super.findUnique("FROM User WHERE deleted=false AND loginName=?0 AND type =?1 ", loginName, userType);
    }

    public PageResult<UserVo> findUsers(PageCriteria pageCriteria) {
        StringBuffer sb = new StringBuffer()
                .append("SELECT ")
                .append("     u.id as id, ")
                .append("     u.login_name as loginName, ")
                .append("     u.name       as name, ")
                .append("     u.gender     as gender, ")
                .append("     u.email      as email, ")
                .append("     u.mobile     as mobile, ")
                .append("     u.type       as typeStr, ")
                .append("     u.login_count as loginCount, ")
                .append("     u.previous_visit as previousVisit ")
                .append("FROM karazam_user u ")
                .append("WHERE u.deleted = false ");
        return super.findPageBySQL(sb.toString(), pageCriteria, new ScalarAliasCallback<UserVo>() {
            @Override
            protected Class<UserVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("loginName", StandardBasicTypes.STRING);
                query.addScalar("name", StandardBasicTypes.STRING);
                query.addScalar("gender", StandardBasicTypes.STRING);
                query.addScalar("email", StandardBasicTypes.STRING);
                query.addScalar("mobile", StandardBasicTypes.STRING);
                query.addScalar("typeStr", StandardBasicTypes.STRING);
                query.addScalar("loginCount", StandardBasicTypes.INTEGER);
                query.addScalar("previousVisit", StandardBasicTypes.TIMESTAMP);
                return UserVo.class;
            }
        });
    }

    public Boolean isExistLoginName(String loginName, UserType userType) {
        String hql = "FROM User WHERE loginName=?0 AND type=?1 ";
        User user = super.findUnique(hql, loginName, userType);
        return null == user ? false : true;
    }

    public User getUserByMobile(String mobile, UserType userType) {
        StringBuffer hql = new StringBuffer("FROM User WHERE mobile=?0 AND type=?1  ");
        return super.findUnique(hql.toString(), mobile, userType);
    }

    public List<User> getUserListByMobile(String mobile, UserType userType) {
        Map map = new HashMap();
        map.put("mobile",mobile);
        StringBuffer hql = new StringBuffer("FROM User WHERE mobile=:mobile");
        if (userType!=null){
            hql.append(" AND type=:userType ");
            map.put("userType",userType);
        }
        return super.find(hql.toString(),map);
    }

    public PageResult<UserVo> findPageListPage(PageCriteria pageCriteria) {
        StringBuffer sb = new StringBuffer()
                .append("SELECT ")
                .append("b.id as userInfoId,")  //主键
                .append("a.id as userId,")//关联用户
                .append("b.is_authentication as isAuthentication,")//是否中金认证
                .append("b.is_quick_payment as isQuickPayment,")//是否快捷支付银行卡设置
                .append("b.real_name as realName,")      //真实姓名
                .append("b.id_no as idNo,")          // 身份证号码
                .append("b.id_issue_date as idIssueDate,")    // 身份证签发日期
                .append("b.id_expiry_date as idExpiryDate,")    //身份证到期日期
                .append("b.qq as qq,")              //qq
                .append("b.educ as educ,")            //最高学历
                .append("b.univ as univ,")            //毕业院校
                .append("b.marriage as marriageStr,")        //婚姻状况
                .append("b.child as childStr,")           //子女情况
                .append("b.birthplace as birthplace,")      //籍贯
                .append("b.domicile_place as domicilePlace,")   //户籍
                .append("b.abode_place as abodePlace,")      //居住地
                .append("b.addr as addr,")            //地址
                .append("b.zipcode as zipcode,")         //邮编
                .append("b.occup as occup,")           //职业状态
                .append("b.work_email as workEmail,")       //工作邮箱
                .append("b.work_mobile as workMobile,")      //工作手机
                .append("b.work_phone as workPhone,")       //工作电话/公司电话
                .append("b.work_qq as workQq,")          //工作QQ
                .append("b.own_house as ownHouse,")
                .append("b.with_house_loan as withHouseLoan,")
                .append("b.own_car as ownCar,")
                .append("b.with_car_loan as withCarLoan,")
                .append("b.monthly_credit_card_statement as monthlyCreditCardStatement,")
                .append("b.educ_id as educId,")
                .append("b.post as post,")
                .append("b.work_years as workYears,")
                .append("b.income as income,")
                .append("b.intro as intro,")
                .append("a.mobile as mobile,")
                .append("a.birthday as birthday,")
                .append("a.login_Name as loginName,")
                .append("a.name as name,")
                .append("a.gender as gender,")
                .append("a.corp as corp,")
                .append("a.email as email ")
                .append("FROM user a left join user_info b on a.id=b.user_id ")
                .append("where a.deleted=false ");
        return this.findPageBySQL(sb.toString(), pageCriteria, new ScalarAliasCallback<UserVo>() {
            @Override
            protected Class<UserVo> doAddScalar(NativeQuery query) {
                query.addScalar("userInfoId", StandardBasicTypes.INTEGER);
                query.addScalar("userId", StandardBasicTypes.INTEGER);
                query.addScalar("isAuthentication", StandardBasicTypes.BOOLEAN);
                query.addScalar("isQuickPayment", StandardBasicTypes.BOOLEAN);
                query.addScalar("realName", StandardBasicTypes.STRING);
                query.addScalar("idNo", StandardBasicTypes.STRING);
                query.addScalar("idIssueDate", StandardBasicTypes.DATE);
                query.addScalar("idExpiryDate", StandardBasicTypes.DATE);
                query.addScalar("qq", StandardBasicTypes.STRING);
                query.addScalar("educ", StandardBasicTypes.STRING);
                query.addScalar("univ", StandardBasicTypes.STRING);
                query.addScalar("marriageStr", StandardBasicTypes.STRING);
                query.addScalar("childStr", StandardBasicTypes.STRING);
                query.addScalar("birthplace", StandardBasicTypes.INTEGER);
                query.addScalar("domicilePlace", StandardBasicTypes.INTEGER);
                query.addScalar("abodePlace", StandardBasicTypes.INTEGER);
                query.addScalar("addr", StandardBasicTypes.STRING);
                query.addScalar("zipcode", StandardBasicTypes.STRING);
                query.addScalar("occup", StandardBasicTypes.STRING);
                query.addScalar("workEmail", StandardBasicTypes.STRING);
                query.addScalar("workMobile", StandardBasicTypes.STRING);
                query.addScalar("workPhone", StandardBasicTypes.STRING);
                query.addScalar("workQq", StandardBasicTypes.STRING);
                query.addScalar("ownHouse", StandardBasicTypes.BOOLEAN);
                query.addScalar("withHouseLoan", StandardBasicTypes.BOOLEAN);
                query.addScalar("ownCar", StandardBasicTypes.BOOLEAN);
                query.addScalar("withCarLoan", StandardBasicTypes.BOOLEAN);
                query.addScalar("monthlyCreditCardStatement", StandardBasicTypes.STRING);
                query.addScalar("educId", StandardBasicTypes.STRING);
                query.addScalar("post", StandardBasicTypes.STRING);
                query.addScalar("workYears", StandardBasicTypes.STRING);
                query.addScalar("income", StandardBasicTypes.STRING);
                query.addScalar("intro", StandardBasicTypes.STRING);
                query.addScalar("mobile", StandardBasicTypes.STRING);
                query.addScalar("birthday", StandardBasicTypes.DATE);
                query.addScalar("loginName", StandardBasicTypes.STRING);
                query.addScalar("name", StandardBasicTypes.STRING);
                query.addScalar("gender", StandardBasicTypes.STRING);
                query.addScalar("corp", StandardBasicTypes.BOOLEAN);
                query.addScalar("email", StandardBasicTypes.STRING);
                return UserVo.class;
            }
        },pageCriteria.getParams());
    }

    public User createUser(User user){
        return this.persist(user);
    }

    public User getUserByInviteCode(String inviteCode) {
        String hql = "FROM User WHERE deleted=0 AND inviteCode=?0 ";
        return findUnique(hql, inviteCode);
    }
}
