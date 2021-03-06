package com.jwss.sra.system.param.dictionary;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author jwss
 * @project sss-rbac-admin
 * @version 1.0.0
 * @description sys_dictionary,系统字典表  
 */
public class DictionaryUpdateParam implements Serializable {
	private static final long serialVersionUID = -1767974905556894292L;

	@ApiModelProperty(value = "主键ID", required = true)
	@NotBlank(message = "主键ID为空")
	private String id;

	@ApiModelProperty("父级ID")
	private String parentId;
	
	@ApiModelProperty("字典名称")
	private String dictionaryName;
	
	@ApiModelProperty("备注")
	private String remark;
	
	@ApiModelProperty("排序号")
	private Integer sort;
	
	@ApiModelProperty("启用状态;0关闭 1启用")
	private String enableStatus;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDictionaryName() {
		return dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
