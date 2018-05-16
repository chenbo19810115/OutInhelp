package com.loiuschen.help.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.loiuschen.help.domain.Touzi;

@Repository
public class TouziDao  extends BaseDao<Touzi> {
	protected final String GET_USER_DAY_TOUZI = "from Touzi where id=? and touziriqi=?";
	protected final String UPDATE_USER_DAY_TOUZI = "update Touzi set touzie=?, touzitianshu=? where id=? and touziriqi=?";
	protected final String DELETE_USER_DAY_TOUZI = "delete from Touzi where id=? and touziriqi=?";
	protected final String GET_USER_SUM_TOUZI = "select sum(touzie) as zongtouzie from Touzi where id=?";
	protected final String GET_USER_NIAN_YEAR_LIST = "from Touzi where id=? order by touziriqi asc";
	protected final String DELETE_USER_TOUZI = "delete from Touzi where id=?";
	
	@SuppressWarnings("unchecked")
	public Touzi queryTouziDayInfo(String id, String touziriqi)
	{
		List<Touzi> userDayTouziS = getHibernateTemplate().find(GET_USER_DAY_TOUZI, id,
				touziriqi);
		
		if(null == userDayTouziS || userDayTouziS.size() <= 0)
		{
			return null;
		}
		
		Touzi userDayTouzi = userDayTouziS.get(0);
		if(null != userDayTouzi)
		{
			return userDayTouzi;
		}
		else
		{
			return null;
		}
	}
	
	public void UpdateUserDayTouzi(String id, String touziriqi, String touzie, String touzitianshu)
	{
		getHibernateTemplate().bulkUpdate(UPDATE_USER_DAY_TOUZI, touzie, touzitianshu, id, touziriqi);
	}
	
	public void DeleteUserTouzi(String id)
	{
		getHibernateTemplate().bulkUpdate(DELETE_USER_TOUZI, id);
	}
	
	public void DeleteUserDayTouzi(String id, String touziriqi)
	{
		getHibernateTemplate().bulkUpdate(DELETE_USER_DAY_TOUZI, id, touziriqi);
	}
	
	public Double queryUserSumTouzi(String id)
	{
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Object sumTouzi = session.createQuery(  
				GET_USER_SUM_TOUZI).setParameter(0, id).uniqueResult(); 
		
		Double dRet = 0.0;
		if(null != sumTouzi)
		{
			dRet = Double.parseDouble(sumTouzi.toString());
		}
		else
		{
			;
		}
		
		return dRet;
	}
	
	@SuppressWarnings("unchecked")
	public List<Touzi> GetUserNianHuaList(String id)
	{
		 return getHibernateTemplate().find(GET_USER_NIAN_YEAR_LIST, id);
	}
}
