package com.loiuschen.help.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.loiuschen.help.domain.User;

@Repository
public class UserDao extends BaseDao<User> {

	private final String QUERY_USER_BY_USERNAME = "from User where name = ? and password = ?";
	protected final String DelteUser = "delete from User where id=?";
	private final String QUERY_USER_BY_ID = "from User where id = ?";
	private final String UPDATE_USER_PW = "update User set password = ? where id =?";
	private final String UPDATE_USER_INFO = "update User set cellphone=?, email=? where id=?";
	private final String GET_ALL_USER = "from User where author = 1";
	
	@SuppressWarnings("unchecked")
	public User queryUserInfoByName(String name, String password) {
		List<User> userLs = getHibernateTemplate().find(QUERY_USER_BY_USERNAME, name,
				password);
		
		if(null == userLs || userLs.size() <= 0)
		{
			return null;
		}
		
		User user = userLs.get(0);
		if(null != user)
		{
			return user;
		}
		else
		{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<User> GetALLUser()
	{
		return getHibernateTemplate().find(GET_ALL_USER);
	}
	
	@SuppressWarnings("unchecked")
	public User queryUserInfoByID(String id) {
		List<User> userLs = getHibernateTemplate().find(QUERY_USER_BY_ID, id);
		
		if(null == userLs || userLs.size() <= 0)
		{
			return null;
		}
		
		User user = userLs.get(0);
		if(null != user)
		{
			return user;
		}
		else
		{
			return null;
		}
	}
	
	public void deleteUser(String id)
	{
		getHibernateTemplate().bulkUpdate(DelteUser, id);
	}
	
	public int updateUserPW(String id, String pw)
	{
		return getHibernateTemplate().bulkUpdate(UPDATE_USER_PW, pw, id);
	}
	
	public void updateUserInfo(String id, String cellphone, String email)
	{
		getHibernateTemplate().bulkUpdate(UPDATE_USER_INFO, cellphone, email, id);
		
	}
}
