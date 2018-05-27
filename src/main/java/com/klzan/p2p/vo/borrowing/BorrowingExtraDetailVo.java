package com.klzan.p2p.vo.borrowing;

import com.klzan.p2p.common.vo.BaseVo;

/**
 * Created by zhutao on 2017/5/3.
 */
public class BorrowingExtraDetailVo extends BaseVo{
    private Integer extraId;

    private Integer borrowing;

    private String extraKey;

    private String extraField;

    private String extraFieldAlias;

    private String extraFieldDes;

    private String extraFieldValue;

    public Integer getExtraId() {
        return extraId;
    }

    public void setExtraId(Integer extraId) {
        this.extraId = extraId;
    }

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

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
    }

    public String getExtraFieldAlias() {
        return extraFieldAlias;
    }

    public void setExtraFieldAlias(String extraFieldAlias) {
        this.extraFieldAlias = extraFieldAlias;
    }

    public String getExtraFieldDes() {
        return extraFieldDes;
    }

    public void setExtraFieldDes(String extraFieldDes) {
        this.extraFieldDes = extraFieldDes;
    }

    public String getExtraFieldValue() {
        return extraFieldValue;
    }

    public void setExtraFieldValue(String extraFieldValue) {
        this.extraFieldValue = extraFieldValue;
    }
}
