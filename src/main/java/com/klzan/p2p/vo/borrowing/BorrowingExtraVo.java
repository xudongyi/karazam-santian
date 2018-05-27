package com.klzan.p2p.vo.borrowing;

import com.klzan.p2p.common.vo.BaseVo;

import java.util.List;

/**
 * Created by zhutao on 2017/5/3.
 */
public class BorrowingExtraVo extends BaseVo{
    private Integer borrowing;

    private String extraKey;

    private String extraKeyAlias;

    private String extraKeyDes;

    private String extraValue;

    private List<BorrowingExtraDetailVo> details;

    public Integer getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    public String getExtraKey() {
        return extraKey;
    }

    public void setExtraKey(String extraKey) {
        this.extraKey = extraKey;
    }

    public String getExtraKeyAlias() {
        return extraKeyAlias;
    }

    public void setExtraKeyAlias(String extraKeyAlias) {
        this.extraKeyAlias = extraKeyAlias;
    }

    public String getExtraKeyDes() {
        return extraKeyDes;
    }

    public void setExtraKeyDes(String extraKeyDes) {
        this.extraKeyDes = extraKeyDes;
    }

    public String getExtraValue() {
        return extraValue;
    }

    public void setExtraValue(String extraValue) {
        this.extraValue = extraValue;
    }

    public List<BorrowingExtraDetailVo> getDetails() {
        return details;
    }

    public void setDetails(List<BorrowingExtraDetailVo> details) {
        this.details = details;
    }

}
