package com.klzan.mobile.vo;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserFinance;
import com.klzan.p2p.vo.borrowing.BorrowingSimpleVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by suhao Date: 2016/12/22 Time: 21:15
 *
 * @version: 1.0
 */
public class IndexPageVo implements Serializable {
    private List<BorrowingSimpleVo> hotProjects;
    private List<Map<String, String>> banners;
    private  User user;
    private UserFinance userFinance;
    private Map detail;
    public IndexPageVo() {
    }

    public Map getDetail() {
        return detail;
    }

    public void setDetail(Map detail) {
        this.detail = detail;
    }

    public IndexPageVo(List<BorrowingSimpleVo> hotProjects, List<Map<String, String>> banners, User user, UserFinance userFinance) {
        this.hotProjects = hotProjects;
        this.banners = banners;
        this.user = user;
        this.userFinance=userFinance;
    }
    public List<BorrowingSimpleVo> getHotProjects() {
        return hotProjects;
    }

    public void setHotProjects(List<BorrowingSimpleVo> hotProjects) {
        this.hotProjects = hotProjects;
    }

    public List<Map<String, String>> getBanners() {
        return banners;
    }

    public void setBanners(List<Map<String, String>> banners) {
        this.banners = banners;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserFinance getUserFinance() {
        return userFinance;
    }

    public void setUserFinance(UserFinance userFinance) {
        this.userFinance = userFinance;
    }
}
