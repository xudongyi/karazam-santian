package com.klzan.p2p.model;

import com.klzan.p2p.enums.TemplateContentType;
import com.klzan.p2p.enums.TemplatePurpose;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;

/***
 * 短信模板
 */
@Entity
@Table(name = "karazam_template_content")
public class TemplateContent extends BaseModel {

	/**
	 * 模板名称
	 */
	@Column(nullable = false, length = 32)
	private String name;
	/**
	 * 模板内容
	 */
	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;
	/**
	 * 模板类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 64)
	private TemplateContentType templateContentType;
	/**
	 * 模板用途
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 64)
	private TemplatePurpose templatePurpose;
	/**
	 * 用途
	 */
	private String memo;

	/**
	 * 是否启用
	 */
	private Boolean enable = Boolean.TRUE;

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}

	public TemplateContentType getTemplateContentType() {
		return templateContentType;
	}

	public TemplatePurpose getTemplatePurpose() {
		return templatePurpose;
	}

	public String getMemo() {
		return memo;
	}

	public Boolean getEnable() {
		return enable;
	}
}