package com.loiuschen.help.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.loiuschen.help.domain.Back;

@Repository
public class BackDao extends BaseDao<Back> {
	
	private final String GET_ALL_BACK = "from Back order by backfile desc";
	
	private final String GET_ONE_BACK = "from Back where backid = ?";
	
	@SuppressWarnings("unchecked")
	public List<Back>  loadAll()
	{
		return getHibernateTemplate().find(GET_ALL_BACK);
	}
	
	public void backdataInfo(String filename)
	{
		Back backInfo = new Back();
		
		backInfo.setBackfile(filename);
		
		getHibernateTemplate().save(backInfo);
		
	}
	
	@SuppressWarnings("unchecked")
	public String getbackfilename(String backid)
	{
		List<Back> backs = getHibernateTemplate().find(GET_ONE_BACK, backid);
		if(null != backs && backs.size() > 0)
		{
			Back back = backs.get(0);
			if(null != back && back.getBackfile().isEmpty())
			{
				return back.getBackfile();
			}else
			{
				return null;
			}
		}else
		{
			return null;
		}
	}
	
}
