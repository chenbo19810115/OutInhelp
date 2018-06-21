package com.loiuschen.help.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.loiuschen.help.domain.JizhangSum;

@Repository
public class JizhangSumDao  extends BaseDao<JizhangSum> {
	
	protected final String GET_USER_JIZHNAGSUM = "from JizhangSum where id=?";
	
	protected final String GET_EXCLUDE_USER_JIZHNAG = "from JizhangSum where id !=?";
	
	protected final String UpdateJizhang = "update JizhangSum set zongtouzie=?, shouyie=?, zongshouyie=?, jizhangriqi=? where id =?";
	
	protected final String UpdateJizhang2 = "update JizhangSum set zongtouzie=?, zongshouyie=? where id =?";
	
	protected final String DELETE_USER_JIZHANGSUM = "delete from JizhangSum where id=?";
	
	protected final String UPDATE_USER_NIAN_LV = "update JizhangSum set nianhuashouyilv=? where id =?";
	
	@SuppressWarnings("unchecked")
	public List<JizhangSum> GetUserJizhangSumInfo(String id) {
		return getHibernateTemplate().find(GET_USER_JIZHNAGSUM, id);
	}
	
	public int UpdateJizhangSumInfo(String userId,
			String zongtouzie, String shouyie, String zongshouyie, String jizhangriqi)
	{
		return getHibernateTemplate().bulkUpdate(UpdateJizhang, zongtouzie, 
				shouyie, zongshouyie, jizhangriqi, userId);
	}
	
	public int UpdateJizhangSumInfo(String userId,
			String zongtouzie, String zongshouyie)
	{
		return getHibernateTemplate().bulkUpdate(UpdateJizhang2, zongtouzie, 
				zongshouyie, userId);
	}
	
	public void deletejizhangSumbyusrid(String userid)
	{
		getHibernateTemplate().bulkUpdate(DELETE_USER_JIZHANGSUM, userid);
	} 
	
	@SuppressWarnings("unchecked")
	public List<JizhangSum> GetExCludeUserJizhangSumInfo(String userid)
	{
		return getHibernateTemplate().find(GET_EXCLUDE_USER_JIZHNAG, userid);
	}
	
	public int UpdateUsrNianhualv(String userId, String userNianhualv){
		return getHibernateTemplate().bulkUpdate(UPDATE_USER_NIAN_LV, userNianhualv, userId);
	}
}
