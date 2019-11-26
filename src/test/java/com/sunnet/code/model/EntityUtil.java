//package com.sunnet.code.model;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import com.sunnet.code.util.ColumnData;
//import com.sunnet.code.util.ModelUtil;
//import com.sunnet.code.util.TestUtil;
//
///**
// * 生成
// * @author Administrator
// *
// */
//public class EntityUtil
//{
//	public static List<EntityModule> list = new ArrayList<EntityModule>();
//	
//	public EntityUtil(){}
//	public EntityUtil(String Model){
//		list.clear();
//		if(Model.equals("FundManagement")){
//			 getClassFundManagement();
//		}
//		if(Model.equals("FundBuyers")){
//			getClassFundBuyers();
//		}
//		if(Model.equals("Member")){
//			getClassVlue();
//		}
//		if(Model.equals("AboutUs")){
//			getClassAboutUs();
//		}
//		if(Model.equals("Information")){
//			getClassInformation();
//		}
//		if(Model.equals("ApplicationCase")){
//			getClassApplicationCase();
//		}
//		if(Model.equals("ServiceRequirements")){
//			getClassServiceRequirements();
//		}
//		if(Model.equals("Banner")){
//			getClassBanner();
//		}
//		if(Model.equals("CustomerMessage")){
//			getClassCustomerMessage();
//		}
//	}
//	
//	public static void getClassFundManagement(){
//		list.add(new EntityModule("String","fundName"));
//		list.add(new EntityModule("long","createDateTime"));
//	}
//	
//	public static void getClassFundBuyers(){
//		list.add(new EntityModule("String","buyers"));
//		list.add(new EntityModule("Integer","documentType"));
//		list.add(new EntityModule("String","documentNumber"));
//	}
//	public static void getClassVlue(){
//		list.add(new EntityModule("String","fdName"));
//		list.add(new EntityModule("String","FdUrl"));
//		list.add(new EntityModule("String","FdContent"));
//	}
//	
//	public static void getClassAboutUs(){
//		list.add(new EntityModule("String","aboutsName"));
//		list.add(new EntityModule("String","aboutContent"));
//	}
//	public static void getClassInformation(){
//		list.add(new EntityModule("String","informationName"));
//		list.add(new EntityModule("String","informationTitle"));
//		list.add(new EntityModule("String","informationContent"));
//		list.add(new EntityModule("long","createDateTime"));
//		list.add(new EntityModule("String","informationUrl"));
//	}
//	public static void getClassApplicationCase(){
//		list.add(new EntityModule("String","applicationTitle"));
//		list.add(new EntityModule("String","applicationContent"));
//		list.add(new EntityModule("String","applicationUrl"));
//		list.add(new EntityModule("long","createDateTime"));
//	}
//	public static void getClassServiceRequirements(){
//		list.add(new EntityModule("String","serviceRequirementsUrl"));
//		list.add(new EntityModule("String","serviceRequirementscontent"));
//		list.add(new EntityModule("long","createDateTime"));
//	}
//	public static void getClassBanner(){
//		list.add(new EntityModule("String","Bannerurl"));
//		list.add(new EntityModule("String","Bannerlink"));
//		list.add(new EntityModule("long","createDateTime"));
//	}
//	public static void getClassCustomerMessage(){
//		list.add(new EntityModule("String","userName"));
//		list.add(new EntityModule("String","userPhone"));
//		list.add(new EntityModule("String","userContent"));
//		list.add(new EntityModule("long","createDateTime"));
//	}
//	
//	/**
//	 * 返回的 entityVlue
//	 * @return
//	 */
//	public static  String getStringCode(){
//		StringBuffer getset = new StringBuffer();
//		//手动写的
//		for (EntityModule entityModule : list)
//		{
//			String cloumnName= entityModule.getModelVlue();
//			if(entityModule.getModelVlue().contains("_")){
//				cloumnName="";
//				String [] sp =entityModule.getModelVlue().split("_");
//				for (int i = 0; i < sp.length; i++)
//				{
//					if(i == 0){
//						cloumnName += sp[i];
//					}else{
//						cloumnName += TestUtil.getToClassName(sp[i]);
//					}
//				}
//			}
//			//大写
//			String BigCloumnName=TestUtil.getToClassName(cloumnName);
//			
//			getset.append("\t private "+entityModule.getModelType()+" "+cloumnName+";  ");
//			if(ModelUtil.total.get(entityModule.getModelVlue()) != null){
//				getset.append("//"+ModelUtil.total.get(entityModule.getModelVlue()));
//			}
//			getset.append("  \r\r");
//			getset.append("\t public "+entityModule.getModelType()+" get"+BigCloumnName+"() \r");
//			getset.append("\t {                       \r");
//			getset.append("\t return "+entityModule.getModelVlue()+";        	   \r");
//			getset.append("\t }                       \r\r");
//			getset.append("\t public void set"+BigCloumnName+"("+entityModule.getModelType()+" "+entityModule.getModelVlue()+") \r");
//			getset.append("\t {                       \r");
//			getset.append("\t this."+entityModule.getModelVlue()+" = "+entityModule.getModelVlue()+";       \r\r");
//			getset.append("\t }				 \r\r");
//		}
//		return getset.toString();
//	}
//	
//	public static void main(String[] args)
//	{
//		EntityUtil en =new EntityUtil("FundManagement");
//		System.out.println(getStringCode());
//	}
//
//}
