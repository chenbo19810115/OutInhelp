package com.loiuschen.help.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.loiuschen.help.domain.JizhangDay;

@Repository
public class JizhangDayDao extends BaseDao<JizhangDay> {
    private final String QUERY_USER_DAY_JIZHANG = "from JizhangDay where id = ? and jizhangriqi = ?";
    
    private final String GET_USER_JIZHNAG = "from JizhangDay where id=?";
    
    private final String UPDATE_USER_DAY_JIZHANG = "update JizhangDay set xinzengtouzie=?, shouyie=?, tixiane=? where id = ? and jizhangriqi = ?";
    
    private final String UPDATE_JIZHANG = "update JizhangDay set jizhangriqi=?, xinzengtouzie=?, tixiane=?,shouyie=? where jizhangid=?";
    
    private final String DELETE_USER_DAY_JIZHANG = "delete from JizhangDay where id = ?";
    
    private final String DELETE_JIZHANG = "delete from JizhangDay where jizhangid=?";
    
    @SuppressWarnings("unchecked")
	public JizhangDay queryJizhangDayInfo(String id, String jizhangriqi) {
		List<JizhangDay> userDayjizhang = getHibernateTemplate().find(QUERY_USER_DAY_JIZHANG, id,
				jizhangriqi);
		
		if(null == userDayjizhang || userDayjizhang.size() <= 0)
		{
			return null;
		}
		
		JizhangDay jizhangDay = userDayjizhang.get(0);
		if(null != jizhangDay)
		{
			return jizhangDay;
		}
		else
		{
			return null;
		}
	}
    
    @SuppressWarnings("unchecked")
	public List<JizhangDay> GetUserJizhangInfo(String id) {
		return getHibernateTemplate().find(GET_USER_JIZHNAG, id);
	}
    
    public int UpdateJizhangDayInfo(String userId, String jizhangriqi,
			String xinzengtouzie, String shouyie, String tixiane)
	{
		return getHibernateTemplate().bulkUpdate(UPDATE_USER_DAY_JIZHANG, xinzengtouzie, shouyie, 
				tixiane, userId, jizhangriqi);
	}
    
    public void deletejizhangDaybyusrid(String userid)
	{
		getHibernateTemplate().bulkUpdate(DELETE_USER_DAY_JIZHANG, userid);
	}
    
    public int UpdateJizhangInfo(String jizhangid, String userId, String jizhangriqi,
			String xinzengtouzie, String tixiane, String shouyie)
	{
		return getHibernateTemplate().bulkUpdate(UPDATE_JIZHANG, jizhangriqi,
				xinzengtouzie, tixiane, shouyie, jizhangid);
	}
    
    public int deleteJizhang(String jizhangid)
	{
		return getHibernateTemplate().bulkUpdate(DELETE_JIZHANG, jizhangid);
	}
    
}
