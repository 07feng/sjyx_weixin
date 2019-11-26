package com.sunnet.framework.enums;

public enum OperateLockEnum {
	//登录时密码输错5次，则该用户锁定30分钟
	LOGIN_ERROR_COUNT("loginErrorCount_");
	
	public final String lockPrefix;


	private OperateLockEnum(String lockPrefix){
		this.lockPrefix = lockPrefix;
	}
}
