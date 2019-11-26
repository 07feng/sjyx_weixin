package com.sunnet.org.member.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.member.model.Tb_member;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_member Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_memberService extends IBaseService<Tb_member>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_member> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param tb_member
	 */
	public void update(Tb_member tb_member);
	public void updateUserPhone(String memberId, Tb_member tb_member);
	public void updateUserOpenId(Tb_member tb_member,String memberId,String openId);
	public List<Tb_member> getAllByWhere( String sql);
	public void delete(Object[] entityids);
	/*public void updateUserDate(Tb_member tb_member) ;*/
	
	public Tb_member getByKey(String memberid);

	/**
	 * 获取用户等级经验，容量等
	 * @param memberId
	 * @return
	 */
	public Object[] getMemInfo(String memberId);

	public  List<Object[]> getByMenid(String memberid);

	/**
	 * 获取作者等级经验
	 * @param memberId
	 * @return
	 */
	public List<Object[]> getMemLevel(String memberId);

	/**
	 * author jinhao
	 * 通过电话号码获取用户信息
	 * @param phone
	 * @return
	 */
	public List<Tb_member> getByPhone(String phone);

	/**
	 * author jinhao
	 * 获取个人主页访问量
	 * @param memberId
	 * @return
	 */
	public int getViewnum(String memberId);

    /**
     * 修改云库容量
     * @param space
     * @param memberId
     */
	public void updateCapacity(String space,String memberId);

	public void updateViewnum(int num,String id);

	public void updateOpenId(String openId,String memberId);

	public void updateMemberInfo(String sql,Object... values);

	public void updatelevel(Double levelintegral,String memberId);

	public void updatelevel2(Integer level, Double levelintegral,String memberId);

	/**
	 * 修改背景图
	 * @param bgPath
	 * @param menberId
	 */
	public void updateBg(String bgPath,String menberId);

	/**
	 * 修改头像
	 * @param headPath
	 * @param menberid
	 */
	public void updateHeading(String headPath,String menberid);
}
