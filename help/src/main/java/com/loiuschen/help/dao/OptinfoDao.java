package com.loiuschen.help.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.loiuschen.help.domain.Optinfo;

@Repository
public class OptinfoDao extends BaseDao<Optinfo> {
	
	private final String CLEAR_RECS = "delete from Optinfo";
	private final String GET_ALL_RESC_ORDEBY_TIME = "from Optinfo order by opttime desc";
	
	private final int OPTCODE_ADD_USER_JIZHANG = 0;
	private final int OPTCODE_MODIFY_USER_JIZHANG = 1;
	private final int OPTCODE_DELETE_USER_JIZHANG = 2;
	private final int OPTCODE_USER_TIXIAN_EXCUTE = 3;
	
	private final String OPTDES_ADD_USER_JIZHANG = "添加用户记账";
	private final String OPTDES_MODIFY_USER_JIZHANG = "修改用户记账";
	private final String OPTDES_DELETE_USER_JIZHANG = "删除用户记账";
	private final String OPTDES_USER_TIXIAN_EXCUTE = "确认用户提现";
	
	private static SimpleDateFormat df = null;
	
	static{
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
	}
	
	@SuppressWarnings("unchecked")
	public List<Optinfo>  loadAll()
	{
		return getHibernateTemplate().find(GET_ALL_RESC_ORDEBY_TIME);
	}
	
	public void SaveAddingUserJizhangDayOpt(String userId, String jizhangriqi, 
			String xinzengtouzie, String shouyie, String tixiane, String touzitianshu)
	{
		Optinfo optinfo = new Optinfo();
		String time = df.format(new Date());
		optinfo.setOptcode(OPTCODE_ADD_USER_JIZHANG);
		optinfo.setOptdescription(OPTDES_ADD_USER_JIZHANG);
		optinfo.setOpttime(time);
		
		String optcontent = String.format("用户ID=%s;记账日期：%s;新增投资额：%s;收益额：%s;提现 额：%s;投资天数：%s", 
				userId, jizhangriqi, xinzengtouzie, shouyie, tixiane, touzitianshu);
		optinfo.setOptcontent(optcontent);
		
		getHibernateTemplate().save(optinfo);
	}
	
	public void SaveModifyUserJizhangDayOpt(String userId, String jizhangriqi, 
			String xinzengtouzie, String shouyie, String tixiane, String touzitianshu)
	{
		Optinfo optinfo = new Optinfo();
		String time = df.format(new Date());
		optinfo.setOptcode(OPTCODE_MODIFY_USER_JIZHANG);
		optinfo.setOptdescription(OPTDES_MODIFY_USER_JIZHANG);
		optinfo.setOpttime(time);
		
		String optcontent = String.format("用户ID=%s;记账日期：%s;新增投资额：%s;收益额：%s;提现 额：%s;投资天数：%s", 
				userId, jizhangriqi, xinzengtouzie, shouyie, tixiane, touzitianshu);
		optinfo.setOptcontent(optcontent);
		
		getHibernateTemplate().save(optinfo);
	}
	
	public void SaveDelteUserJizhangDayOpt(String jizhangid,String userId,String jizhangriqi){
		Optinfo optinfo = new Optinfo();
		String time = df.format(new Date());
		optinfo.setOptcode(OPTCODE_DELETE_USER_JIZHANG);
		optinfo.setOptdescription(OPTDES_DELETE_USER_JIZHANG);
		optinfo.setOpttime(time);
		
		String optcontent = String.format("用户ID=%s;记账日期：%s", userId, jizhangriqi);
		optinfo.setOptcontent(optcontent);
		
		getHibernateTemplate().save(optinfo);
	}
	
	public void SaveTixianExcuteOpt(String id, String tixianriqi,String tixiane){
    	Optinfo optinfo = new Optinfo();
		String time = df.format(new Date());
		optinfo.setOptcode(OPTCODE_USER_TIXIAN_EXCUTE);
		optinfo.setOptdescription(OPTDES_USER_TIXIAN_EXCUTE);
		optinfo.setOpttime(time);
		
		String optcontent = String.format("用户ID=%s;提现日期：%s;提现额：%s", id, tixianriqi, tixiane);
		optinfo.setOptcontent(optcontent);
		
		getHibernateTemplate().save(optinfo);
	}
	
	public void clearOptinfo()
	{
		getHibernateTemplate().bulkUpdate(CLEAR_RECS);
	}
}
