package ${com}.${type}.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ${com}.${type}.model.${bigName};

/**
 * ${minName} 返回数据的加载
 * @author 强强
 *
 */
public class ${bigName}Util {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<${bigName}> list){
		List item= new ArrayList();
		for (${bigName} obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			${toMap}
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(${bigName} obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		${toMap}
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<${bigName}> list) {
		List item = new ArrayList();
		for (${bigName} obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			${toMap}
			item.add(map);
		}
		return item;
	}

}
