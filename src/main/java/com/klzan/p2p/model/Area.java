/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseSortModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity - 地区
 *
 * @author: chenxinglin  Date: 2016/10/19 Time: 17:15
 */
@Entity
@Table(name = "karazam_area")
public class Area extends BaseSortModel {

  /**名称*/
  @Column(nullable = false, length = 100)
  private String name;

  /**全称*/
  @Column(nullable = false, length = 100)
  private String fullName;

  /**树路径*/
  @Column(nullable = false)
  private String treePath;

  /**层级*/
  @Column(nullable = false)
  private Integer grade;

  /**上级地区*/
  private Integer parent;

  /**地区代码*/
  @Column
  private String code;

  public Area() {
  }

  public Area(String name, String fullName, String treePath, Integer grade, Integer parent) {
    this.name = name;
    this.fullName = fullName;
    this.treePath = treePath;
    this.grade = grade;
    this.parent = parent;
  }

  public String getName() {
    return name;
  }

  public String getFullName() {
    return fullName;
  }

  public String getTreePath() {
    return treePath;
  }

  public Integer getGrade() {
    return grade;
  }

  public Integer getParent() {
    return parent;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public void setTreePath(String treePath) {
    this.treePath = treePath;
  }

  public void setGrade(Integer grade) {
    this.grade = grade;
  }

  public void setParent(Integer parent) {
    this.parent = parent;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}