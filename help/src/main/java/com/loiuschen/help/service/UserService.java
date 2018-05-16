package com.loiuschen.help.service;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loiuschen.help.dao.BackDao;
import com.loiuschen.help.dao.JizhangDayDao;
import com.loiuschen.help.dao.JizhangSumDao;
import com.loiuschen.help.dao.OptinfoDao;
import com.loiuschen.help.dao.ShouyiDao;
import com.loiuschen.help.dao.TixianDao;
import com.loiuschen.help.dao.TouziDao;
import com.loiuschen.help.dao.UserDao;
import com.loiuschen.help.dao.UserRegDao;
import com.loiuschen.help.domain.Back;
import com.loiuschen.help.domain.JizhangDay;
import com.loiuschen.help.domain.JizhangSum;
import com.loiuschen.help.domain.Message;
import com.loiuschen.help.domain.Optinfo;
import com.loiuschen.help.domain.Shouyi;
import com.loiuschen.help.domain.SumbangdanItem;
import com.loiuschen.help.domain.Tixian;
import com.loiuschen.help.domain.Touzi;
import com.loiuschen.help.domain.User;
import com.loiuschen.help.domain.UserReg;

@Service
public class UserService extends BaseService{
	private final String BACKUP_DIR = "";
	private final String DB_USER = "root";
	private final String DB_PWD = "root";
	private final String DB_NAME = "help";
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRegDao userRegDao;
	@Autowired 
	private JizhangDayDao jizhangDayDao;
	@Autowired 
	private JizhangSumDao jizhangSumDao;
	@Autowired 
	private TouziDao touziDao;
	@Autowired 
	private ShouyiDao shouyiDao;
	@Autowired
	private TixianDao tixianDao;
	@Autowired
	private OptinfoDao optinfoDao;
	@Autowired
	private BackDao backDao;
	
	private static SimpleDateFormat df = null;
	
	static{
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	
	public String GetUserInfo(String username, String password)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userDao.queryUserInfoByName(username, password);
		if (null != user) {
			map.put("code", 200);
			map.put("msg", "请求成功");
			Map<String, Object> usermap = new HashMap<String, Object>();
			usermap.put("id", user.getId());
			usermap.put("name", user.getName());
			usermap.put("cellphone", user.getCellphone());
			usermap.put("securityCode", user.getSecurityCode());
			usermap.put("email", user.getEmail());
			usermap.put("author", user.getAuthor());
			map.put("user", usermap);
			JsonConfig sc = new JsonConfig();
			return JSONObject.fromObject(map, sc).toString();
			
		} else {
			map.put("code", 500);
			map.put("msg", "账号或密码错误");
			JsonConfig sc = new JsonConfig();
			return JSONObject.fromObject(map, sc).toString();
		}
	}
	
	public boolean bCheckUserExist(String userId){
		boolean bRet = false;
		User useTemp = userDao.queryUserInfoByID(userId);
		if(null != useTemp){
			bRet = true;
		}
		return bRet;
	}
	
	public Serializable SaveUserInfo(String name, String cellphone, String email,
                 String id, String securityCode, String password)
	{
		User user = new User();
		user.setName(name);
		user.setId(id);
		user.setEmail(email);
		user.setSecurityCode(securityCode);
		user.setCellphone(cellphone);
		user.setPassword(password);
		return userDao.save(user);
	}
	
	public String SaveUserRegQuestInfo(String name, String cellphone, String email, String id){
		Map<String, Object> map = new HashMap<String, Object>();
		UserReg userReg = new UserReg();
		userReg.setId(id);
		userReg.setName(name);
		userReg.setCellphone(cellphone);
		userReg.setEmail(email);
		
		UserReg existReg = userRegDao.GetRegInfo(id);
		if(null == existReg)
		{
			Serializable ret = userRegDao.save(userReg);
			if(null != ret)
			{
				map.put("retCode", 500);
				map.put("msg", "等待系统管理员发送注册激活页面到邮箱！请注意查收！");
			}
			else
			{
				map.put("retCode", 501);
				map.put("msg", "此用户已经注册，请直接登录！");
			}
		}
		else
		{
			map.put("retCode", 501);
			map.put("msg", "此用户已经注册，请直接登录！");
		}
		
		JsonConfig sc = new JsonConfig();
		return JSONObject.fromObject(map, sc).toString();
	}
	
	public String GetRegInfoList()
	{
		List<UserReg> reglist = userRegDao.loadAll();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", 500);
		map.put("reglist", reglist);
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(map, sc).toString();
	}
	
	public UserReg GetRegInfo(String id)
	{
		return userRegDao.GetRegInfo(id);
	}
	
	public void UpdateRegInfo(String id)
	{
		userRegDao.UpdateRegInfo(id);
	}
	
	public void deleteReg(String id)
	{
		userRegDao.deleteReg(id);
	}
	
	public void deleteUsr(String id)
	{
		userDao.deleteUser(id);
	}
	
	public String GetUsrSumInfo()
	{
		List<User> userList = userDao.loadAll();
		List<Object> totalList = new LinkedList<Object>();
		for(User user: userList)
		{
			Integer author = user.getAuthor();
			if(author.equals(0))
			{
				continue;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			String id = user.getId();
			String name = user.getName();
			String cellphone = user.getCellphone();
			String email = user.getEmail();
			map.put("name", name);
			map.put("id", id);
			map.put("cellphone", cellphone);
			map.put("email", email);
			totalList.add(map);
		}
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("retCode", 500);
		jsonMap.put("userInfo", totalList);
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(jsonMap, sc).toString();
	}
	
	public String GetbnotExcutedTixianInfoList()
	{
		List<Tixian> tixianlist = tixianDao.GetAllNotExcuteTixianList();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", 500);
		map.put("tixianlist", tixianlist);
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(map, sc).toString();
	}

	public String excuteTixianInfo(String tixianid, String id,String tixianriqi,String tixiane)
	{
		tixianDao.excuteTixianInfo(tixianid);
		
		optinfoDao.SaveTixianExcuteOpt(id, tixianriqi, tixiane);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", 500);
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(map, sc).toString();
	}
	
	public String GetTixianMessageList(String userId)
	{
		List<Tixian> tixianlist = tixianDao.GetExcutedUnReadMsgList(userId);
		List<Message> messagelist = new LinkedList<Message>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", 500);
		for(Tixian tixian: tixianlist)
		{
			Message message = new Message();
			message.setTixianid(tixian.getTixianid());
			message.setTitle("提现操作消息");
			message.setTime(tixian.getTixianriqi());
			message.setContent("你" + tixian.getTixianriqi() + "的体现" + tixian.getTixiane() + "金额已经被系统管理员处理");
			messagelist.add(message);
		}
		
		map.put("messagelist", messagelist);
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(map, sc).toString();
	}
	
	public String updateMessageState(String tixianid)
	{
		tixianDao.updateMessageState(tixianid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", 500);
		JsonConfig sc = new JsonConfig();
		return JSONObject.fromObject(map, sc).toString();
	}
	
	public String modifyPW(String userId, String password)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		int validN = userDao.updateUserPW(userId, password);
		if( 0== validN){
			map.put("retCode", 501);
			map.put("msg", "修改密码失败！");
		}
		else{
			map.put("retCode", 500);
			map.put("msg", "修改密码成功！");
		}
		
		JsonConfig sc = new JsonConfig();
		return JSONObject.fromObject(map, sc).toString();
	}
	
	public String GetUserJizhangInfo(String userId)
	{
		List<JizhangDay> jizhanglist = null;
		if(null == userId)
		{
			jizhanglist= jizhangDayDao.loadAll();
		}else{
			jizhanglist = jizhangDayDao.GetUserJizhangInfo(userId);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
	    if(null == jizhanglist || jizhanglist.size() <= 0)
	    {
	    	map.put("retCode", 501);
	    	map.put("msg", "没有用户记账信息！");
	    	
	    }else
	    {
	    	map.put("retCode", 500);
	    	map.put("msg", "用户的记账信息获取成功！");
	    }
	    
		map.put("userjizhanginfo", jizhanglist);
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(map, sc).toString();
	}
	
	public void DeleteUserJizhangInfo(String jizhangid,String userId,String jizhangriqi)
	{
		jizhangDayDao.deleteJizhang(jizhangid);
		
		optinfoDao.SaveDelteUserJizhangDayOpt(jizhangid, userId, jizhangriqi);
	}
	
	public String GetUserBianNianShouyi(String userId, String lastJizhangriqi){
		Double bannianshouyi = shouyiDao.GetUserBianNianShouyi(userId, lastJizhangriqi);
		return bannianshouyi.toString();
	}
	
	public String GetSumbangdanInfo(String userId)
	{
		List<JizhangSum> jizhanglist= jizhangSumDao.GetExCludeUserJizhangSumInfo(userId);;
		
		List<JizhangSum> curUserjizhanglist = jizhangSumDao.GetUserJizhangSumInfo(userId);
		
		List<SumbangdanItem> sumbangdanlist = new LinkedList<SumbangdanItem>();
		for(JizhangSum elem: curUserjizhanglist)
		{
			String id = elem.getId();
			User userInfo = userDao.queryUserInfoByID(id);
			String userName = userInfo.getName();
			
			SumbangdanItem bangdanItem = new SumbangdanItem();
			bangdanItem.setName(userName);
			bangdanItem.setZongtouzie(elem.getZongtouzie());
			bangdanItem.setShouyie(elem.getShouyie());
			bangdanItem.setZongshouyie(elem.getZongshouyie());
			String bannianshouyi = GetUserBianNianShouyi(id, elem.getJizhangriqi());
			bangdanItem.setBannianshouyie(bannianshouyi);
			sumbangdanlist.add(bangdanItem);
		}
		
		for(JizhangSum elem: jizhanglist)
		{
			String id = elem.getId();
			User userInfo = userDao.queryUserInfoByID(id);
			String userName = userInfo.getName();
			
			int nameSize = userName.length();
			String nameTmp = userName.substring(0, 1);
			for(int idx = 1; idx < nameSize; idx++){
				nameTmp += "*";
			}
			
			SumbangdanItem bangdanItem = new SumbangdanItem();
			bangdanItem.setName(nameTmp);
			bangdanItem.setZongtouzie(elem.getZongtouzie());
			bangdanItem.setShouyie(elem.getShouyie());
			bangdanItem.setZongshouyie(elem.getZongshouyie());
			String bannianshouyi = GetUserBianNianShouyi(id, elem.getJizhangriqi());
			bangdanItem.setBannianshouyie(bannianshouyi);
			sumbangdanlist.add(bangdanItem);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
	    if(null == sumbangdanlist || sumbangdanlist.size() <= 0)
	    {
	    	map.put("retCode", 501);
	    	map.put("msg", "没有用户记账信息！无法生成总榜单");
	    	
	    }else
	    {
	    	map.put("retCode", 500);
	    	map.put("msg", "用户的记账信息获取成功！");
	    }
	    
		map.put("sumbangdanlist", sumbangdanlist);
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(map, sc).toString();
	}
	
	public String GetJizhangSumInfo(String userId)
	{
		List<JizhangSum> JizhangSumlist = null;
		if(null == userId)
		{
			JizhangSumlist= jizhangSumDao.loadAll();
		}else{
			JizhangSumlist = jizhangSumDao.GetUserJizhangSumInfo(userId);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
	    if(null == JizhangSumlist || JizhangSumlist.size() <= 0)
	    {
	    	map.put("retCode", 501);
	    	map.put("msg", "没有用户投资收益汇总信息！");
	    	
	    }else
	    {
	    	map.put("retCode", 500);
	    	map.put("msg", "");
	    }
	    
		map.put("userjizhanginfo", JizhangSumlist);
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(map, sc).toString();
	}
	
	public Map<String, Double> GetUserTouziRate()
	{
		Map<String, Double> userTouziRate = new HashMap<String, Double>();
		List<JizhangSum> JizhangSumlist = jizhangSumDao.loadAll();
		Double allUsrTouzie = 0.0;
		for(JizhangSum jizhangSum: JizhangSumlist)
		{
			String userId = jizhangSum.getId();
			String zongtouzie = jizhangSum.getZongtouzie();
			Double dZongtouzie = Double.parseDouble(zongtouzie);
			allUsrTouzie = allUsrTouzie + dZongtouzie;
			userTouziRate.put(userId, dZongtouzie);
		}
		
		for(Map.Entry<String, Double> elem: userTouziRate.entrySet())
		{
			String userId = elem.getKey();
			Double rate = elem.getValue() / allUsrTouzie;
			userTouziRate.put(userId, rate);
		}
		
		return userTouziRate;
	}
	
	public JizhangSum GetNewUserJizhangSumInfo(String userId)
	{
		List<JizhangSum> JizhangSumlist = jizhangSumDao.GetUserJizhangSumInfo(userId);
	    if(null == JizhangSumlist || JizhangSumlist.size() <= 0)
	    {
	    	return null;
	    }else
	    {
	    	return JizhangSumlist.get(0);
	    }
	}
	
	public void ModifyUserJizhangSumInfo(String userId, String shouyie)
	{
		Double userZongtouzie = touziDao.queryUserSumTouzi(userId);
		
		Double userZongshouyie = shouyiDao.queryUserSumShouyi(userId);
		
		jizhangSumDao.UpdateJizhangSumInfo(userId, userZongtouzie.toString(), 
				shouyie, userZongshouyie.toString());
		
	}
	
	public void ModifyUserJizhangSumInfo(String userId)
	{
		Double userZongtouzie = touziDao.queryUserSumTouzi(userId);
		
		Double userZongshouyie = shouyiDao.queryUserSumShouyi(userId);
		
		jizhangSumDao.UpdateJizhangSumInfo(userId, userZongtouzie.toString(), 
				userZongshouyie.toString());
		
	}
	
	public void ModifyUserNianhualv(String userId){
		
        Double userZongtouzie = touziDao.queryUserSumTouzi(userId);
		
		Double userZongshouyie = shouyiDao.queryUserSumShouyi(userId);
		
		Double nianhualv = 0.0;
		
		if(userZongtouzie > 0)
		{
			List<Touzi> nianhuas = touziDao.GetUserNianHuaList(userId);
			if(nianhuas.size() == 1)
			{
				nianhualv = (365 * userZongshouyie ) / (365 * userZongtouzie);
				
			}else if(nianhuas.size() > 1)
			{
				Double niantouziDayS = 0.0;
				for(Touzi elem: nianhuas)
				{
					Double touzie = Double.parseDouble(elem.getTouzie());
					int    tianshu = Integer.parseInt(elem.getTouzitianshu());
					niantouziDayS = niantouziDayS + touzie * tianshu;
				}
				
				if(niantouziDayS > 0)
				{
					nianhualv = (365 * userZongshouyie) / niantouziDayS;
				}else{
					;
				}
			}else{
				;
			}
		}
		else{
			;
		}
		
		jizhangSumDao.UpdateUsrNianhualv(userId, nianhualv.toString());
		
	}
	
	public void AddUserJizhangDayInfo(String userId, String jizhangriqi, 
			String xinzengtouzie, String shouyie, String tixiane, String touzitianshu)
	{
		JizhangDay jizhangDayExisted = jizhangDayDao.queryJizhangDayInfo(userId, jizhangriqi);
		if(null != jizhangDayExisted){//有
			jizhangDayDao.UpdateJizhangDayInfo(userId, jizhangriqi, 
					xinzengtouzie, shouyie, tixiane);
			
			optinfoDao.SaveModifyUserJizhangDayOpt(userId, jizhangriqi, 
					xinzengtouzie, shouyie, tixiane, touzitianshu);
		}
		else{//无
			JizhangDay jizhangDayAdded = new JizhangDay();
			jizhangDayAdded.setId(userId);
			jizhangDayAdded.setJizhangriqi(jizhangriqi);
			jizhangDayAdded.setShouyie(shouyie);
			jizhangDayAdded.setXinzengtouzie(xinzengtouzie);
			jizhangDayAdded.setTixiane(tixiane);
			jizhangDayAdded.setTouzitianshu(touzitianshu);
			jizhangDayDao.save(jizhangDayAdded);
			
			optinfoDao.SaveAddingUserJizhangDayOpt(userId, jizhangriqi, 
					xinzengtouzie, shouyie, tixiane, touzitianshu);
		}
	}
	
	public boolean bUserJizhangExist(String userId)
	{
		boolean bRet = false;
		List<JizhangDay> jizhanglist = jizhangDayDao.GetUserJizhangInfo(userId);
		if(null == jizhanglist || jizhanglist.size() <= 0)
		{
			bRet = false;
		}
		else{
			JizhangDay jizhangDay = jizhanglist.get(0);
			if(null != jizhangDay)
			{
				bRet = true;
			}
			else
			{
				bRet = false;
			}
		}
		
		return bRet;
	}
	
	public int AddUserJizhangSumInfo(String userId, String jizhangriqi, 
			String xinzengtouzie, String shouyie, String tixiane)
	{
		//成功要汇总
		List<JizhangSum> userjizhangsum = jizhangSumDao.GetUserJizhangSumInfo(userId);
		if(userjizhangsum.size() <= 0)
		{
			//没有则插入新记录
			JizhangSum jizhangsum = new JizhangSum();
			jizhangsum.setId(userId);
			jizhangsum.setJizhangriqi(jizhangriqi);
			jizhangsum.setShouyie(shouyie);
			jizhangsum.setZongshouyie(shouyie);
			jizhangsum.setZongtouzie(xinzengtouzie);
			jizhangSumDao.save(jizhangsum);
			return 0;
		}
		else
		{
			//有则更新记录
			//收益额 == 收益额； 
			//总投资 = 总投资 + 新增投资 ； 
			//总收益 = 总收益 + 收益额；
			//半年收益额 = 把当前记账日期，前的半年都计算；
			//可以添加半年时间：进行时间比对：减少重新计算次数；
			JizhangSum jizhangsum = userjizhangsum.get(0);
			String zongtouzie = String.valueOf(
					Double.parseDouble(jizhangsum.getZongtouzie()) 
					+ Double.parseDouble(xinzengtouzie));
			
			String zongshouyie = String.valueOf(
					Double.parseDouble(jizhangsum.getZongshouyie()) 
					+ Double.parseDouble(shouyie));
			
			jizhangSumDao.UpdateJizhangSumInfo(userId, zongtouzie, shouyie, zongshouyie);
			return 1;
			
		}
	}
	
	public void DeleteUserSumJizhang(String userId)
	{
		jizhangSumDao.deletejizhangSumbyusrid(userId);
	    jizhangDayDao.deletejizhangDaybyusrid(userId);
	    touziDao.DeleteUserTouzi(userId);
	    shouyiDao.deleteShouyiById(userId);
	}
	
	public String ModifyuserInfo(String id, String cellphone, String email)
	{
		userDao.updateUserInfo(id, cellphone, email);
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("retCode", 500);
	    map.put("msg", "");
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(map, sc).toString();
	}
	
	public List<User> GetAllUser()
	{
		return userDao.GetALLUser();
	}
	
	public void AddTouziRec(String id, String touziriqi, String touzie, String touzitianshu)
	{
		Touzi touziExisted = touziDao.queryTouziDayInfo(id, touziriqi);
		if(null == touziExisted)
		{
			Touzi newTouzi = new Touzi();
			newTouzi.setId(id);
			newTouzi.setTouzie(touzie);
			newTouzi.setTouziriqi(touziriqi);
			newTouzi.setTouzitianshu(touzitianshu);
			touziDao.save(newTouzi);
		}
		else
		{
			touziDao.UpdateUserDayTouzi(id, touziriqi, touzie, touzitianshu);
		}
	}
	
	public void DeleteTouziRec(String id, String touziriqi)
	{
		touziDao.DeleteUserDayTouzi(id, touziriqi);
	}
	
	public void AddShouyiRec(String id, String shouyiriqi, String shouyie)
	{
		Shouyi shouyiExisted = shouyiDao.queryShouyiDayInfo(id, shouyiriqi);
		if(null == shouyiExisted){
			Shouyi newShouyi = new Shouyi();
			newShouyi.setId(id);
			newShouyi.setShouyiriqi(shouyiriqi);
			newShouyi.setShouyie(shouyie);
			shouyiDao.save(newShouyi);
		}else{
			shouyiDao.UpdateUserDayShouyi(id, shouyiriqi, shouyie);
		}
	}
	
	public void DeleteShouyiRec(String id, String shouyiriqi)
	{
		shouyiDao.DeleteUserDayShouyi(id, shouyiriqi);
	}
	
	public void AddTixianRec(String id, String tixianRiqi, String tixiane)
	{
		Tixian tixianExisted = tixianDao.queryTixianDayInfo(id, tixianRiqi);
		if(null == tixianExisted){
			Tixian newTixian = new Tixian();
			newTixian.setId(id);
			newTixian.setTixianriqi(tixianRiqi);
			newTixian.setTixiane(tixiane);
			tixianDao.save(newTixian);
		}else{
			tixianDao.UpdateUserDayTixian(id, tixianRiqi, tixiane);
		}
	}
	
	public void DeleteTixianRec(String id, String tixianRiqi)
	{
		tixianDao.DeleteUserDayTixian(id, tixianRiqi);
	}
	
	public String GetOptRecInfo()
	{
		List<Optinfo> optinfolist = optinfoDao.loadAll();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", 500);
		map.put("Optinfolist", optinfolist);
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(map, sc).toString();
	}
	
	public String ClearOptRecInfo()
	{
		optinfoDao.clearOptinfo();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", 500);
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(map, sc).toString();
	}
	
	
	public String GetbackrecInfo()
	{
		List<Back> backlist = backDao.loadAll();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", 500);
		map.put("backlist", backlist);
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(map, sc).toString();
	}
	
	public String BackdataInfo()
	{
		String filename = "backup-" + df.format(new Date()) + ".sql";
		
		String savePath = BACKUP_DIR + filename; 
		
		String[] execCMD = new String[] {"mysqldump", "-u" + DB_USER, "-p" + DB_PWD, DB_NAME,  
		            "-r" + savePath, "--skip-lock-tables"}; 
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			
			Process process = null;
			process = Runtime.getRuntime().exec(execCMD);
			
			try {
				int processComplete = process.waitFor();
				if (processComplete == 0) {  
				    backDao.backdataInfo(filename);
				    List<Back> backlist = backDao.loadAll();
				    map.put("retCode", 500);
					map.put("backlist", backlist);
				} else {
					backDao.backdataInfo(filename);
				    map.put("retCode", 501);
				} 
			} catch (InterruptedException e) {
				map.put("retCode", 501);
			}
		} catch (IOException e) {
			map.put("retCode", 501);
		}
		
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(map, sc).toString();
	}
	
	public String RestorDataInfo(String backid)
	{
		String filename = backDao.getbackfilename(backid);
		
		String targetFile =  BACKUP_DIR + filename; 
		
	    String[] execCMD = new String[]{"mysql", DB_NAME, "-u" + DB_USER, "-p" + DB_PWD, "-e source", targetFile};  
	   
	    Map<String, Object> map = new HashMap<String, Object>();
	    
		try {
			Process process = Runtime.getRuntime().exec(execCMD);
			
			try {
				int processComplete = process.waitFor();
				
				if (processComplete == 0) {  
					map.put("retCode", 500);
			    } else {  
			    	map.put("retCode", 501);  
			    }  
			} catch (InterruptedException e) {
				map.put("retCode", 501); 
			}  
		} catch (IOException e) {
			map.put("retCode", 501);
		}  
	  
		JsonConfig sc = new JsonConfig();
		return  JSONObject.fromObject(map, sc).toString();
	}
}
