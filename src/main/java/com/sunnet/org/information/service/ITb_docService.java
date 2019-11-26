package com.sunnet.org.information.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.model.vie_Tb_DocInfo;
import com.sunnet.org.member.model.Tb_filelabel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_doc Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_docService extends IBaseService<Tb_doc>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_doc> list(PageBean pageBean,HttpServletRequest request);
	public List<Tb_doc> list(Class t,PageBean pageBean);
	/**
	 * 更新
	 * @param tb_doc
	 */
	public void update(Tb_doc tb_doc);
	/**
	 * 审核
	 * @param entityids
	 */
	public void updateStatus(Object[] entityids);
	
	public void delete(Object[] entityids);

	public void deletebyArr(String [] strArr)throws Exception;
	
	public List<Tb_doc> findorderby(PageBean pagebean);
	public List<Tb_doc> findfileviewcount();
	public List<Tb_doc> findfilegoodcount();
	public List<Tb_doc> findfilekeepcount();
	/*public String updateHead(MultipartFile file, long userId);*/
	public List<Tb_filelabel> find(String docid);
	public int updateSql(Integer id, String sql);
	public void updatePicList(StringBuilder hql);
	public int update2(String hql);
	public QueryResult<vie_Tb_DocInfo> getDocPage(PageBean pagebean,String wherename,String memberid,Class c);
	
	public QueryResult<Tb_doc> getDoc(PageBean pagebean,  String sql);

	/**
	 * author jinhao
	 * @param docid
	 * @return
	 */
	public Tb_doc getByKey(String docid);

	/**
	 * 获取作者等级经验
	 * @param docid
	 * @return
	 */
	public List<Object[]> getDocMemLevel(String docid);

	/**
	 * 获取该页所有视频
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<Object[]> getVideo(int startRow ,int endRow);

	public List<Object[]> getVideo1(int startRow ,int endRow);

	/**
	 * 所有作品
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<Object[]> getAll(int startRow ,int endRow);


	public  List<Tb_doc> getByManyIds(String[] ids);

	/**
	 * 获取该类别某页
	 * @param typeid
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<Object[]> getByFiletypeid(String typeid,int startRow ,int endRow);

	/**
	 * sql查
	 * @param docid
	 * @return
	 */
	public List<Object[]> getByDocId(String docid);

	/**
	 * 左右滑动 加载五张
	 * @param time
	 * @param typeId
	 * @param code
	 * @return
	 */
	public List<Map> getNextWorks(String time, String typeId, String code, String memberId);

	public  List<Object[]> getWorksInfo(String docid);

	/**
	 * 修改浏览数量
	 * @param docid
	 * @param viewnum
	 */
	public void updateViewNum(String docid,int viewnum);
	/**
	 * 修改评论数量
	 * @param docid
	 * @param commentnum
	 */
	public void updateCommentscount(String docid,int commentnum);
	/**
	 * 修改公开状态
	 * @param docid
	 * @param ispublic
	 */
	public void updateIspublic(String docid,int ispublic);

	/**
	 * 被关注人的作品列表
	 * @param memberid
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<Object[]> getFocusUserDoc(String memberid,int startRow,int endRow);

	/**
	 * 随机5个最近的作品1
	 * @return
	 */
	List<Object[]> getDocList1();

	/**
	 * 随机5个最近的作品
	 * @return
	 */
	public List<Object[]> getDocList(int[] is);

	/**
	 * 保存上传文件以及相关信息
	 */
	public void addDoc(Tb_doc tb_doc,Object[] memInfo,String label,String matchId,String memberId,String space,String spotId);

}
