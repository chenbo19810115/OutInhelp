package com.loiuschen.help.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.loiuschen.help.domain.UserReg;

@Repository
public class UserRegDao  extends BaseDao<UserReg>{
	protected final String EmailReadly = "update UserReg set bsendmail='1' where id=?";
    protected final String FindReg = "from UserReg where id=?";
    protected final String AllReg = "from UserReg where bsendmail='0'";
    protected final String DelteReg = "delete from UserReg where id=?";
    
	public UserReg GetRegInfo(String id)
	{
		@SuppressWarnings("unchecked")
		List<UserReg> regLs = getHibernateTemplate().find(FindReg, id);
		if(null == regLs || regLs.size() <= 0)
		{
			return null;
		}
		
		UserReg regInfo = regLs.get(0);
		if(null != regInfo)
		{
			return regInfo;
		}
		else
		{
			return null;
		}
	}
	
	public void UpdateRegInfo(String id)
	{
		getHibernateTemplate().bulkUpdate(EmailReadly, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserReg> loadAll()
	{
		return getHibernateTemplate().find(AllReg);
	}
	
	public void deleteReg(String id)
	{
		getHibernateTemplate().bulkUpdate(DelteReg, id);
	}
	
	
}
