package com.sunnet.org.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

public class VSysNoteUserType {
	private Integer id;

	private String fdUserId;// 用户
	
	private String fdUserName;//登录账号
	
	private String fdName;// 操作类型名称
	
	private String fdRoleName;//角色名称
	
	private String fdOperationTypeId; // 操作类型

	private String fdOperationContent; // 操作内容

	private Date fdOperationTime; // 操作时间
	
	private String fdIpAddress; // ip地址
}
