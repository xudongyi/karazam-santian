package com.klzan.p2p.dao.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.UserInfo;
import com.klzan.p2p.vo.user.UserInfoVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserInfoDao extends DaoSupport<UserInfo> {

    public void createUserInfo(UserInfoVo userInfoVo, Integer id) {
        UserInfo userInfo = new UserInfo(id, userInfoVo.getRealName(), userInfoVo.getIdNo());
        this.persist(userInfo);
    }

    public List<UserInfo> findByIdNo(String idNo) {
        String sql = "select * from karazam_user_info WHERE deleted=0 AND id_no =?0 ";
        return this.findBySQL(sql, UserInfo.class, idNo);
    }

    public UserInfo getUserInfoByIdNo(String idNo) {
        String sql = "FROM UserInfo WHERE deleted=false and id_no=?0 ";
        return this.findUnique(sql, idNo);
    }

    public UserInfo getUserInfoByUserId(Integer userId) {
        String sql = "FROM UserInfo WHERE deleted=false and userId=?0 ";
        return this.findUnique(sql, userId);
    }

    public PageResult<UserInfoVo> findPages(PageCriteria pageCriteria) {
        StringBuffer sb = new StringBuffer()
                .append("SELECT ")
                .append("b.id as id, ")  //主键
                .append("b.user_id           as userId, ")//关联用户
                .append("b.user_type         as userTypeStr, ")//用户类型
                .append("b.is_authentication as isAuthentication, ")//是否中金认证
                .append("b.is_quick_payment   as isQuickPayment,")//是否快捷支付银行卡设置
                .append("b.real_name         as realName,")      //真实姓名
                .append("b.id_no             as idNo, ")          // 身份证号码
                .append("b.id_issue_date      as idIssueDate,")    // 身份证签发日期
                .append("b.id_expiry_date     as idExpiryDate, ")    //身份证到期日期
                .append("b.phone            as phone,")           //电话号码
                .append("b.qq               as qq,")              //qq
                .append("b.educ             as educ,")            //最高学历
                .append("b.univ             as univ,")            //毕业院校
                .append("b.marriage         as marriageStr,")        //婚姻状况
                .append("b.child            as childStr,")           //子女情况
                .append("b.birthplace       as birthplace,")      //籍贯
                .append("b.domicile_place    as domicilePlace,")   //户籍
                .append("b.abode_place       as abodePlace,")      //居住地
                .append("b.addr             as addr,")            //地址
                .append("b.zipcode          as zipcode,")         //邮编
                .append("b.occup            as occup,")           //职业状态
                .append("b.work_email        as workEmail,")       //工作邮箱
                .append("b.work_mobile       as workMobile,")      //工作手机
                .append("b.work_phone        as workPhone,")       //工作电话/公司电话
                .append("b.work_qq           as workQq,")          //工作QQ
                .append("b.own_house         as ownHouse,")
                .append("b.with_house_loan    as withHouseLoan,")
                .append("b.own_car           as ownCar,")
                .append("b.with_car_loan      as withCarLoan,")
                .append("b.monthly_credit_card_statement as monthlyCreditCardStatement,")
                .append("b.educ_id           as educId,")
                .append("b.post             as post,")
                .append("b.work_years        as workYears,")
                .append("b.income           as income,")
                .append("b.intro            as intro ")
                .append("FROM karazam_user_info b ");
        return this.findPageBySQL(sb.toString(), pageCriteria, new ScalarAliasCallback<UserInfoVo>() {
            @Override
            protected Class<UserInfoVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("userId", StandardBasicTypes.INTEGER);
                query.addScalar("userTypeStr", StandardBasicTypes.STRING);
                query.addScalar("isAuthentication", StandardBasicTypes.BOOLEAN);
                query.addScalar("isQuickPayment", StandardBasicTypes.BOOLEAN);
                query.addScalar("realName", StandardBasicTypes.STRING);
                query.addScalar("idNo", StandardBasicTypes.STRING);
                query.addScalar("idIssueDate", StandardBasicTypes.DATE);
                query.addScalar("idExpiryDate", StandardBasicTypes.DATE);
                query.addScalar("phone", StandardBasicTypes.STRING);
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
                return UserInfoVo.class;
            }
        });
    }

    public List<UserInfo> findByIdNo(UserType type, String idNo) {
        String sql = "select ui.* from karazam_user_info ui left join karazam_user u on u.id=ui.user_id WHERE ui.deleted=0 AND ui.id_no =?0 AND u.type=?1";
        return this.findBySQL(sql, UserInfo.class, idNo, type.name());
    }
}