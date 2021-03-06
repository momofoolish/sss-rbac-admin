package com.jwss.sra.system.entity;

import java.io.Serializable;

import org.sagacity.sqltoy.config.annotation.Entity;
import org.sagacity.sqltoy.config.annotation.Id;
import org.sagacity.sqltoy.config.annotation.Column;
import java.time.LocalDateTime;

/**
 * @author jwss
 * @project sss-rbac-admin
 * @version 1.0.0
 * Table: sys_user,Remark:系统用户表  
 */
@Entity(tableName="sys_user")
public class User implements Serializable {

	private static final long serialVersionUID = 3203290987069291273L;
	
	/**
	 * jdbcType:VARCHAR
	 * 主键id
	 */
	@Id(strategy="generator",generator="org.sagacity.sqltoy.plugins.id.impl.UUIDGenerator")
	@Column(name="ID",length=32L,type=java.sql.Types.VARCHAR,nullable=false)
	private String id;
	
	/**
	 * jdbcType:INT
	 * 乐观锁
	 */
	@Column(name="REVISION",length=10L,type=java.sql.Types.INTEGER,nullable=true)
	private Integer revision;
	
	/**
	 * jdbcType:VARCHAR
	 * 创建人
	 */
	@Column(name="CREATE_BY",length=32L,type=java.sql.Types.VARCHAR,nullable=false)
	private String createBy;
	
	/**
	 * jdbcType:DATETIME
	 * 创建时间
	 */
	@Column(name="CREATE_TIME",length=19L,type=java.sql.Types.DATE,nullable=false)
	private LocalDateTime createTime;
	
	/**
	 * jdbcType:VARCHAR
	 * 更新人
	 */
	@Column(name="UPDATE_BY",length=32L,type=java.sql.Types.VARCHAR,nullable=true)
	private String updateBy;
	
	/**
	 * jdbcType:DATETIME
	 * 更新时间
	 */
	@Column(name="UPDATE_TIME",length=19L,type=java.sql.Types.DATE,nullable=true)
	private LocalDateTime updateTime;
	
	/**
	 * jdbcType:CHAR
	 * 删除状态;0删除 1未删除
	 */
	@Column(name="DELETE_STATUS",length=1L,type=java.sql.Types.CHAR,nullable=false)
	private String deleteStatus;
	
	/**
	 * jdbcType:VARCHAR
	 * 用户账号
	 */
	@Column(name="USERNAME",length=30L,type=java.sql.Types.VARCHAR,nullable=false)
	private String username;
	
	/**
	 * jdbcType:VARCHAR
	 * 用户昵称
	 */
	@Column(name="NICKNAME",length=30L,type=java.sql.Types.VARCHAR,nullable=false)
	private String nickname;
	
	/**
	 * jdbcType:VARCHAR
	 * 密码
	 */
	@Column(name="PASSWORD",length=32L,type=java.sql.Types.VARCHAR,nullable=false)
	private String password;
	
	/**
	 * jdbcType:CHAR
	 * 用户性别;0未知 1男 2女
	 */
	@Column(name="SEX",length=1L,type=java.sql.Types.CHAR,nullable=false)
	private String sex;
	
	/**
	 * jdbcType:VARCHAR
	 * 用户邮箱
	 */
	@Column(name="EMAIL",length=64L,type=java.sql.Types.VARCHAR,nullable=true)
	private String email;
	
	/**
	 * jdbcType:VARCHAR
	 * 手机号
	 */
	@Column(name="MOBILE_PHONE",length=11L,type=java.sql.Types.VARCHAR,nullable=true)
	private String mobilePhone;
	
	/**
	 * jdbcType:VARCHAR
	 * 部门id
	 */
	@Column(name="DEPARTMENT_ID",length=32L,type=java.sql.Types.VARCHAR,nullable=true)
	private String departmentId;
	
	/**
	 * jdbcType:CHAR
	 * 账号状态;0停用 1正常 2冻结 3封禁
	 */
	@Column(name="ACCOUNT_STATUS",length=1L,type=java.sql.Types.CHAR,nullable=false)
	private String accountStatus;
	
	/**
	 * jdbcType:VARCHAR
	 * 头像地址
	 */
	@Column(name="AVATAR",length=255L,type=java.sql.Types.VARCHAR,nullable=true)
	private String avatar;
	
	/**
	 * jdbcType:VARCHAR
	 * 最后登录IP
	 */
	@Column(name="LAST_LOGIN_IP",length=64L,type=java.sql.Types.VARCHAR,nullable=true)
	private String lastLoginIp;
	
	/**
	 * jdbcType:DATETIME
	 * 最后登录时间
	 */
	@Column(name="LAST_LOGIN_TIME",length=19L,type=java.sql.Types.DATE,nullable=true)
	private LocalDateTime lastLoginTime;
	
	/** default constructor */
	public User() {
	}
	
	/** pk constructor */
	public User(String id)
	{
		this.id=id;
	}
	
	/**
	 *@param id the id to set
	 */
	public User setId(String id) {
		this.id=id;
		return this;
	}
		
	/**
	 *@return the Id
	 */
	public String getId() {
	    return this.id;
	}
	
	/**
	 *@param revision the revision to set
	 */
	public User setRevision(Integer revision) {
		this.revision=revision;
		return this;
	}
		
	/**
	 *@return the Revision
	 */
	public Integer getRevision() {
	    return this.revision;
	}
	
	/**
	 *@param createBy the createBy to set
	 */
	public User setCreateBy(String createBy) {
		this.createBy=createBy;
		return this;
	}
		
	/**
	 *@return the CreateBy
	 */
	public String getCreateBy() {
	    return this.createBy;
	}
	
	/**
	 *@param createTime the createTime to set
	 */
	public User setCreateTime(LocalDateTime createTime) {
		this.createTime=createTime;
		return this;
	}
		
	/**
	 *@return the CreateTime
	 */
	public LocalDateTime getCreateTime() {
	    return this.createTime;
	}
	
	/**
	 *@param updateBy the updateBy to set
	 */
	public User setUpdateBy(String updateBy) {
		this.updateBy=updateBy;
		return this;
	}
		
	/**
	 *@return the UpdateBy
	 */
	public String getUpdateBy() {
	    return this.updateBy;
	}
	
	/**
	 *@param updateTime the updateTime to set
	 */
	public User setUpdateTime(LocalDateTime updateTime) {
		this.updateTime=updateTime;
		return this;
	}
		
	/**
	 *@return the UpdateTime
	 */
	public LocalDateTime getUpdateTime() {
	    return this.updateTime;
	}
	
	/**
	 *@param deleteStatus the deleteStatus to set
	 */
	public User setDeleteStatus(String deleteStatus) {
		this.deleteStatus=deleteStatus;
		return this;
	}
		
	/**
	 *@return the DeleteStatus
	 */
	public String getDeleteStatus() {
	    return this.deleteStatus;
	}
	
	/**
	 *@param username the username to set
	 */
	public User setUsername(String username) {
		this.username=username;
		return this;
	}
		
	/**
	 *@return the Username
	 */
	public String getUsername() {
	    return this.username;
	}
	
	/**
	 *@param nickname the nickname to set
	 */
	public User setNickname(String nickname) {
		this.nickname=nickname;
		return this;
	}
		
	/**
	 *@return the Nickname
	 */
	public String getNickname() {
	    return this.nickname;
	}
	
	/**
	 *@param password the password to set
	 */
	public User setPassword(String password) {
		this.password=password;
		return this;
	}
		
	/**
	 *@return the Password
	 */
	public String getPassword() {
	    return this.password;
	}
	
	/**
	 *@param sex the sex to set
	 */
	public User setSex(String sex) {
		this.sex=sex;
		return this;
	}
		
	/**
	 *@return the Sex
	 */
	public String getSex() {
	    return this.sex;
	}
	
	/**
	 *@param email the email to set
	 */
	public User setEmail(String email) {
		this.email=email;
		return this;
	}
		
	/**
	 *@return the Email
	 */
	public String getEmail() {
	    return this.email;
	}
	
	/**
	 *@param mobilePhone the mobilePhone to set
	 */
	public User setMobilePhone(String mobilePhone) {
		this.mobilePhone=mobilePhone;
		return this;
	}
		
	/**
	 *@return the MobilePhone
	 */
	public String getMobilePhone() {
	    return this.mobilePhone;
	}
	
	/**
	 *@param departmentId the departmentId to set
	 */
	public User setDepartmentId(String departmentId) {
		this.departmentId=departmentId;
		return this;
	}
		
	/**
	 *@return the DepartmentId
	 */
	public String getDepartmentId() {
	    return this.departmentId;
	}
	
	/**
	 *@param accountStatus the accountStatus to set
	 */
	public User setAccountStatus(String accountStatus) {
		this.accountStatus=accountStatus;
		return this;
	}
		
	/**
	 *@return the AccountStatus
	 */
	public String getAccountStatus() {
	    return this.accountStatus;
	}
	
	/**
	 *@param avatar the avatar to set
	 */
	public User setAvatar(String avatar) {
		this.avatar=avatar;
		return this;
	}
		
	/**
	 *@return the Avatar
	 */
	public String getAvatar() {
	    return this.avatar;
	}
	
	/**
	 *@param lastLoginIp the lastLoginIp to set
	 */
	public User setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp=lastLoginIp;
		return this;
	}
		
	/**
	 *@return the LastLoginIp
	 */
	public String getLastLoginIp() {
	    return this.lastLoginIp;
	}
	
	/**
	 *@param lastLoginTime the lastLoginTime to set
	 */
	public User setLastLoginTime(LocalDateTime lastLoginTime) {
		this.lastLoginTime=lastLoginTime;
		return this;
	}
		
	/**
	 *@return the LastLoginTime
	 */
	public LocalDateTime getLastLoginTime() {
	    return this.lastLoginTime;
	}

    @Override
	public String toString() {
		StringBuilder columnsBuffer=new StringBuilder();
		columnsBuffer.append("id=").append(getId()).append("\n");
		columnsBuffer.append("revision=").append(getRevision()).append("\n");
		columnsBuffer.append("createBy=").append(getCreateBy()).append("\n");
		columnsBuffer.append("createTime=").append(getCreateTime()).append("\n");
		columnsBuffer.append("updateBy=").append(getUpdateBy()).append("\n");
		columnsBuffer.append("updateTime=").append(getUpdateTime()).append("\n");
		columnsBuffer.append("deleteStatus=").append(getDeleteStatus()).append("\n");
		columnsBuffer.append("username=").append(getUsername()).append("\n");
		columnsBuffer.append("nickname=").append(getNickname()).append("\n");
		columnsBuffer.append("password=").append(getPassword()).append("\n");
		columnsBuffer.append("sex=").append(getSex()).append("\n");
		columnsBuffer.append("email=").append(getEmail()).append("\n");
		columnsBuffer.append("mobilePhone=").append(getMobilePhone()).append("\n");
		columnsBuffer.append("departmentId=").append(getDepartmentId()).append("\n");
		columnsBuffer.append("accountStatus=").append(getAccountStatus()).append("\n");
		columnsBuffer.append("avatar=").append(getAvatar()).append("\n");
		columnsBuffer.append("lastLoginIp=").append(getLastLoginIp()).append("\n");
		columnsBuffer.append("lastLoginTime=").append(getLastLoginTime()).append("\n");
		return columnsBuffer.toString();
	}
	
}
