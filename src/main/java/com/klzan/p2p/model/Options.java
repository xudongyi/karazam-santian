package com.klzan.p2p.model;

import com.klzan.p2p.enums.OptionsDataType;
import com.klzan.p2p.enums.OptionsDataUnit;
import com.klzan.p2p.enums.OptionsType;
import com.klzan.p2p.model.base.BaseSortModel;

import javax.persistence.*;

/**
 * 参数表
 */
@Entity
@Table(name = "karazam_options")
public class Options extends BaseSortModel {
    /**
     * 配置类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OptionsType type;

    /**
     * 数据类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OptionsDataType dataType;

    /**
     * 数据单位
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OptionsDataUnit dataUnit;

    /**
     * 名称
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * 键
     */
    @Column(nullable = false, length = 50)
    private String keyName;

    /**
     * 值
     */
    @Column(nullable = false, length = 500)
    private String keyValue;

    public Options() {
    }

    public Options(OptionsType type, OptionsDataType dataType, OptionsDataUnit dataUnit, String name, String keyName, String keyValue) {
        this.type = type;
        this.dataType = dataType;
        this.dataUnit = dataUnit;
        this.name = name;
        this.keyName = keyName;
        this.keyValue = keyValue;
    }

    public OptionsType getType() {
        return type;
    }

    public OptionsDataType getDataType() {
        return dataType;
    }

    public OptionsDataUnit getDataUnit() {
        return dataUnit;
    }

    public String getName() {
        return name;
    }

    public String getKeyName() {
        return keyName;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void update(OptionsType type, OptionsDataType dataType, OptionsDataUnit dataUnit, String name, String keyName, String keyValue) {
        this.type = type;
        this.dataType = dataType;
        this.dataUnit = dataUnit;
        this.name = name;
        this.keyName = keyName;
        this.keyValue = keyValue;
    }

    public void update(String keyValue) {
        this.keyValue = keyValue;
    }
}
