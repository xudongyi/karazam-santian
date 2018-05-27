/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.vo.options;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.OptionsDataType;
import com.klzan.p2p.enums.OptionsDataUnit;
import com.klzan.p2p.enums.OptionsType;

/**
 * Created by suhao Date: 2016/11/11 Time: 9:45
 *
 * @version: 1.0
 */
public class OptionsVo extends BaseVo {
    /**
     * 配置类型
     */
    private OptionsType type;
    private String typeStr;

    /**
     * 数据类型
     */
    private OptionsDataType dataType;
    private String dataTypeStr;

    /**
     * 数据单位
     */
    private OptionsDataUnit dataUnit;
    private String dataUnitStr;

    /**
     * 名称
     */
    private String name;

    /**
     * 键
     */
    private String keyName;

    /**
     * 值
     */
    private String keyValue;

    public OptionsType getType() {
        return OptionsType.valueOf(typeStr);
    }

    public void setType(OptionsType type) {
        this.type = type;
    }

    public String getTypeStr() {
        return OptionsType.valueOf(typeStr).getDisplayName();
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public OptionsDataType getDataType() {
        return OptionsDataType.valueOf(dataTypeStr);
    }

    public void setDataType(OptionsDataType dataType) {
        this.dataType = dataType;
    }

    public String getDataTypeStr() {
        return OptionsDataType.valueOf(dataTypeStr).getDisplayName();
    }

    public void setDataTypeStr(String dataTypeStr) {
        this.dataTypeStr = dataTypeStr;
    }

    public OptionsDataUnit getDataUnit() {
        return OptionsDataUnit.valueOf(dataUnitStr);
    }

    public void setDataUnit(OptionsDataUnit dataUnit) {
        this.dataUnit = dataUnit;
    }

    public String getDataUnitStr() {
        return OptionsDataUnit.valueOf(dataUnitStr).getAddonName();
    }

    public void setDataUnitStr(String dataUnitStr) {
        this.dataUnitStr = dataUnitStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
}
