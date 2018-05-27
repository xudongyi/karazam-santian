package com.klzan.p2p.model.base;

import org.apache.commons.lang3.builder.CompareToBuilder;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;

/**
 * Entity - 排序基类
 *
 * @author suhao
 * @version 1.0
 */
@MappedSuperclass
public abstract class BaseSortModel extends BaseModel implements Comparable<BaseSortModel> {

    /**
     * “排序”属性
     */
    public static final String SORT_PROP = "sort";

    /**
     * 排序
     */
    @Min(0)
    public Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 重写compareTo方法
     *
     * @param sortEntity 排序基类及其子类
     * @return 比较结果
     */
    @Override
    public int compareTo(BaseSortModel sortEntity) {
        return new CompareToBuilder().append(getSort(), sortEntity.getSort()).append(getId(), sortEntity.getId())
                .toComparison();
    }

}