package com.loiuschen.help.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.loiuschen.help.domain.Shouyi;

@Repository
public class ShouyiDao extends BaseDao<Shouyi>{
    private final String DELETE_SHOUYI_BY_ID ="delete from Shouyi where id=?";
    private final String GET_USER_DAY_SHOYI = "from Shouyi where id=? and shouyiriqi=?";
    private final String UPDATE_USER_DAY_SHOUYI = "update Shouyi set shouyie=? where id=? and shouyiriqi=?";
    private final String DELETE_USER_DAY_SHOUYI = "delete from Shouyi where id=? and shouyiriqi=?";
    private final String GET_USER_SUM_SHOUYI = "select sum(shouyie) as zongshouyie from Shouyi where id=?";
    private final String QUERY_SIX_MONTH_SHOUYI = "select sum(shouyie) as sixmonthshoyie from Shouyi where id = ? and shouyiriqi >= ?";
    
	public Double GetUserBianNianShouyi(String userId, String lastJizhangriqi)
    {
		Double dRet = 0.0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date date = format.parse(lastJizhangriqi);
			c.setTime(date);
	        c.add(Calendar.MONTH, -6);
	        Date m3 = c.getTime();
	        String bannianqian = format.format(m3);
			
	    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
	 		
	 		Object sumShouyi = session.createQuery(  
	 				QUERY_SIX_MONTH_SHOUYI).setParameter(0, userId).setParameter(1, bannianqian).uniqueResult();
	    	
	    	if(null != sumShouyi)
	    	{
	    		dRet = Double.parseDouble(sumShouyi.toString());
	    	}
	    	else{
	    		;
	    	}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
    	return dRet;
    }
    
    public void deleteShouyiById(String id)
    {
    	getHibernateTemplate().bulkUpdate(DELETE_SHOUYI_BY_ID, id);
    }
    
    @SuppressWarnings("unchecked")
	public Shouyi queryShouyiDayInfo(String id, String shouyiriqi)
	{
		List<Shouyi> userDayShoyiS = getHibernateTemplate().find(GET_USER_DAY_SHOYI, id,
				shouyiriqi);
		
		if(null == userDayShoyiS || userDayShoyiS.size() <= 0)
		{
			return null;
		}
		
		Shouyi userDayShoyi = userDayShoyiS.get(0);
		if(null != userDayShoyi)
		{
			return userDayShoyi;
		}
		else
		{
			return null;
		}
	}
    
    public void UpdateUserDayShouyi(String id, String shouyiriqi, String shouyie)
	{
		getHibernateTemplate().bulkUpdate(UPDATE_USER_DAY_SHOUYI, shouyie, id, shouyiriqi);
	}
    
    public void DeleteUserDayShouyi(String id, String shouyiriqi)
	{
		getHibernateTemplate().bulkUpdate(DELETE_USER_DAY_SHOUYI, id, shouyiriqi);
	}
    
	public Double queryUserSumShouyi(String id)
	{
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Object sumShouyi = session.createQuery(  
				GET_USER_SUM_SHOUYI).setParameter(0, id).uniqueResult();
		
		Double dRet = 0.0;
		if(null != sumShouyi)
		{
			dRet = Double.parseDouble(sumShouyi.toString());
		}
		else
		{
			;
		}
		
		return dRet;
	}
}
