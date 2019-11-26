package com.sunnet.org.member.vo;

import com.sunnet.org.member.model.Tb_member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tb_member 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_memberUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_member> list) {
		List item = new ArrayList();
		for (Tb_member obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("devicid", obj.getDevicid());
			if ( obj.getUsersname()!=null) {
				map.put("usersname", obj.getUsersname());
			} else {
				map.put("usersname", "");
			} 
			if ( obj.getHeadimg()!=null) {
				map.put("headimg", obj.getHeadimg());
			} else {
				map.put("headimg", "");
			} 
			if ( obj.getIntroduction()!=null) {
				map.put("introduction", obj.getIntroduction());
			} else {
				map.put("introduction", "");
			} 
			if ( obj.getRealname()!=null) {
				map.put("realname", obj.getRealname());
			} else {
				map.put("realname", "");
			}
			if ( obj.getMobilenumber()!=null) {
				map.put("mobilenumber", obj.getMobilenumber());
			} else {
				map.put("mobilenumber", "");
			}
			if ( obj.getEmail()!=null) {
				map.put("email", obj.getEmail());
			} else {
				map.put("email", "");
			}
			if ( obj.getAddress()!=null) {
				map.put("address", obj.getAddress());
			} else {
				map.put("address", "");
			}
			if ( obj.getQqnumber()!=null) {
				map.put("qqnumber", obj.getQqnumber());
			} else {
				map.put("qqnumber", "");
			}
			if ( obj.getWeixinnumber()!=null) {
				map.put("weixinnumber", obj.getWeixinnumber());
			} else {
				map.put("weixinnumber", "");
			}
			if ( obj.getWeibonumber()!=null) {
				map.put("weibonumber", obj.getWeibonumber());
			} else {
				map.put("weibonumber", "");
			}
			if ( obj.getBirthday()!=null) {
				map.put("birthday", obj.getBirthday());
			} else {
				map.put("birthday", "");
			}
			if ( obj.getGender()>0) {
				map.put("gender", obj.getGender());
			} else {
				map.put("gender", 0);
			}
			if ( obj.getLevelintegral()>0) {
				map.put("levelintegral", obj.getLevelintegral());
			} else {
				map.put("levelintegral", 0);
			}
			if ( obj.getStatus()!= null) {
				map.put("status", obj.getStatus());
			} else {
				map.put("status", 0);
			}
			if ( obj.getWallet()!= null) {
				map.put("wallet", obj.getWallet());
			} else {
				map.put("wallet", "");
			}
			if ( obj.getCapacity()!= null) {
				map.put("capacity", obj.getCapacity());
			} else {
				map.put("capacity", "");
			}
			if ( obj.getRemainingcapacity()!= null) {
				map.put("remainingcapacity", obj.getRemainingcapacity());
			} else {
				map.put("remainingcapacity", "");
			}
			if (obj.getRegtime()!= null) {
				map.put("regtime",obj.getRegtime());
			} else {
				map.put("regtime", "");
			}
			if (obj.getAlipayname() != null) {
				map.put("alipayname", obj.getAlipayname());
			} else {
				map.put("alipayname", "");
			}
			if (obj.getLevelid() != null) {
				map.put("levelid", obj.getLevelid().getId());
				map.put("levelname", obj.getLevelid().getLevelname());
			} else {
				map.put("levelid", "");
			}
			if (obj.getGroupid() != null) {
				if(obj.getGroupid().getId()!=null){
					map.put("groupid", obj.getGroupid().getId());
				}else{
					map.put("groupid", "");
				}
				if(obj.getGroupid().getGroupname()!=null){
					map.put("gusersname", obj.getGroupid().getGroupname());
				}else{
					map.put("gusersname", "");
				}
				if(obj.getGroupid().getNotes()!=null){
					map.put("notes", obj.getGroupid().getNotes());
				}else{
					map.put("notes", "");
				}
			} else {
				map.put("groupid", "");
				map.put("notes", "");
				map.put("groupname", "");
			}
			if (obj.getBackgroundimgoneimg() != null) {
				map.put("backgroundimgoneimg", obj.getBackgroundimgoneimg());
			} else {
				map.put("backgroundimgoneimg", "");
			}
			if (obj.getBackgroundtwoimg() != null) {
				map.put("backgroundtwoimg", obj.getBackgroundtwoimg());
			} else {
				map.put("backgroundtwoimg", "");
			}
			if (obj.getBackgroundthreeimg() != null) {
				map.put("backgroundthreeimg", obj.getBackgroundthreeimg());
			} else {
				map.put("backgroundthreeimg", "");
			}
			

			item.add(map);
		}
		return item;
	}

	/**
	 * 返回单个对象
	 * 
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_member obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		 
		map.put("id", obj.getId());
		if ( obj.getUsersname()!=null) {
			map.put("usersname", obj.getUsersname());
		} else {
			map.put("usersname", "");
		} 
		if ( obj.getHeadimg()!=null) {
			map.put("headimg", obj.getHeadimg());
		} else {
			map.put("headimg", "");
		} 
		if ( obj.getIntroduction()!=null) {
			map.put("introduction", obj.getIntroduction());
		} else {
			map.put("introduction", "");
		} 
		if ( obj.getRealname()!=null) {
			map.put("realname", obj.getRealname());
		} else {
			map.put("realname", "");
		}
		if ( obj.getMobilenumber()!=null) {
			map.put("mobilenumber", obj.getMobilenumber());
		} else {
			map.put("mobilenumber", "");
		}
		if ( obj.getEmail()!=null) {
			map.put("email", obj.getEmail());
		} else {
			map.put("email", "");
		}
		if ( obj.getAddress()!=null) {
			map.put("address", obj.getAddress());
		} else {
			map.put("address", "");
		}
		if ( obj.getQqnumber()!=null) {
			map.put("qqnumber", obj.getQqnumber());
		} else {
			map.put("qqnumber", "");
		}
		if ( obj.getWeixinnumber()!=null) {
			map.put("weixinnumber", obj.getWeixinnumber());
		} else {
			map.put("weixinnumber", "");
		}
		if ( obj.getWeibonumber()!=null) {
			map.put("weibonumber", obj.getWeibonumber());
		} else {
			map.put("weibonumber", "");
		}
		if ( obj.getBirthday()!=null) {
			map.put("birthday", obj.getBirthday());
		} else {
			map.put("birthday", "");
		}
		if ( obj.getGender()>0) {
			map.put("gender", obj.getGender());
		} else {
			map.put("gender", 0);
		}
		if ( obj.getLevelintegral()>0) {
			map.put("levelintegral", obj.getLevelintegral());
		} else {
			map.put("levelintegral", 0);
		}
		if ( obj.getStatus()!= null) {
			map.put("status", obj.getStatus());
		} else {
			map.put("status", 0);
		}
		if ( obj.getWallet()!= null) {
			map.put("wallet", obj.getWallet());
		} else {
			map.put("wallet", "");
		}
		if ( obj.getCapacity()!= null) {
			map.put("capacity", obj.getCapacity());
		} else {
			map.put("capacity", "");
		}
		if ( obj.getRemainingcapacity()!= null) {
			map.put("remainingcapacity", obj.getRemainingcapacity());
		} else {
			map.put("remainingcapacity", "");
		}
		if (obj.getRegtime()!= null) {
			map.put("regtime",obj.getRegtime());
		} else {
			map.put("regtime", "");
		}
		if (obj.getAlipayname() != null) {
			map.put("alipayname", obj.getAlipayname());
		} else {
			map.put("alipayname", "");
		}
		if (obj.getLevelid() != null) {
			map.put("levelid", obj.getLevelid().getLevelname());
		} else {
			map.put("levelid", "");
		}
		if (obj.getGroupid() != null) {
			if(obj.getGroupid().getId()!=null){
				map.put("groupid", obj.getGroupid().getId());
			}else{
				map.put("groupid", "");
			}
			if(obj.getGroupid().getGroupname()!=null){
				map.put("gusersname", obj.getGroupid().getGroupname());
			}else{
				map.put("gusersname", "");
			}
			if(obj.getGroupid().getNotes()!=null){
				map.put("notes", obj.getGroupid().getNotes());
			}else{
				map.put("notes", "");
			}
		} else {
			map.put("groupid", "");
			map.put("notes", "");
			map.put("groupname", "");
		}
		if (obj.getBackgroundimgoneimg() != null) {
			map.put("backgroundimgoneimg", obj.getBackgroundimgoneimg());
		} else {
			map.put("backgroundimgoneimg", "");
		}
		if (obj.getBackgroundtwoimg() != null) {
			map.put("backgroundtwoimg", obj.getBackgroundtwoimg());
		} else {
			map.put("backgroundtwoimg", "");
		}
		if (obj.getBackgroundthreeimg() != null) {
			map.put("backgroundthreeimg", obj.getBackgroundthreeimg());
		} else {
			map.put("backgroundthreeimg", "");
		}
		return map;
	}
	/**
	 * 返回单个对象
	 *author jinhao
	 * @param obj
	 * @return
	 */
	public static Map getControllerMapJ(Tb_member obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("user_id", obj.getId());
		if (obj.getIdcard() != null){
			map.put("user_idCard", obj.getIdcard());
		}else
		{
			map.put("user_idCard", "");
		}
		if ( obj.getUsersname()!=null) {
			map.put("user_name", obj.getUsersname());
		} else {
			map.put("user_name", "");
		}
		if ( obj.getHeadimg()!=null) {
			map.put("portrait", obj.getHeadimg());
		} else {
			map.put("portrait", "");
		}
		if ( obj.getIntroduction()!=null) {
			map.put("user_profile", obj.getIntroduction());
		} else {
			map.put("user_profile", "");
		}
		if ( obj.getRealname()!=null) {
			map.put("realName", obj.getRealname());
		} else {
			map.put("realName", "");
		}
		if ( obj.getMobilenumber()!=null) {
			map.put("user_phone", obj.getMobilenumber());
		} else {
			map.put("user_phone", "");
		}
		if ( obj.getEmail()!=null) {
			map.put("email", obj.getEmail());
		} else {
			map.put("email", "");
		}
		if ( obj.getAddress()!=null) {
			map.put("address", obj.getAddress());
		} else {
			map.put("address", "");
		}
		if ( obj.getQqnumber()!=null) {
			map.put("qq", obj.getQqnumber());
		} else {
			map.put("qq", "");
		}
		if ( obj.getWeixinnumber()!=null) {
			map.put("weixin", obj.getWeixinnumber());
		} else {
			map.put("weixin", "");
		}
		if ( obj.getWeibonumber()!=null) {
			map.put("weibo", obj.getWeibonumber());
		} else {
			map.put("weibo", "");
		}
		if ( obj.getBirthday()!=null) {
			map.put("user_birthday", obj.getBirthday());
		} else {
			map.put("user_birthday", "");
		}
		if ( obj.getGender() != null) {
			if(obj.getGender() == 0)
				map.put("user_sex", "男");
			if(obj.getGender() == 1)
				map.put("user_sex", "女");
		} else {
			map.put("user_sex", "");
		}
		if (obj.getAlipayname() != null) {
			map.put("alipayname", obj.getAlipayname());
		} else {
			map.put("alipayname", "");
		}
		if (obj.getLevelid() != null) {
			map.put("user_rank", obj.getLevelid().getLevelname());
		} else {
			map.put("user_rank", "");
		}
		map.put("background",obj.getBackgroundimgoneimg());
		return map;
	}
	/**
	 * 返回全部list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerListAll(List<Tb_member> list) {
		List item = new ArrayList();
		for (Tb_member obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if ( obj.getUsersname()!=null) {
				map.put("usersname", obj.getUsersname());
			} else {
				map.put("usersname", "");
			} 
			if ( obj.getHeadimg()!=null) {
				map.put("headimg", obj.getHeadimg());
			} else {
				map.put("headimg", "");
			} 
			if ( obj.getIntroduction()!=null) {
				map.put("introduction", obj.getIntroduction());
			} else {
				map.put("introduction", "");
			} 
			if ( obj.getRealname()!=null) {
				map.put("realname", obj.getRealname());
			} else {
				map.put("realname", "");
			}
			if ( obj.getMobilenumber()!=null) {
				map.put("mobilenumber", obj.getMobilenumber());
			} else {
				map.put("mobilenumber", "");
			}
			if ( obj.getEmail()!=null) {
				map.put("email", obj.getEmail());
			} else {
				map.put("email", "");
			}
			if ( obj.getAddress()!=null) {
				map.put("address", obj.getAddress());
			} else {
				map.put("address", "");
			}
			if ( obj.getQqnumber()!=null) {
				map.put("qqnumber", obj.getQqnumber());
			} else {
				map.put("qqnumber", "");
			}
			if ( obj.getWeixinnumber()!=null) {
				map.put("weixinnumber", obj.getWeixinnumber());
			} else {
				map.put("weixinnumber", "");
			}
			if ( obj.getWeibonumber()!=null) {
				map.put("weibonumber", obj.getWeibonumber());
			} else {
				map.put("weibonumber", "");
			}
			if ( obj.getBirthday()!=null) {
				map.put("birthday", obj.getBirthday());
			} else {
				map.put("birthday", "");
			}
			if ( obj.getGender()>0) {
				map.put("gender", obj.getGender());
			} else {
				map.put("gender", 0);
			}
			if ( obj.getLevelintegral()>0) {
				map.put("levelintegral", obj.getLevelintegral());
			} else {
				map.put("levelintegral", 0);
			}
			if ( obj.getStatus()!= null) {
				map.put("status", obj.getStatus());
			} else {
				map.put("status", 0);
			}
			if ( obj.getWallet()!= null) {
				map.put("wallet", obj.getWallet());
			} else {
				map.put("wallet", "");
			}
			if ( obj.getCapacity()!= null) {
				map.put("capacity", obj.getCapacity());
			} else {
				map.put("capacity", "");
			}
			if ( obj.getRemainingcapacity()!= null) {
				map.put("remainingcapacity", obj.getRemainingcapacity());
			} else {
				map.put("remainingcapacity", "");
			}
			if (obj.getRegtime()!= null) {
				map.put("regtime",obj.getRegtime());
			} else {
				map.put("regtime", "");
			}
			if (obj.getAlipayname() != null) {
				map.put("alipayname", obj.getAlipayname());
			} else {
				map.put("alipayname", "");
			}
			if (obj.getLevelid() != null) {
				map.put("levelid", obj.getLevelid().getLevelname());
			} else {
				map.put("levelid", "");
			}
			if (obj.getGroupid() != null) {
				if(obj.getGroupid().getId()!=null){
					map.put("groupid", obj.getGroupid().getId());
				}else{
					map.put("groupid", "");
				}
				if(obj.getGroupid().getGroupname()!=null){
					map.put("gusersname", obj.getGroupid().getGroupname());
				}else{
					map.put("gusersname", "");
				}
				if(obj.getGroupid().getNotes()!=null){
					map.put("notes", obj.getGroupid().getNotes());
				}else{
					map.put("notes", "");
				}
			} else {
				map.put("groupid", "");
				map.put("notes", "");
				map.put("groupname", "");
			}
			if (obj.getBackgroundimgoneimg() != null) {
				map.put("backgroundimgoneimg", obj.getBackgroundimgoneimg());
			} else {
				map.put("backgroundimgoneimg", "");
			}
			if (obj.getBackgroundtwoimg() != null) {
				map.put("backgroundtwoimg", obj.getBackgroundtwoimg());
			} else {
				map.put("backgroundtwoimg", "");
			}
			if (obj.getBackgroundthreeimg() != null) {
				map.put("backgroundthreeimg", obj.getBackgroundthreeimg());
			} else {
				map.put("backgroundthreeimg", "");
			}

			item.add(map);
		}
		return item;
	}

}
