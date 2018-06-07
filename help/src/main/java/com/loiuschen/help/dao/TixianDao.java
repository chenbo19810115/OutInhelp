package com.loiuschen.help.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.loiuschen.help.domain.Tixian;

@Repository
public class TixianDao extends BaseDao<Tixian> {
	protected final String GET_USER_NOT_READ_EXUCTED = "from Tixian where buserread = 0 and btixian = 1 and id=?";
	protected final String UpdateMessageStatus = "update Tixian set buserread=1 where tixianid=?";
	protected final String UPDATE_USER_DAY_TIXIAN = "update Tixian set tixiane=? where id=? and tixianriqi=?";
	protected final String GET_USER_DAY_TIXIAN = "from Tixian where id=? and tixianriqi=?";
	protected final String DELETE_USER_DAY_TIXIAN = "delete from Tixian where id=? and tixianriqi=?";
	protected final String GET_ALL_NOT_EXCUTE_TIXIAN = "from Tixian where btixian=0";
	protected final String EXCUTE_TIXIAN = "update Tixian set btixian=1 where tixianid=?";
	private final String GET_USER_SUM_TIXIAN = "select sum(tixiane) as zongtixiane from Tixian where id=?";
	private final String DELETE_TIXIAN_BY_ID ="delete from Tixian where id=?";
	
	@SuppressWarnings("unchecked")
	public List<Tixian> GetExcutedUnReadMsgList(String id) {
		return getHibernateTemplate().find(GET_USER_NOT_READ_EXUCTED, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tixian> GetAllNotExcuteTixianList() {
		return getHibernateTemplate().find(GET_ALL_NOT_EXCUTE_TIXIAN);
	}
	
	public void excuteTixianInfo(String tixianid)
	{
		getHibernateTemplate().bulkUpdate(EXCUTE_TIXIAN, tixianid);
	}
	
	public void updateMessageState(String tixianid)
	{
		getHibernateTemplate().bulkUpdate(UpdateMessageStatus, tixianid);
	}
	
	@SuppressWarnings("unchecked")
	public Tixian queryTixianDayInfo(String id, String tixianriqi)
	{
		List<Tixian> userDayTixianS = getHibernateTemplate().find(GET_USER_DAY_TIXIAN, id,
				tixianriqi);
		
		if(null == userDayTixianS || userDayTixianS.size() <= 0)
		{
			return null;
		}
		
		Tixian userDayTixian = userDayTixianS.get(0);
		if(null != userDayTixian)
		{
			return userDayTixian;
		}
		else
		{
			return null;
		}
	}
	
	public void UpdateUserDayTixian(String id, String tixianriqi, String tixiane)
	{
		getHibernateTemplate().bulkUpdate(UPDATE_USER_DAY_TIXIAN, tixiane, id, tixianriqi);
	}
	
	public void DeleteUserDayTixian(String id, String tixianriqi)
	{
		getHibernateTemplate().bulkUpdate(DELETE_USER_DAY_TIXIAN, id, tixianriqi);
	}
	
	public Double GetUserSumTixian(String id) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();

		Object sumtixiane = session.createQuery(GET_USER_SUM_TIXIAN)
				.setParameter(0, id).uniqueResult();

		Double dRet = 0.0;
		if (null != sumtixiane) {
			dRet = Double.parseDouble(sumtixiane.toString());
		} else {
			;
		}

		return dRet;
	}
	
	public void deleteTixianById(String id)
	{
		getHibernateTemplate().bulkUpdate(DELETE_TIXIAN_BY_ID, id);
	}
}
