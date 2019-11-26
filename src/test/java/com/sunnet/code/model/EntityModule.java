package com.sunnet.code.model;

import com.sunnet.code.util.ModelUtil;

/**
 * 实体类生成
 * 
 * @author 强强
 *
 *         时间: 2017年2月20日
 */
public class EntityModule
{

	private String modelType;

	private String modelVlue;

	public EntityModule()
	{
	}

	public EntityModule(String modelType, String modelVlue)
	{
		this.modelType = modelType;
		this.modelVlue = modelVlue;
	}

	public String getModelType()
	{
		return modelType;
	}

	public void setModelType(String modelType)
	{
		this.modelType = modelType;
	}

	public String getModelVlue()
	{
		return modelVlue;
	}

	public void setModelVlue(String modelVlue)
	{
		this.modelVlue = modelVlue;
	}

}
